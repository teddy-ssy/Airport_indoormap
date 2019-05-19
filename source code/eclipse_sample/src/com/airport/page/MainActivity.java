package com.airport.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.airport.R;
import com.airport.bean.FightInfo;
import com.airport.bean.SearchEntity;
import com.airport.bean.ShopInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.commons.GlobalStrings;
import com.airport.help.MainHelpActivity;
import com.airport.help.chatService.ChatService;
import com.airport.help.message.MainBodyActivity;
import com.airport.help.message.MainBodyService;
import com.airport.help.userRequest.FriendRequestService;
import com.airport.map.MapActivity;
import com.airport.network.NetStateReceiver;
import com.airport.network.NetworkService;
import com.airport.search.SearchFightResultActivity;

import com.airport.twodimensioncode.CaptureActivity;
import com.airport.util.Constants;
import com.airport.util.DptoPx;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;

import android.speech.RecognizerIntent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DptoPx px = new DptoPx();
	private int one;
	private int two;
	// init
	private ImageView page_now;
	private ViewPager viewpager;
	private ImageView mtab1, mtab2, mtab3;
	private TextView text1, text2, text3;
	private TextView textview_dongtai2, textview_dongtai3, textview_dongtai4;
	private ListView listview_hangban;
	private LinearLayout layout;
	private int currIndex = 0;
	private EditText edittext_sousuo_input = null;
	private EditText edittext_hangbansearch = null;
	private BaiduASRDigitalDialog mDialog = null;
	private DialogRecognitionListener mRecognitionListener;
	private int mCurrentTheme = com.airport.util.Config.DIALOG_THEME;
	private SimpleAdapter adaptertakeoff, adapterarrival;
	boolean mMsg9Received;
	String mSearchedString;
	private NetStateReceiver netStateReceiver;
	private static MainActivity instance;
	private Boolean fightlist = true;
	private ArrayList<String> takeoff = new ArrayList<String>();
	private ArrayList<String> arrival = new ArrayList<String>();

	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		one = DptoPx.dip2px(this, 110);
		two = one * 2;
		instance = this;
		init_Intent();
		init_Service();
		// ���ؿؼ�
		init();
		// viewpageʵ��
		init_listview_main();
		mRecognitionListener = new DialogRecognitionListener() {
			@Override
			public void onResults(Bundle results) {
				ArrayList<String> rs = results != null ? results
						.getStringArrayList(RESULTS_RECOGNITION) : null;
				if (rs != null && rs.size() > 0) {
					String str = rs.get(0);
					str = str.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
					edittext_sousuo_input.setText(str);
				}

			}
		};
	}

	private void init_Intent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		takeoff = intent.getStringArrayListExtra("takeoff");
		arrival = intent.getStringArrayListExtra("arrival");
		List<FightInfo> listtakeoff = new ArrayList<FightInfo>();
		List<FightInfo> listarrival = new ArrayList<FightInfo>();
		for (int i = 0; i < takeoff.size(); i++) {
			FightInfo fi = new FightInfo(takeoff.get(i));
			System.out.println("出发航班>>" + fi.getFightnumber());
			listtakeoff.add(fi);
		}
		for (int i = 0; i < arrival.size(); i++) {
			FightInfo fi = new FightInfo(arrival.get(i));
			System.out.println("到达航班>>" + fi.getFightnumber());
			listarrival.add(fi);
		}
		List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listtakeoff.size(); i++) {
			Map<String, Object> listitem = new HashMap<String, Object>();
			listitem.put("code", listtakeoff.get(i).getFightnumber());
			listitem.put("arrival", listtakeoff.get(i).getArrivalcity());
			listitem.put("plantakeoff", listtakeoff.get(i).getPlanTakeoffTime());
			listitem.put("realtakeoff", listtakeoff.get(i).getRealTakeoffTime());
			listitems.add(listitem);
		}
		adaptertakeoff = new SimpleAdapter(this, listitems,
				R.layout.fightlist_row, new String[] { "code", "arrival",
						"plantakeoff", "realtakeoff" }, new int[] {
						R.id.fight_row_code, R.id.fight_row_airport,
						R.id.fight_row_plantime, R.id.fight_row_realtime });
		List<Map<String, Object>> listitems2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listarrival.size(); i++) {
			Map<String, Object> listitem = new HashMap<String, Object>();
			listitem.put("code", listarrival.get(i).getFightnumber());
			listitem.put("takeoff", listarrival.get(i).getTakeoffcity());
			listitem.put("planarrival", listarrival.get(i).getPlanArrivalTime());
			listitem.put("realarrival", listarrival.get(i).getRealArrivalTime());
			listitems2.add(listitem);
		}
		adapterarrival = new SimpleAdapter(this, listitems2,
				R.layout.fightlist_row, new String[] { "code", "takeoff",
						"planarrival", "realarrival" }, new int[] {
						R.id.fight_row_code, R.id.fight_row_airport,
						R.id.fight_row_plantime, R.id.fight_row_realtime });
	}

	private void init_Service() {
		// TODO Auto-generated method stub
		// Intent intentTemp = new Intent(MainActivity.this, ChatService.class);
		// startService(intentTemp);
		// intentTemp = new Intent(MainActivity.this,
		// FriendRequestService.class);
		// startService(intentTemp);
		// MainBodyService.getInstance().onInit(this);
		netStateReceiver = new NetStateReceiver();
		registerReceiver(netStateReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	protected void onDestroy() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		super.onDestroy();
	}

	// *************************************************
	// ��ʼ���ؼ�
	public void init() {
		NetworkService.getInstance().onInit(this);
		NetworkService.getInstance().setupConnection();
		page_now = (ImageView) findViewById(R.id.image_page_now);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		mtab1 = (ImageView) findViewById(R.id.image_first);
		mtab2 = (ImageView) findViewById(R.id.image_second);
		mtab3 = (ImageView) findViewById(R.id.image_three);
		text1 = (TextView) findViewById(R.id.text_first);
		text2 = (TextView) findViewById(R.id.text_second);
		text3 = (TextView) findViewById(R.id.text_three);
		layout = (LinearLayout) findViewById(R.id.main_biji_fenxiang);

	}

	// *************************************************
	// ��ҳ��viewpager���ؼ�����
	public void init_listview_main() {
		text1.setText("机场服务");
		text2.setText("航班信息");
		text3.setText("机场搜索");

		layout.setVisibility(View.VISIBLE);
		mtab1.setOnClickListener(new MyOnClickListener(0));
		mtab2.setOnClickListener(new MyOnClickListener(1));
		mtab3.setOnClickListener(new MyOnClickListener(2));
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener1());
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.jichangfuwu, null);
		View view2 = mLi.inflate(R.layout.hangbandongtai, null);
		View view3 = mLi.inflate(R.layout.sousuo, null);
		edittext_sousuo_input = (EditText) view3
				.findViewById(R.id.edittext_sousuo_input);
		edittext_hangbansearch = (EditText) view2
				.findViewById(R.id.edittext_hangbansearch);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		viewpager.setAdapter(mPagerAdapter);
		textview_dongtai2 = (TextView) view2
				.findViewById(R.id.textview2_dongtai);
		textview_dongtai3 = (TextView) view2
				.findViewById(R.id.textview3_dongtai);
		textview_dongtai4 = (TextView) view2
				.findViewById(R.id.textview4_dongtai);
		listview_hangban = (ListView) view2.findViewById(R.id.listview_hangban);
		listview_hangban.setAdapter(adapterarrival);
		listview_hangban.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FightInfo fi = null;
				if (fightlist) {
					fi = new FightInfo(arrival.get(position));
				} else {
					fi = new FightInfo(takeoff.get(position));
				}
				edittext_hangbansearch.setText(fi.getFightnumber());
			}
		});
	}

	// **********************************************
	// ��ҳ���ͼƬ��ť

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewpager.setCurrentItem(index);
		}

	};

	// **********************************************
	// ��ҳviewpager��������

	public class MyOnPageChangeListener1 implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = null;
			switch (arg0) {
			case 0:
				// ���ͼƬ�仯
				if (currIndex == 1) {
					animation = new TranslateAnimation(0, 0, one, 0);
					// ���ͼƬ�仯
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(0, 0, two, 0);
					// ���ͼƬ�仯
				}
				break;
			case 1:
				// ���ͼƬ�仯
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, 0, 0, one);
					// ���ͼƬ�仯
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(0, 0, two, one);
					// ���ͼƬ�仯
				}
				break;
			case 2:
				// ���ͼƬ�仯
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, 0, 0, two);
					// ���ͼƬ�仯
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(0, 0, one, two);
					// ���ͼƬ�仯
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(150);
			page_now.startAnimation(animation);
		}

	}

	// **********************************************
	// ��ť����
	public void image_userpage(View v) {
		Intent intent = new Intent(MainActivity.this, UserActivity.class);
		startActivity(intent);
	}

	public void image_mappage(View v) {
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		startActivity(intent);
	}

	public void image_scanpage(View v) {
		Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
		startActivity(intent);
	}

	public void button_voice(View v) {
		edittext_sousuo_input.setText(null);
		// if (mDialog == null || mCurrentTheme != Config.DIALOG_THEME) {
		mCurrentTheme = com.airport.util.Config.DIALOG_THEME;
		if (mDialog != null) {
			mDialog.dismiss();
		}
		Bundle params = new Bundle();
		params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
		params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
				Constants.SECRET_KEY);
		params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
				com.airport.util.Config.DIALOG_THEME);
		mDialog = new BaiduASRDigitalDialog(this, params);
		mDialog.setDialogRecognitionListener(mRecognitionListener);
		// }
		mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP,
				com.airport.util.Config.CURRENT_PROP);
		mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
				com.airport.util.Config.getCurrentLanguage());
		Log.e("DEBUG", "Config.PLAY_START_SOUND = "
				+ com.airport.util.Config.PLAY_START_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE,
				com.airport.util.Config.PLAY_START_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE,
				com.airport.util.Config.PLAY_END_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE,
				com.airport.util.Config.DIALOG_TIPS_SOUND);
		mDialog.show();
	}

	public void button_help(View v) {
		Intent intent = new Intent(MainActivity.this, MainHelpActivity.class);
		startActivity(intent);
	}

	public void button_sousuo(View v) {

		String str = edittext_sousuo_input.getText().toString();
		Constants.TempSerchContent = str;
		// Intent intent = new
		// Intent(MainActivity.this,MapActivity.class);
		// intent.putExtra("content", str);
		// startActivity(intent);
		SearchEntity SE = new SearchEntity(SearchEntity.SEARCH_SHOP_BY_LABEL,
				-1, -1, str, str, str);
		startSearch(SE);
	}

	public void button_searchkind1(View v) {
		String str = "E";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind2(View v) {
		String str = "S";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind3(View v) {
		String str = "EE";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind4(View v) {
		String str = "B";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind5(View v) {
		String str = "C";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind6(View v) {
		String str = "Q";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind7(View v) {
		String str = "W";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_searchkind8(View v) {
		String str = "WT";
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		intent.putExtra("content", str);
		startActivity(intent);
	}

	public void button_fightsearch(View v) {
		String str = edittext_hangbansearch.getText().toString();
		SearchEntity SE = new SearchEntity(SearchEntity.SEARCH_FIGHT_BY_CODE,
				str, "-1", "-1");
		System.out.println(str);
		System.out.println(SE);
		startSearch(SE);
	}

	public void button_arrival(View v) {
		fightlist = true;
		listview_hangban.setAdapter(adapterarrival);
		textview_dongtai2.setText("始发地");
		textview_dongtai3.setText("预计到港");
		textview_dongtai4.setText("实际到港");
	}

	public void button_take_off(View v) {
		fightlist = false;
		listview_hangban.setAdapter(adaptertakeoff);
		textview_dongtai2.setText("目的地");
		textview_dongtai3.setText("预计起飞");
		textview_dongtai4.setText("实际起飞");
	}

	// ****************************************************
	public void startSearch(SearchEntity sEnt0) {
		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgSearchPeople,
				sEnt0.toString());
		System.out.println("strsender=" + sEnt0.toString());
		mMsg9Received = false;
		while (!mMsg9Received) {
		}
		String[] arr0 = mSearchedString.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_SHOP_BY_LABEL) {
			gotoShopSearchResult(mSearchedString);
		} else if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_BY_CODE
				|| Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_FROM_AIRPORT
				|| Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_ARRIVAL_AIRPORT) {
			gotoFightSearchResult(mSearchedString);
		}

	}

	private void gotoFightSearchResult(String str) {
		// TODO Auto-generated method stub
		String[] arr0 = str.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_BY_CODE) {
			int num = Integer.parseInt(arr0[1]);
			ArrayList<String> listfight = new ArrayList<String>();
			for (int i = 2; i < num + 2; i++) {
				FightInfo fi = new FightInfo(arr0[i]);
				listfight.add(fi.toString());
			}
			Intent intent = new Intent(MainActivity.this,
					SearchFightResultActivity.class);
			intent.putExtra("listfight", listfight);
			startActivity(intent);
		} else if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_FROM_AIRPORT) {
			int num = Integer.parseInt(arr0[1]);
			ArrayList<FightInfo> listfight = new ArrayList<FightInfo>();
			for (int i = 2; i < num + 2; i++) {
				FightInfo fi = new FightInfo(arr0[i]);
				listfight.add(fi);
			}
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			intent.putExtra("listfightfrom", listfight);
			startActivity(intent);
		} else if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_ARRIVAL_AIRPORT) {
			int num = Integer.parseInt(arr0[1]);
			ArrayList<FightInfo> listfight = new ArrayList<FightInfo>();
			for (int i = 2; i < num + 2; i++) {
				FightInfo fi = new FightInfo(arr0[i]);
				listfight.add(fi);
			}
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			intent.putExtra("listfightarrival", listfight);
			startActivity(intent);
		}
	}

	private void gotoShopSearchResult(String str) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, MapActivity.class);
		String[] arr0 = str.split(GlobalStrings.searchListDivider);
		int num = Integer.parseInt(arr0[1]);
		ArrayList<String> listlabel = new ArrayList<String>();
		for (int i = 2; i < num + 2; i++) {
			ShopInfo si = new ShopInfo(arr0[i]);
			listlabel.add(si.getLabel());
		}
		if (listlabel.size() > 0) {
			intent.putStringArrayListExtra("shopsearchresult", listlabel);
		} else {
			listlabel.add(Constants.TempSerchContent);
			intent.putStringArrayListExtra("content", listlabel);
		}
		for (int i = 0; i < listlabel.size(); i++) {
			System.out.println("label=" + listlabel.get(i));
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
		startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
		overridePendingTransition(R.anim.my_slide_right_in,
				R.anim.my_slide_left_out);

	}

	public void onReceiveSearchList(String msg0) {
		mSearchedString = msg0;
		mMsg9Received = true;
	}

	// *************************************************
	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
			intent.putExtra("out", "out");
			startActivity(intent);
		}
	}
}
