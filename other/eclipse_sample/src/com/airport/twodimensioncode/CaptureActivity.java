package com.airport.twodimensioncode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import com.airport.R;
import com.airport.bean.FightInfo;
import com.airport.bean.SearchEntity;
import com.airport.bean.ShopInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.commons.GlobalStrings;
import com.airport.help.message.MainBodyActivity;
import com.airport.network.NetStateReceiver;
import com.airport.network.NetworkService;
import com.airport.page.MainActivity;

import com.airport.page.UserActivity;
import com.airport.page.WelcomeActivity;
import com.airport.search.FightDetailActivity;
import com.airport.search.SearchresultActivity;
import com.airport.shop.ShopDetailActivity;
import com.airport.util.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Button btn_light_control;
	private boolean isShow = false;

	private ProgressBar pg;
	private ImageView iv_pg_bg_grey;

	boolean mMsg9Received;
	String mSearchedString;
	private NetStateReceiver netStateReceiver;
	private static CaptureActivity instance;

	public static CaptureActivity getInstance() {
		return instance;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_diy);
		instance = this;
		init_Service();
		// ViewUtil.addTopView(getApplicationContext(), this,
		// R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		btn_light_control = (Button) this.findViewById(R.id.btn_light_control);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	private void init_Service() {
		// TODO Auto-generated method stub
		NetworkService.getInstance().onInit(this);
		NetworkService.getInstance().setupConnection();
		netStateReceiver = new NetStateReceiver();
		registerReceiver(netStateReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

		btn_light_control.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				LightControl mLightControl = new LightControl();

				if (isShow) {
					isShow = false;
					btn_light_control
							.setBackgroundResource(R.drawable.torch_off);
					Toast.makeText(getApplicationContext(), "����ƹر�", 0)
							.show();
					mLightControl.turnOff();
				} else {
					isShow = true;
					btn_light_control
							.setBackgroundResource(R.drawable.torch_on);
					mLightControl.turnOn();
					Toast.makeText(getApplicationContext(), "����ƿ���", 0)
							.show();
				}

			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * Handler scan result
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		// FIXME
		if (resultString.equals("")) {
			Toast.makeText(CaptureActivity.this, "Scan failed!",
					Toast.LENGTH_SHORT).show();
		} else {
			// System.out.println("Result:"+resultString);
			if (pg != null && pg.isShown()) {
				pg.setVisibility(View.GONE);
				iv_pg_bg_grey.setVisibility(View.VISIBLE);
			}
			/*
			 * Intent resultIntent = new Intent(CaptureActivity.this,
			 * SearchresultActivity.class); Bundle bundle = new Bundle();
			 * bundle.putString("content", resultString);
			 * resultIntent.putExtras(bundle); // this.setResult(resultIntent);
			 * startActivity(resultIntent);
			 */
			System.out.println(resultString);

			String[] arr0 = resultString.split("&%&%");
			if (arr0[0].equals("HB")) {
				SearchEntity SE = new SearchEntity(
						SearchEntity.SEARCH_FIGHT_BY_CODE, arr0[1], "-1", "-1");
				startSearch(SE);

			} else if (arr0[0].equals("SD")) {
				Constants.TempSerchContent = arr0[1];

				SearchEntity SE = new SearchEntity(
						SearchEntity.SEARCH_SHOP_BY_LABEL, -1, -1, arr0[1],
						arr0[1], arr0[1]);
				startSearch(SE);
			} else if (arr0[0].equals("CAR")) {
				Intent intent = new Intent(CaptureActivity.this,
						UserActivity.class);
				Constants.CAR = arr0[1];
				startActivity(intent);
			}
		}
		// CaptureActivity.this.finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public void startSearch(SearchEntity sEnt0) {
		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgSearchPeople,
				sEnt0.toString());
		System.out.println("strsender=" + sEnt0.toString());
		mMsg9Received = false;
		while (!mMsg9Received) {
		}
		String[] arr0 = mSearchedString.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_BY_CODE) {
			gotoFightDetail(mSearchedString);
		} else if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_SHOP_BY_LABEL) {
			gotoShopDetail(mSearchedString);
		}

	}

	public void onReceiveSearchList(String msg0) {
		mSearchedString = msg0;
		mMsg9Received = true;
	}

	private void gotoFightDetail(String str) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(CaptureActivity.this,
				FightDetailActivity.class);
		String[] arr0 = str.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_BY_CODE) {
			FightInfo fi = new FightInfo(arr0[2]);
			intent.putExtra("fightinfo", fi.toString());
			startActivity(intent
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			overridePendingTransition(R.anim.my_slide_right_in,
					R.anim.my_slide_left_out);
		}
	}

	private void gotoShopDetail(String str) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(CaptureActivity.this,
				ShopDetailActivity.class);
		String[] arr0 = str.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_SHOP_BY_LABEL) {
			ShopInfo si = new ShopInfo(arr0[2]);
			intent.putExtra("shopinfo", si.toString());
			startActivity(intent
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			overridePendingTransition(R.anim.my_slide_right_in,
					R.anim.my_slide_left_out);
		}
	}

}