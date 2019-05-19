package com.airport.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airport.exterview.Myview;
import com.airport.R;
import com.airport.util.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sails.engine.LocationRegion;
import com.sails.engine.SAILS;
import com.sails.engine.MarkerManager;
import com.sails.engine.PathRoutingManager;
import com.sails.engine.PinMarkerManager;
import com.sails.engine.SAILSMapView;
import com.sails.engine.core.model.GeoPoint;
import com.sails.engine.overlay.ListOverlay;
import com.sails.engine.overlay.Marker;
import com.sails.engine.overlay.PolygonalChain;
import com.sails.engine.overlay.PolylineWithArrow;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.text.InputFilter.AllCaps;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchresultActivity extends Activity {

	private String str = null;
	private ArrayList<String> arr0 = null;

	static SAILS mSails;
	static SAILSMapView mSailsMapView;
	ImageView zoomin;
	ImageView zoomout;
	ImageView lockcenter;
	Button endRouteButton;
	Button pinMarkerButton;
	TextView distanceView;
	TextView currentFloorDistanceView;
	TextView msgView;
	SlidingMenu menu;
	ActionBar actionBar;
	ExpandableListView expandableListView;
	ExpandableListView expandablelistView2;
	ExpandableAdapter eAdapter;
	ExpandableAdapter eAdapter2;
	Vibrator mVibrator;
	Spinner floorList;
	ArrayAdapter<String> adapter;
	byte zoomSav = 0;
	Paint mpaint = null;
	List<LocationRegion> SAElist = new ArrayList<LocationRegion>();
	List<LocationRegion> trainsitlist = new ArrayList<LocationRegion>();
	List<Integer> trainsitImage = new ArrayList<Integer>();
	LocationRegion templr;
	RelativeLayout banner_unopen;
	RelativeLayout banner_open;
	private Animation animation;
	TextView textview_unopen;
	TextView textview_open;
	LocationRegion lrpointto;
	Button banner_open_settraget;
	Button banner_open_settrainset;
	Button banner_open_setImage;
	Button button_checkallroute;
	List<PathRoutingManager.SwitchFloorInfo> routeinfolist = new ArrayList<PathRoutingManager.SwitchFloorInfo>();
	int currentfloordistance;
	int alldistance;
	int checkcount = 0;
	List<GeoPoint> templist = new ArrayList<GeoPoint>();
	Boolean allroute = false;
	Button banner_unopen_setstart;
	Button banner_unopen_settraget;
	Button banner_unopen_settarinsit;
	Button banner_unopen_setImage;
	RelativeLayout banner_label;
	Button banner_label_eat;
	Button banner_label_shop;
	Button banner_label_enter;
	Button banner_label_info;
	Button banner_label_zhiji;
	FrameLayout SAILSMap_search_result;
	RelativeLayout relativelayout_allitemofmap;
	RelativeLayout RelativeLayout_result;
	List<ArrayList<GeoPoint>> allroutelist = new ArrayList<ArrayList<GeoPoint>>();
	int method = 0;

	private SensorManager manager;

	private SensorListener listener = new SensorListener();

	Myview show;
	int Max_ANGLE = 15;
	int bubblex, bubbley;
	int screenwidth, screenheight;
	SurfaceView sView;
	SurfaceHolder surfaceHolder;
	Camera camera;
	boolean isPreview = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.airport_search_result);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		SAILSMap_search_result = (FrameLayout) findViewById(R.id.SAILSMap_search_result);
		relativelayout_allitemofmap = (RelativeLayout) findViewById(R.id.relativelayout_allitemofmap);
		RelativeLayout_result = (RelativeLayout) findViewById(R.id.RelativeLayout_result);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setFadeDegree(0.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.expantablelist);
		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent)));
		zoomin = (ImageView) findViewById(R.id.zoomin_search);
		zoomout = (ImageView) findViewById(R.id.zoomout_search);
		lockcenter = (ImageView) findViewById(R.id.lockcenter_search);
		endRouteButton = (Button) findViewById(R.id.stopRoute_search);
		pinMarkerButton = (Button) findViewById(R.id.pinMarker_search);
		distanceView = (TextView) findViewById(R.id.distanceView_search);
		distanceView.setVisibility(View.INVISIBLE);
		currentFloorDistanceView = (TextView) findViewById(R.id.currentFloorDistanceView_search);
		currentFloorDistanceView.setVisibility(View.INVISIBLE);
		msgView = (TextView) findViewById(R.id.msgView_search);
		msgView.setVisibility(View.INVISIBLE);
		banner_unopen = (RelativeLayout) findViewById(R.id.banner_unopen_search);
		banner_unopen.setVisibility(View.INVISIBLE);
		banner_open = (RelativeLayout) findViewById(R.id.banner_open_search);
		banner_open.setVisibility(View.INVISIBLE);
		floorList = (Spinner) findViewById(R.id.spinner_search);

		zoomin.setOnClickListener(controlListener);
		zoomout.setOnClickListener(controlListener);
		lockcenter.setOnClickListener(controlListener);
		endRouteButton.setOnClickListener(controlListener);
		endRouteButton.setVisibility(View.INVISIBLE);
		pinMarkerButton.setOnClickListener(controlListener);
		pinMarkerButton.setVisibility(View.VISIBLE);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		expandableListView.setOnChildClickListener(childClickListener);
		expandablelistView2 = (ExpandableListView) findViewById(R.id.expandableListView_search_result);
		expandablelistView2.setOnChildClickListener(childClickListener2);
		animation = AnimationUtils.loadAnimation(SearchresultActivity.this,
				R.anim.bannershow);
		textview_open = (TextView) findViewById(R.id.textvire_open_search);
		textview_unopen = (TextView) findViewById(R.id.textvire_unopen_search);
		banner_open_settraget = (Button) findViewById(R.id.button_open_settraget_search);
		banner_open_settraget.setOnClickListener(controlListener);
		banner_open_settrainset = (Button) findViewById(R.id.button_open_settrainset_search);
		banner_open_settrainset.setOnClickListener(controlListener);
		button_checkallroute = (Button) findViewById(R.id.button_checkallroute_search);
		button_checkallroute.setOnClickListener(controlListener);
		LocationRegion.FONT_LANGUAGE = LocationRegion.NORMAL;
		banner_unopen_setstart = (Button) findViewById(R.id.button_unopen_setstart_search);
		banner_unopen_setstart.setOnClickListener(controlListener);
		banner_unopen_settarinsit = (Button) findViewById(R.id.button_unopen_settrainset_search);
		banner_unopen_settarinsit.setOnClickListener(controlListener);
		banner_unopen_settraget = (Button) findViewById(R.id.button_unopen_settraget_search);
		banner_unopen_settraget.setOnClickListener(controlListener);
		banner_unopen_setImage = (Button) findViewById(R.id.button_unopen_checkdetail_search);
		banner_unopen_setImage.setOnClickListener(controlListener);
		banner_open_setImage = (Button) findViewById(R.id.button_open_checkdetail_search);
		banner_open_setImage.setOnClickListener(controlListener);
		banner_label = (RelativeLayout) findViewById(R.id.banner_label_search);
		banner_label.setVisibility(View.INVISIBLE);
		banner_label_eat = (Button) findViewById(R.id.button_banner_label_eat_search);
		banner_label_eat.setOnClickListener(controlListener);
		banner_label_shop = (Button) findViewById(R.id.button_banner_label_shop_search);
		banner_label_shop.setOnClickListener(controlListener);
		banner_label_info = (Button) findViewById(R.id.button_banner_label_info_search);
		banner_label_info.setOnClickListener(controlListener);
		banner_label_enter = (Button) findViewById(R.id.button_banner_label_enter_search);
		banner_label_enter.setOnClickListener(controlListener);
		banner_label_zhiji = (Button) findViewById(R.id.button_banner_label_zhiji_search);
		banner_label_zhiji.setOnClickListener(controlListener);

		// new a SAILS engine.
		mSails = new SAILS(this);
		// set location mode.
		mSails.setMode(SAILS.WIFI_GFP_IMU);
		// set floor number sort rule from descending to ascending.
		mSails.setReverseFloorList(true);
		// create location change call back.
		mSails.setOnLocationChangeEventListener(new SAILS.OnLocationChangeEventListener() {
			@Override
			public void OnLocationChange() {

				if (mSailsMapView.isCenterLock()
						&& !mSailsMapView.isInLocationFloor()
						&& !mSails.getFloor().equals("")
						&& mSails.isLocationFix()) {
					// set the map that currently location engine recognize.
					mSailsMapView.getMapViewPosition().setZoomLevel((byte) 20);
					mSailsMapView.loadCurrentLocationFloorMap();
					Toast t = Toast.makeText(getBaseContext(),
							mSails.getFloorDescription(mSails.getFloor()),
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});

		mSails.setOnBLEPositionInitialzeCallback(10000,
				new SAILS.OnBLEPositionInitializeCallback() {
					@Override
					public void onStart() {

					}

					@Override
					public void onFixed() {

					}

					@Override
					public void onTimeOut() {
						if (!mSails.checkMode(SAILS.WIFI_GFP_IMU))
							mSails.stopLocatingEngine();
						new AlertDialog.Builder(SearchresultActivity.this)
								.setTitle("Positioning Timeout")
								.setMessage("Put some time out message!")
								.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialoginterface,
													int i) {
												mSailsMapView
														.setMode(SAILSMapView.GENERAL);
											}
										})
								.setPositiveButton("Retry",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												mSails.startLocatingEngine();
											}
										}).show();
					}
				});

		// new and insert a SAILS MapView from layout resource.
		mSailsMapView = new SAILSMapView(this);
		((FrameLayout) findViewById(R.id.SAILSMap_search_result))
				.addView(mSailsMapView);
		// configure SAILS map after map preparation finish.
		mSailsMapView.post(new Runnable() {

			@Override
			public void run() {
				// please change token and building id to your own building
				// project in cloud.
				mSails.loadCloudBuilding("50ff280355064c9eaf728fbd5c782611",
						"551e1d06d98797a814001e18",
						new SAILS.OnFinishCallback() {
							@Override
							public void onSuccess(String response) {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										if (trainsitlist != null
												&& trainsitlist.size() > 1
												&& SAElist != null
												&& SAElist.size() > 1
												&& templist != null
												&& templist.size() > 0) {
											showTemplist();
										}
										mapViewInitial();
										routingInitial();
										slidingMenuInitial();
										resultViewInit();
									}
								});

							}

							@Override
							public void onFailed(String response) {
								Toast t = Toast
										.makeText(
												getBaseContext(),
												"Load cloud project fail, please check network connection.",
												Toast.LENGTH_SHORT);
								t.show();
							}
						});
			}

		});

		show = (Myview) findViewById(R.id.show_search);
		// 获取系统服务（SENSOR_SERVICE)返回一个SensorManager 对象
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenwidth = dm.widthPixels;
		screenheight = dm.heightPixels;
		sView = (SurfaceView) findViewById(R.id.camera_search);
		sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder = sView.getHolder();
		surfaceHolder.addCallback(new Callback() {
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}

			public void surfaceCreated(SurfaceHolder holder) {

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (camera != null) {
					if (isPreview)
						camera.stopPreview();
					camera.release();
					camera = null;
				}
			}
		});
	}

	void resultViewInit() {
		Intent intent = getIntent();
		if (intent.getStringArrayListExtra("shopsearchresult") != null) {
			arr0 = intent.getStringArrayListExtra("shopsearchresult");
			System.out.println("size=" + arr0.size());
			System.out.println("arr0[0]=" + arr0.get(0));
		} 
		if (intent.getStringExtra("content") != null) {
			str = intent.getStringExtra("content");
			System.out.println("存在content>>>>>" + str);
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 1st stage groups
				List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
				// 2nd stage groups
				List<List<Map<String, LocationRegion>>> childs = new ArrayList<List<Map<String, LocationRegion>>>();
				for (String mS : mSails.getFloorNameList()) {
					Map<String, String> group_item = new HashMap<String, String>();
					group_item.put("group", mSails.getFloorDescription(mS));
					groups.add(group_item);

					List<Map<String, LocationRegion>> child_items = new ArrayList<Map<String, LocationRegion>>();
					if (str != null) {
						System.out.println("进入str");
						for (LocationRegion mlr : mSails
								.getLocationRegionList(mS)) {
							if (mlr.getName() == null
									|| mlr.getName().length() == 0)
								continue;
							if (!mlr.getName().contains(str.toUpperCase())) {
								continue;
							}
							if (str.toUpperCase().equals("E")) {
								if (mlr.getName().contains("EL")
										|| mlr.getName().contains("ES")) {
									continue;
								}
							}
							if (str.toUpperCase().equals("S")) {
								if (mlr.getName().contains("ES")
										|| mlr.getName().contains("SC")) {
									continue;
								}
							}
							if (str.toUpperCase().equals("EE")) {
								if (!mlr.getName().contains("ES")
										&& !mlr.getName().contains("SC")) {
									continue;
								}
							}
							if (str.toUpperCase().equals("W")) {
								if (mlr.getName().contains("WT")) {
									continue;
								}
							}

							if (mlr.chinese_s != null) {
								System.out.println(mlr.chinese_s);
								if (!mlr.chinese_s.contains(str)) {
									continue;
								}
							}

							Map<String, LocationRegion> childData = new HashMap<String, LocationRegion>();
							childData.put("child", mlr);
							child_items.add(childData);
						}
					}
					if (arr0 != null) {

						for (LocationRegion mlr : mSails
								.getLocationRegionList(mS)) {
							for (String str1 : arr0) {
								if (mlr.getName() == null
										|| mlr.getName().length() == 0)
									continue;
								if (!mlr.getName().equals(str1)) {
									continue;
								}

								if (mlr.chinese_s != null) {
									System.out.println(mlr.chinese_s);
									if (!mlr.chinese_s.equals(str1)) {
										continue;
									}
								}

								Map<String, LocationRegion> childData = new HashMap<String, LocationRegion>();
								childData.put("child", mlr);
								child_items.add(childData);
							}

						}

					}
					childs.add(child_items);
				}
				eAdapter2 = new ExpandableAdapter(getBaseContext(), groups,
						childs);
				expandablelistView2.setAdapter(eAdapter2);
			}
		});

	}

	void mapViewInitial() {
		// establish a connection of SAILS engine into SAILS MapView.
		mSailsMapView.setSAILSEngine(mSails);

		// set location pointer icon.
		mSailsMapView.setLocationMarker(R.drawable.circle, R.drawable.arrow,
				null, 35);

		// set location marker visible.
		mSailsMapView.setLocatorMarkerVisible(true);

		// load first floor map in package.
		mSailsMapView.loadFloorMap(mSails.getFloorNameList().get(0));
		actionBar.setTitle("Map POIs");

		// Auto Adjust suitable map zoom level and position to best view
		// position.
		mSailsMapView.autoSetMapZoomAndView();

		// set location region click call back.
		mSailsMapView
				.setOnRegionClickListener(new SAILSMapView.OnRegionClickListener() {
					@Override
					public void onClick(List<LocationRegion> locationRegions) {
						LocationRegion lr = locationRegions.get(0);

						if (mSails.isLocationEngineStarted()) {

							banner_open.setVisibility(View.VISIBLE);
							banner_open.startAnimation(animation);
							if (lr.type != null) {
								if (lr.subtype != null) {
									textview_open.setText(lr.getName() + "-"
											+ lr.type + "-" + lr.subtype);
								} else {
									textview_open.setText(lr.getName() + "-"
											+ lr.type);
								}
							} else {
								textview_open.setText(lr.getName());
							}
							// textview_open.setText(lr.getName());
							lrpointto = lr;

						} else {
							banner_unopen.setVisibility(View.VISIBLE);
							banner_unopen.startAnimation(animation);
							if (lr.type != null) {
								if (lr.subtype != null) {
									textview_unopen.setText(lr.getName() + "-"
											+ lr.type + "-" + lr.subtype);
								} else {
									textview_unopen.setText(lr.getName() + "-"
											+ lr.type);
								}
							} else {
								textview_unopen.setText(lr.getName());
							}
							lrpointto = lr;
						}
					}
				});

		mSailsMapView.getPinMarkerManager().setOnPinMarkerClickCallback(
				new PinMarkerManager.OnPinMarkerClickCallback() {
					@Override
					public void OnClick(
							MarkerManager.LocationRegionMarker locationRegionMarker) {
						Toast.makeText(
								getApplication(),
								"("
										+ Double.toString(locationRegionMarker.locationRegion
												.getCenterLatitude())
										+ ","
										+ Double.toString(locationRegionMarker.locationRegion
												.getCenterLongitude()) + ")",
								Toast.LENGTH_SHORT).show();
					}
				});

		// set location region long click call back.
		mSailsMapView
				.setOnRegionLongClickListener(new SAILSMapView.OnRegionLongClickListener() {
					@Override
					public void onLongClick(List<LocationRegion> locationRegions) {// 已开启引擎
						LocationRegion lr = locationRegions.get(0);
						mVibrator.vibrate(70);
						if (mSails.isLocationEngineStarted()) {
							if (SAElist != null && SAElist.size() > 1
									&& SAElist.get(1) == lr) {
								if (trainsitlist != null
										&& trainsitlist.size() >= 1) {
									if (trainsitlist.size() == 1) {
										method1(PathRoutingManager.MY_LOCATION,
												lr, SAElist, trainsitlist);
									} else {
										method2(PathRoutingManager.MY_LOCATION,
												lr, SAElist, trainsitlist);
									}
								} else {
									method3();
								}
							} else {
								methob4(PathRoutingManager.MY_LOCATION, lr,
										SAElist);
							}
						} else {
							methob5(lr);
						}
					}
				});

		// design some action in floor change call back.
		mSailsMapView
				.setOnFloorChangedListener(new SAILSMapView.OnFloorChangedListener() {
					@Override
					public void onFloorChangedBefore(String floorName) {
						// get current map view zoom level.
						zoomSav = mSailsMapView.getMapViewPosition()
								.getZoomLevel();

					}

					@Override
					public void onFloorChangedAfter(final String floorName) {
						Runnable r = new Runnable() {
							@Override
							public void run() {
								// check is locating engine is start and current
								// brows map is in the locating floor or not.
								if (mSails.isLocationEngineStarted()
										&& mSailsMapView.isInLocationFloor()) {
									// change map view zoom level with
									// animation.
									mSailsMapView.setAnimationToZoom(zoomSav);
								}
							}
						};
						new Handler().postDelayed(r, 1000);

						int position = 0;
						for (String mS : mSails.getFloorNameList()) {
							if (mS.equals(floorName))
								break;
							position++;
						}
					}

				});

		// design some action in mode change call back.
		mSailsMapView
				.setOnModeChangedListener(new SAILSMapView.OnModeChangedListener() {
					@Override
					public void onModeChanged(int mode) {
						if (((mode & SAILSMapView.LOCATION_CENTER_LOCK) == SAILSMapView.LOCATION_CENTER_LOCK)
								&& ((mode & SAILSMapView.FOLLOW_PHONE_HEADING) == SAILSMapView.FOLLOW_PHONE_HEADING)) {
							lockcenter.setImageDrawable(getResources()
									.getDrawable(R.drawable.center3));

						} else if ((mode & SAILSMapView.LOCATION_CENTER_LOCK) == SAILSMapView.LOCATION_CENTER_LOCK) {
							lockcenter.setImageDrawable(getResources()
									.getDrawable(R.drawable.center2));
						} else {
							lockcenter.setImageDrawable(getResources()
									.getDrawable(R.drawable.center1));
						}
					}
				});

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mSails.getFloorDescList());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		floorList.setAdapter(adapter);
		floorList
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (!mSailsMapView.getCurrentBrowseFloorName().equals(
								mSails.getFloorNameList().get(position)))
							mSailsMapView.loadFloorMap(mSails
									.getFloorNameList().get(position));
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
	}

	void routingInitial() {
		mSailsMapView.getRoutingManager().setStartMakerDrawable(
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.start_point)));
		mSailsMapView.getRoutingManager().setTargetMakerDrawable(
				Marker.boundCenterBottom(getResources().getDrawable(
						R.drawable.map_destination)));

		mSailsMapView.getRoutingManager().setOnRoutingUpdateListener(

		new PathRoutingManager.OnRoutingUpdateListener() {

			@Override
			public void onArrived(LocationRegion targetRegion) {
				Toast.makeText(getApplication(), "Arrive.", Toast.LENGTH_SHORT)
						.show();
				if (method == 21) {
					if (trainsitlist.size() == 1) {
						method10(trainsitlist.get(0), SAElist.get(1),
								mSailsMapView.getRoutingManager()
										.getPathPaint());
						trainsitlist.clear();
						trainsitImage.clear();
						checkcount = 0;
					} else if (trainsitlist.size() > 1) {
						method10(trainsitlist.get(0), trainsitlist.get(1),
								mSailsMapView.getRoutingManager()
										.getPathPaint());
						for (int i = 0; i < trainsitlist.size() - 1; i++) {
							trainsitlist.set(i, trainsitlist.get(i + 1));
							trainsitImage.set(i, trainsitImage.get(i + 1));
						}
						checkcount = 0;
					}
				} else if (method == 23) {
					if (trainsitlist.size() == 1) {
						method10(trainsitlist.get(0), SAElist.get(1),
								mSailsMapView.getRoutingManager()
										.getPathPaint());
						trainsitlist.clear();
						trainsitImage.clear();
						allroutelist.clear();
					} else if (trainsitlist.size() > 1) {
						method10(trainsitlist.get(0), trainsitlist.get(1),
								mSailsMapView.getRoutingManager()
										.getPathPaint());
						for (int i = 0; i < trainsitlist.size() - 1; i++) {
							trainsitlist.set(i, trainsitlist.get(i + 1));
							trainsitlist.remove(trainsitlist.size() - 1);
							trainsitImage.set(i, trainsitImage.get(i + 1));
							trainsitImage.remove(trainsitImage.size() - 1);
						}
						for (int i = 0; i < allroutelist.size() - 1; i++) {
							allroutelist.set(i, allroutelist.get(i + 1));
							allroutelist.remove(allroutelist.size() - 1);
						}
						templist.clear();
						for (int i = 0; i < allroutelist.size(); i++) {
							for (int x = 0; x < allroutelist.get(i).size(); x++) {
								templist.add(allroutelist.get(i).get(x));
							}
						}
						showTemplist();
					}
				}

			}

			@Override
			public void onRouteSuccess() {
				List<GeoPoint> gplist = mSailsMapView.getRoutingManager()
						.getCurrentFloorRoutingPathNodes();
				mSailsMapView.autoSetMapZoomAndView(gplist);

			}

			@Override
			public void onRouteFail() {
				Toast.makeText(getApplication(), "Route Fail.",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPathDrawFinish() {

			}

			@Override
			public void onTotalDistanceRefresh(int distance) {

				distanceView.setText("Total Routing Distance: "
						+ Integer.toString(distance) + " (m)");
				alldistance = distance;
			}

			@Override
			public void onReachNearestTransferDistanceRefresh(int distance,
					int nodeType) {
				currentfloordistance = distance;
				switch (nodeType) {
				case PathRoutingManager.SwitchFloorInfo.ELEVATOR:
					currentFloorDistanceView
							.setText("To Nearest Elevator Distance: "
									+ Integer.toString(distance) + " (m)");
					break;
				case PathRoutingManager.SwitchFloorInfo.ESCALATOR:
					currentFloorDistanceView
							.setText("To Nearest Escalator Distance: "
									+ Integer.toString(distance) + " (m)");
					break;
				case PathRoutingManager.SwitchFloorInfo.STAIR:
					currentFloorDistanceView
							.setText("To Nearest Stair Distance: "
									+ Integer.toString(distance) + " (m)");
					break;
				case PathRoutingManager.SwitchFloorInfo.DESTINATION:
					currentFloorDistanceView
							.setText("To Destination Distance: "
									+ Integer.toString(distance) + " (m)");
					break;
				}
				if (distance <= 5) {
					mSailsMapView.setVisibility(View.INVISIBLE);
					sView.setVisibility(View.VISIBLE);
					// imageView.setVisibility(View.VISIBLE);
					show.setVisibility(View.VISIBLE);
					initCamera();
				}
			}

			@Override
			public void onSwitchFloorInfoRefresh(
					List<PathRoutingManager.SwitchFloorInfo> infoList,
					int nearestIndex) {
				routeinfolist.clear();
				// set markers for every transfer location
				for (PathRoutingManager.SwitchFloorInfo mS : infoList) {

					routeinfolist.add(mS);

					if (mS.direction != PathRoutingManager.SwitchFloorInfo.GO_TARGET)
						mSailsMapView
								.getMarkerManager()
								.setLocationRegionMarker(
										mS.fromBelongsRegion,
										Marker.boundCenter(getResources()
												.getDrawable(
														R.drawable.transfer_point)));
				}

				for (int i = 0; i < trainsitlist.size(); i++) {
					if (trainsitImage.get(i) == 1) {
						mSailsMapView
								.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(
														R.drawable.transfer_point)));
					} else if (trainsitImage.get(i) == 2) {
						mSailsMapView.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(R.drawable.eat)));
					} else if (trainsitImage.get(i) == 3) {
						mSailsMapView.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(R.drawable.shop)));
					} else if (trainsitImage.get(i) == 4) {
						mSailsMapView.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(R.drawable.info)));
					} else if (trainsitImage.get(i) == 5) {
						mSailsMapView
								.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(R.drawable.enter)));
					} else if (trainsitImage.get(i) == 6) {
						mSailsMapView
								.getMarkerManager()
								.setLocationRegionMarker(
										trainsitlist.get(i),
										Marker.boundCenter(getResources()
												.getDrawable(R.drawable.zhiji)));
					}
				}
				if (nearestIndex == -1)
					return;

				PathRoutingManager.SwitchFloorInfo sf = infoList
						.get(nearestIndex);

				switch (sf.nodeType) {
				case PathRoutingManager.SwitchFloorInfo.ELEVATOR:
					if (sf.direction == PathRoutingManager.SwitchFloorInfo.UP)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					else if (sf.direction == PathRoutingManager.SwitchFloorInfo.DOWN)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					break;

				case PathRoutingManager.SwitchFloorInfo.ESCALATOR:
					if (sf.direction == PathRoutingManager.SwitchFloorInfo.UP)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					else if (sf.direction == PathRoutingManager.SwitchFloorInfo.DOWN)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					break;

				case PathRoutingManager.SwitchFloorInfo.STAIR:
					if (sf.direction == PathRoutingManager.SwitchFloorInfo.UP)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					else if (sf.direction == PathRoutingManager.SwitchFloorInfo.DOWN)
						msgView.setText("导航提示: \n 请搭乘电梯至"
								+ mSails.getFloorDescription(sf.toFloorname));
					break;

				case PathRoutingManager.SwitchFloorInfo.DESTINATION:
					msgView.setText("导航提示: \n 前往"
							+ sf.fromBelongsRegion.getName());
					break;
				}

			}
		});
	}

	void slidingMenuInitial() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// 1st stage groups
				List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
				// 2nd stage groups
				List<List<Map<String, LocationRegion>>> childs = new ArrayList<List<Map<String, LocationRegion>>>();
				for (String mS : mSails.getFloorNameList()) {
					Map<String, String> group_item = new HashMap<String, String>();
					group_item.put("group", mSails.getFloorDescription(mS));
					groups.add(group_item);

					List<Map<String, LocationRegion>> child_items = new ArrayList<Map<String, LocationRegion>>();
					for (LocationRegion mlr : mSails.getLocationRegionList(mS)) {
						if (mlr.getName() == null
								|| mlr.getName().length() == 0)
							continue;

						Map<String, LocationRegion> childData = new HashMap<String, LocationRegion>();
						childData.put("child", mlr);
						child_items.add(childData);
					}
					childs.add(child_items);
				}
				eAdapter = new ExpandableAdapter(getBaseContext(), groups,
						childs);
				expandableListView.setAdapter(eAdapter);
			}
		});
	}

	ExpandableListView.OnChildClickListener childClickListener = new ExpandableListView.OnChildClickListener() {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

			LocationRegion lr = eAdapter.childs.get(groupPosition)
					.get(childPosition).get("child");

			if (!lr.getFloorName().equals(
					mSailsMapView.getCurrentBrowseFloorName())) {
				mSailsMapView.loadFloorMap(lr.getFloorName());
				mSailsMapView.getMapViewPosition().setZoomLevel((byte) 19);
				Toast.makeText(getBaseContext(),
						mSails.getFloorDescription(lr.getFloorName()),
						Toast.LENGTH_SHORT).show();
			}
			GeoPoint poi = new GeoPoint(lr.getCenterLatitude(),
					lr.getCenterLongitude());
			mSailsMapView.setAnimationMoveMapTo(poi);
			menu.showContent();
			return false;
		}
	};
	ExpandableListView.OnChildClickListener childClickListener2 = new ExpandableListView.OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			LocationRegion lr = eAdapter2.childs.get(groupPosition)
					.get(childPosition).get("child");

			if (!lr.getFloorName().equals(
					mSailsMapView.getCurrentBrowseFloorName())) {
				mSailsMapView.loadFloorMap(lr.getFloorName());
				mSailsMapView.getMapViewPosition().setZoomLevel((byte) 19);
				Toast.makeText(getBaseContext(),
						mSails.getFloorDescription(lr.getFloorName()),
						Toast.LENGTH_SHORT).show();
			}
			GeoPoint poi = new GeoPoint(lr.getCenterLatitude(),
					lr.getCenterLongitude());
			mSailsMapView.setAnimationMoveMapTo(poi);
			RelativeLayout_result.setVisibility(View.INVISIBLE);
			relativelayout_allitemofmap.setVisibility(View.VISIBLE);
			SAILSMap_search_result.setVisibility(View.VISIBLE);

			return false;
		}
	};

	View.OnClickListener controlListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == zoomin) {
				// set map zoomin function.
				mSailsMapView.zoomIn();
			} else if (v == zoomout) {
				// set map zoomout function.
				mSailsMapView.zoomOut();
			} else if (v == lockcenter) {
				if (!mSails.isLocationFix()
						|| !mSails.isLocationEngineStarted()) {
					Toast t = Toast.makeText(getBaseContext(),
							"Location Not Found or Location Engine Turn Off.",
							Toast.LENGTH_SHORT);
					t.show();
					return;
				}
				if (!mSailsMapView.isCenterLock()
						&& !mSailsMapView.isInLocationFloor()) {
					// set the map that currently location engine recognize.
					mSailsMapView.loadCurrentLocationFloorMap();

					Toast t = Toast.makeText(getBaseContext(),
							"Go Back to Locating Floor First.",
							Toast.LENGTH_SHORT);
					t.show();
					return;
				}

				if (mSailsMapView.isCenterLock()) {
					if ((mSailsMapView.getMode() & SAILSMapView.FOLLOW_PHONE_HEADING) == SAILSMapView.FOLLOW_PHONE_HEADING)
						// if map control mode is follow phone heading, then set
						// mode to location center lock when button click.
						mSailsMapView.setMode(mSailsMapView.getMode()
								& ~SAILSMapView.FOLLOW_PHONE_HEADING);
					else
						// if map control mode is location center lock, then set
						// mode to follow phone heading when button click.
						mSailsMapView.setMode(mSailsMapView.getMode()
								| SAILSMapView.FOLLOW_PHONE_HEADING);
				} else {
					// if map control mode is none, then set mode to loction
					// center lock when button click.
					mSailsMapView.setMode(mSailsMapView.getMode()
							| SAILSMapView.LOCATION_CENTER_LOCK);
				}
			} else if (v == endRouteButton) {
				endRouteButton.setVisibility(View.INVISIBLE);
				distanceView.setVisibility(View.INVISIBLE);
				currentFloorDistanceView.setVisibility(View.INVISIBLE);
				msgView.setVisibility(View.INVISIBLE);
				// end route.
				mSailsMapView.getRoutingManager().disableHandler();

			} else if (v == pinMarkerButton) {
				Toast.makeText(getApplication(),
						"Please Touch Map and Set PinMarker.",
						Toast.LENGTH_SHORT).show();
				mSailsMapView
						.getPinMarkerManager()
						.setOnPinMarkerGenerateCallback(
								Marker.boundCenterBottom(getResources()
										.getDrawable(R.drawable.parking_target)),
								new PinMarkerManager.OnPinMarkerGenerateCallback() {
									@Override
									public void OnGenerate(
											MarkerManager.LocationRegionMarker locationRegionMarker) {
										Toast.makeText(getApplication(),
												"One PinMarker Generated.",
												Toast.LENGTH_SHORT).show();
									}
								});
			} else if (v == banner_open_settraget) {
				methob6(PathRoutingManager.MY_LOCATION, lrpointto, SAElist);
			} else if (v == banner_open_settrainset) {
				method7();
			} else if (v == button_checkallroute) {
				// System.out.println("checkcount=" + checkcount);
				if (mSails.isLocationEngineStarted()) {
					if (templr == null) {
						if (checkcount < trainsitlist.size()) {
							if (trainsitlist != null && trainsitlist.size() > 0) {
								method21();// 开启定位第一次check不保存记录
							}
						} else if (checkcount > trainsitlist.size()) {
							if (trainsitlist != null && trainsitlist.size() > 0) {
								method23();// 开启定位循环check记录最后一条allroutelist.get(trainsit.size())
							} else {
								method24();// 开启定位循环无中转点
							}
						} else {
							method22();// 开启定位无中转点
						}
					} else if (templr != null) {
						for (int i = 0; i < trainsitlist.size(); i++) {
							if (templr == trainsitlist.get(i)) {
								if (i == trainsitlist.size() - 1) {
									if (checkcount <= trainsitlist.size()) {
										method25();// 开启定位，第一次最后一个中转点到目的地，保存allroutelist.get(trainsit.get(i));保存倒数第二条
										break;
									} else if (checkcount > trainsitlist.size()) {
										method26();// 开启定位，循环最后一个中转点到目的地
										break;
									}
								} else {
									if (checkcount < trainsitlist.size()) {
										method27(i);// 开启定位，记录中转点之间的路径保存allroutelist.get(trainsit.get(i)）;
										break;
									} else if (checkcount > trainsitlist.size()) {
										method28(i);// 开启定位，不保存
										break;
									}
								}
							}
						}
					}
				} else {
					if (SAElist.size() == 0 || SAElist.size() == 1) {

					} else if (trainsitlist.size() == 0) {
						method41();//
					} else if (templr == null) {
						if (checkcount == 0) {
							method42();// 不保存
						} else {
							method43();// 保存最后allroutelist.get(trainsit.size())
						}
					} else if (templr == trainsitlist
							.get(trainsitlist.size() - 1)) {
						if (checkcount == trainsitlist.size()) {
							method44();// 保存倒数第二项allroutelist.get(trainsit.get(i));
						} else {
							method45();
						}
					} else {
						for (int i = 0; i < trainsitlist.size(); i++) {
							if (templr == trainsitlist.get(i)) {
								if (checkcount < trainsitlist.size()) {
									method46(i);// 保存第一项及中转点之间项allroutelist.get(trainsit.get(i))
									break;
								} else {
									method47(i);
									break;
								}
							}
						}
					}
				}

			} else if (v == banner_unopen_setstart) {
				method31();
			} else if (v == banner_unopen_settarinsit) {
				method33();
			} else if (v == banner_unopen_settraget) {
				method32();
			} else if (v == banner_unopen_setImage) {
				method34();
			} else if (v == banner_open_setImage) {
				method35();
			} else if (v == banner_label_eat) {
				method51();
				banner_label.setVisibility(View.INVISIBLE);
			} else if (v == banner_label_shop) {
				method52();
				banner_label.setVisibility(View.INVISIBLE);
			} else if (v == banner_label_info) {
				method53();
				banner_label.setVisibility(View.INVISIBLE);
			} else if (v == banner_label_enter) {
				method54();
				banner_label.setVisibility(View.INVISIBLE);
			} else if (v == banner_label_zhiji) {
				method55();
				banner_label.setVisibility(View.INVISIBLE);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			if (menu.isMenuShowing())
				menu.showContent();
			else
				menu.showMenu();

			// collapse all expandable groups.
			if (eAdapter != null) {
				for (int i = 0; i < eAdapter.groups.size(); i++)
					expandableListView.collapseGroup(i);
			}

			return true;

		case R.id.start_location_engine:
			if (!mSails.isLocationEngineStarted()) {
				mSails.startLocatingEngine();
				mSailsMapView.setLocatorMarkerVisible(true);
				Toast.makeText(this, "Start Location Engine",
						Toast.LENGTH_SHORT).show();
				mSailsMapView.setMode(SAILSMapView.LOCATION_CENTER_LOCK
						| SAILSMapView.FOLLOW_PHONE_HEADING);
				lockcenter.setVisibility(View.VISIBLE);
				endRouteButton.setVisibility(View.INVISIBLE);
				checkcount = 0;
				templist = new ArrayList<GeoPoint>();
			}

			return true;

		case R.id.stop_location_engine:
			if (mSails.isLocationEngineStarted()) {
				mSails.stopLocatingEngine();
				mSailsMapView.setLocatorMarkerVisible(false);
				mSailsMapView.setMode(SAILSMapView.GENERAL);
				mSailsMapView.getRoutingManager().disableHandler();
				pinMarkerButton.setVisibility(View.VISIBLE);
				endRouteButton.setVisibility(View.INVISIBLE);
				distanceView.setVisibility(View.INVISIBLE);
				currentFloorDistanceView.setVisibility(View.INVISIBLE);
				msgView.setVisibility(View.INVISIBLE);
				Toast.makeText(this, "Stop Location Engine", Toast.LENGTH_SHORT)
						.show();
				checkcount = 0;
				templist = new ArrayList<GeoPoint>();

			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		mSailsMapView.onResume();
		/**
		 * 获取方向传感器 通过SensorManager对象获取相应的Sensor类型的对象
		 */
		Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// 应用在前台时候注册监听器
		manager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		mSailsMapView.onPause();
		manager.unregisterListener(listener);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
		((FrameLayout) findViewById(R.id.SAILSMap_search_result))
				.removeAllViews();
	}

	class ExpandableAdapter extends BaseExpandableListAdapter {

		private Context context;
		List<Map<String, String>> groups;
		List<List<Map<String, LocationRegion>>> childs;

		public ExpandableAdapter(Context context,
				List<Map<String, String>> groups,
				List<List<Map<String, LocationRegion>>> childs) {
			this.context = context;
			this.groups = groups;
			this.childs = childs;
		}

		@Override
		public int getGroupCount() {
			return groups.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childs.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groups.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childs.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
					R.layout.group, null);
			String text = ((Map<String, String>) getGroup(groupPosition))
					.get("group");
			TextView tv = (TextView) linearLayout.findViewById(R.id.group_tv);
			tv.setText(text);
			linearLayout.setBackgroundColor(getResources().getColor(
					android.R.color.holo_blue_dark));
			tv.setTextColor(getResources().getColor(android.R.color.white));
			return linearLayout;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
					R.layout.child, null);
			LocationRegion lr = ((Map<String, LocationRegion>) getChild(
					groupPosition, childPosition)).get("child");
			TextView tv = (TextView) linearLayout.findViewById(R.id.child_tv);
			tv.setText(lr.getName());
			ImageView imageView = (ImageView) linearLayout
					.findViewById(R.id.child_iv);
			imageView.setImageResource(R.drawable.expand_item);
			return linearLayout;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	public void method1(LocationRegion now, LocationRegion pointto,
			List<LocationRegion> sae, List<LocationRegion> trainsits) {
		method = 1;
		System.out.println("enter to method1");
		mSailsMapView.getRoutingManager().setStartRegion(now);
		mSailsMapView.getRoutingManager().setTargetRegion(
				trainsits.get(trainsits.size() - 1));
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				trainsits.get(trainsits.size() - 1),
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.destination)));
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		endRouteButton.setVisibility(View.VISIBLE);
		currentFloorDistanceView.setVisibility(View.VISIBLE);
		msgView.setVisibility(View.VISIBLE);
		if (mSailsMapView.getRoutingManager().enableHandler())
			distanceView.setVisibility(View.VISIBLE);
		SAElist.clear();
		SAElist.add(now);
		SAElist.add(trainsits.get(trainsits.size() - 1));
		trainsitlist.clear();
		trainsitImage.clear();
		allroutelist.clear();
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();
	}

	public void method2(LocationRegion now, LocationRegion pointto,
			List<LocationRegion> sae, List<LocationRegion> trainsits) {
		method = 2;
		System.out.println("enter to method2");
		for (int i = 0; i < trainsits.size() - 1; i++) {

		}
		mSailsMapView.getRoutingManager().setStartRegion(now);
		mSailsMapView.getRoutingManager().setTargetRegion(
				trainsits.get(trainsits.size() - 1));
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				trainsits.get(trainsits.size() - 1),
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.destination)));
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		endRouteButton.setVisibility(View.VISIBLE);
		currentFloorDistanceView.setVisibility(View.VISIBLE);
		msgView.setVisibility(View.VISIBLE);
		if (mSailsMapView.getRoutingManager().enableHandler())
			distanceView.setVisibility(View.VISIBLE);
		SAElist.clear();
		SAElist.add(now);
		SAElist.add(trainsits.get(trainsits.size() - 1));
		List<LocationRegion> temp = new ArrayList<LocationRegion>();
		for (int i = 0; i < trainsits.size(); i++) {
			temp.add(trainsits.get(i));
		}
		trainsits.clear();
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();

		for (int i = 0; i < temp.size() - 1; i++) {
			trainsits.add(temp.get(i));
		}
		trainsitlist = trainsits;

	}

	public void method3() {
		method = 3;
		System.out.println("enter to method3");
		endRouteButton.setVisibility(View.INVISIBLE);
		distanceView.setVisibility(View.INVISIBLE);
		currentFloorDistanceView.setVisibility(View.INVISIBLE);
		msgView.setVisibility(View.INVISIBLE);
		mSailsMapView.getRoutingManager().disableHandler();
	}

	public void methob4(LocationRegion now, LocationRegion pointto,
			List<LocationRegion> sae) {
		method = 4;
		System.out.println("enter to method4");
		mSailsMapView.getRoutingManager().setStartRegion(now);
		mSailsMapView.getRoutingManager().setTargetRegion(pointto);
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				pointto,
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.destination)));
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		endRouteButton.setVisibility(View.VISIBLE);
		currentFloorDistanceView.setVisibility(View.VISIBLE);
		msgView.setVisibility(View.VISIBLE);
		if (mSailsMapView.getRoutingManager().enableHandler())
			distanceView.setVisibility(View.VISIBLE);
		SAElist.clear();
		SAElist.add(now);
		SAElist.add(pointto);
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();
	}

	public void methob5(LocationRegion pointto) {
		method = 5;
		System.out.println("enter to method5");
		mVibrator.vibrate(70);
		mSailsMapView.getMarkerManager().clear();
		mSailsMapView.getRoutingManager().setStartRegion(pointto);
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				pointto,
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.start_point)));
		SAElist.clear();
		SAElist.add(pointto);
		trainsitlist.clear();
		trainsitImage.clear();
		allroutelist.clear();
	}

	public void methob6(LocationRegion lrnow, LocationRegion lrpoint,
			List<LocationRegion> SAE) {
		method = 6;
		System.out.println("enter to method6");
		mSailsMapView.getRoutingManager().setStartRegion(lrnow);
		mSailsMapView.getRoutingManager().setTargetRegion(lrpoint);
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				lrpoint,
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.destination)));
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		endRouteButton.setVisibility(View.VISIBLE);
		currentFloorDistanceView.setVisibility(View.VISIBLE);
		msgView.setVisibility(View.VISIBLE);
		if (mSailsMapView.getRoutingManager().enableHandler())
			distanceView.setVisibility(View.VISIBLE);
		SAElist.clear();
		SAElist.add(lrnow);
		SAElist.add(lrpoint);
		trainsitlist.clear();
		trainsitImage.clear();
		allroutelist.clear();
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();
		banner_open.setVisibility(View.INVISIBLE);
	}

	public void method7() {
		method = 7;
		System.out.println("enter to method7");

		if (SAElist.size() > 1 && SAElist.get(1) != null) {
			trainsitlist.add(lrpointto);
			trainsitImage.add(1);

			banner_open.setVisibility(View.INVISIBLE);
		} else {
			mSailsMapView.getRoutingManager().setTargetRegion(lrpointto);
			mSailsMapView.getRoutingManager().setStartRegion(
					PathRoutingManager.MY_LOCATION);
			mSailsMapView.getRoutingManager().getPathPaint()
					.setColor(0xFF35b3e5);

			endRouteButton.setVisibility(View.VISIBLE);
			currentFloorDistanceView.setVisibility(View.VISIBLE);
			msgView.setVisibility(View.VISIBLE);
			if (mSailsMapView.getRoutingManager().enableHandler())
				distanceView.setVisibility(View.VISIBLE);

			mSailsMapView.getMarkerManager().setLocationRegionMarker(
					lrpointto,
					Marker.boundCenter(getResources().getDrawable(
							R.drawable.destination)));
			banner_open.setVisibility(View.INVISIBLE);
			SAElist.clear();
			SAElist.add(PathRoutingManager.MY_LOCATION);
			SAElist.add(lrpointto);
		}

	}

	public void method10(LocationRegion start, LocationRegion end, Paint paint) {
		System.out.println("enter to method10");
		mSailsMapView.getRoutingManager().setStartRegion(start);
		mSailsMapView.getRoutingManager().setTargetMakerDrawable(
				Marker.boundCenterBottom(getResources().getDrawable(
						R.drawable.map_destination)));
		mSailsMapView.getRoutingManager().getPathPaint().set(paint);
		mSailsMapView.getRoutingManager().setTargetRegion(end);

		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF85b038);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		if (mSailsMapView.getRoutingManager().enableHandler()) {
			distanceView.setVisibility(View.VISIBLE);
		}
		List<GeoPoint> gplist = mSailsMapView.getRoutingManager()
				.getCurrentFloorRoutingPathNodes();
		mSailsMapView.autoSetMapZoomAndView(gplist);
	}

	public List<GeoPoint> saveTemplist() {
		System.out.println("enter ro method saveTemplist");
		List<GeoPoint> temp = new ArrayList<GeoPoint>();
		for (GeoPoint GP : mSailsMapView.getRoutingManager()
				.getCurrentFloorRoutingPathNodes()) {
			temp.add(GP);
		}
		return temp;
	}

	public void showTemplist() {
		System.out.println("enter ro method showTemplist");
		ListOverlay listOverlay = new ListOverlay();
		PolylineWithArrow pathDraw = new PolylineWithArrow(new PolygonalChain(
				null), mSailsMapView.getRoutingManager().getPathPaint(), 30);
		PolygonalChain mPcWay = new PolygonalChain(templist);
		System.out.println(templist);
		pathDraw.setPolygonalChain(mPcWay);
		listOverlay.getOverlayItems().add(pathDraw);
		mSailsMapView.getOverlays().add(listOverlay);
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		mSailsMapView.redraw();
	}

	public void method21() {
		method = 21;
		System.out.println("enter to method21");
		checkcount = checkcount + 1;
		templr = trainsitlist.get(0);
		method10(PathRoutingManager.MY_LOCATION, trainsitlist.get(0),
				mSailsMapView.getRoutingManager().getPathPaint());
	}

	public void method22() {
		method = 22;
		System.out.println("enter to method22");
		method10(PathRoutingManager.MY_LOCATION, SAElist.get(1), mSailsMapView
				.getRoutingManager().getPathPaint());
		templr = null;
	}

	public void method23() {
		method = 23;
		System.out.println("enter to method23");
		checkcount = checkcount + 1;
		templr = trainsitlist.get(0);
		method10(PathRoutingManager.MY_LOCATION, trainsitlist.get(0),
				mSailsMapView.getRoutingManager().getPathPaint());
		if (!allroute) {
			allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		}
		System.out.println(allroutelist.size());
		templist.clear();
		for (int x = 1; x < allroutelist.size(); x++) {
			for (int y = 0; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
		allroute = Boolean.TRUE;
	}

	public void method24() {
		method = 24;
		System.out.println("enter to method24");
		templr = null;
		method10(PathRoutingManager.MY_LOCATION, SAElist.get(1), mSailsMapView
				.getRoutingManager().getPathPaint());

	}

	public void method25() {
		method = 25;
		System.out.println("enter to method25");
		checkcount = checkcount + 1;
		templr = null;
		method10(trainsitlist.get(trainsitlist.size() - 1), SAElist.get(1),
				mSailsMapView.getRoutingManager().getPathPaint());

		allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 0; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
	}

	public void method26() {
		method = 26;
		System.out.println("enter to method26");
		checkcount = checkcount + 1;
		templr = null;
		method10(trainsitlist.get(trainsitlist.size() - 1), SAElist.get(1),
				mSailsMapView.getRoutingManager().getPathPaint());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 0; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
	}

	public void method27(int i) {
		method = 27;
		System.out.println("enter to method27");
		checkcount = checkcount + 1;
		templr = trainsitlist.get(i + 1);
		method10(trainsitlist.get(i), trainsitlist.get(i + 1), mSailsMapView
				.getRoutingManager().getPathPaint());
		allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 0; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
	}

	public void method28(int i) {
		method = 28;
		System.out.println("enter to method28");
		checkcount = checkcount + 1;
		templr = trainsitlist.get(i + 1);
		method10(trainsitlist.get(i), trainsitlist.get(i + 1), mSailsMapView
				.getRoutingManager().getPathPaint());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 0; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();

	}

	public void method31() {
		System.out.println("enter to method31");
		SAElist.clear();
		SAElist.add(lrpointto);
		trainsitlist.clear();
		allroutelist.clear();
		trainsitImage.clear();
		mSailsMapView.getRoutingManager().setStartRegion(SAElist.get(0));
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				SAElist.get(0),
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.start_location)));
		checkcount = 0;
		banner_unopen.setVisibility(View.INVISIBLE);

	}

	public void method32() {
		System.out.println("enter to methd32");
		if (SAElist.size() == 0) {
			method31();
		}
		if (SAElist.size() > 1) {
			SAElist.set(1, lrpointto);
		} else {
			SAElist.add(lrpointto);
		}
		trainsitlist.clear();
		trainsitImage.clear();
		allroutelist.clear();
		mSailsMapView.getRoutingManager().setTargetRegion(lrpointto);
		mSailsMapView.getMarkerManager().setLocationRegionMarker(
				SAElist.get(1),
				Marker.boundCenter(getResources().getDrawable(
						R.drawable.stop_location)));
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF35b3e5);
		endRouteButton.setVisibility(View.VISIBLE);
		currentFloorDistanceView.setVisibility(View.VISIBLE);
		msgView.setVisibility(View.VISIBLE);
		if (mSailsMapView.getRoutingManager().enableHandler())
			distanceView.setVisibility(View.VISIBLE);
		banner_unopen.setVisibility(View.INVISIBLE);
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();

	}

	public void method33() {
		System.out.println("enter to method33");
		if (SAElist.size() == 0) {
			method31();
		} else if (SAElist.size() == 1) {
			method32();
		} else {
			if (SAElist.get(0) == lrpointto || SAElist.get(1) == lrpointto) {

			} else {
				trainsitlist.add(lrpointto);
				trainsitImage.add(1);
			}
		}
		banner_unopen.setVisibility(View.INVISIBLE);
		checkcount = 0;
		templist = new ArrayList<GeoPoint>();
	}

	public void method34() {
		banner_unopen.setVisibility(View.INVISIBLE);
		banner_open.setVisibility(View.INVISIBLE);
		banner_label.setVisibility(View.VISIBLE);
		banner_label.startAnimation(animation);
	}

	public void method35() {
		banner_unopen.setVisibility(View.INVISIBLE);
		banner_open.setVisibility(View.INVISIBLE);
		banner_label.setVisibility(View.VISIBLE);
		banner_label.startAnimation(animation);
	}

	public void method41() {
		System.out.println("enter to method41");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xffffffff);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(SAElist.get(0), SAElist.get(1), mSailsMapView
				.getRoutingManager().getPathPaint());
		showTemplist();
	}

	public void method42() {
		System.out.println("enter to method42");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xff993344);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(SAElist.get(0), trainsitlist.get(0), mSailsMapView
				.getRoutingManager().getPathPaint());
		templr = trainsitlist.get(0);
		checkcount++;
		showTemplist();
	}

	public void method43() {
		System.out.println("enter to method43");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xff993344);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(SAElist.get(0), trainsitlist.get(0), mSailsMapView
				.getRoutingManager().getPathPaint());
		templr = trainsitlist.get(0);
		checkcount++;
		if (allroute) {

		} else {
			allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		}
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 1; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
		allroute = Boolean.TRUE;
	}

	public void method44() {
		System.out.println("enter to method44");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xff85b038);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(trainsitlist.get(trainsitlist.size() - 1), SAElist.get(1),
				mSailsMapView.getRoutingManager().getPathPaint());
		templr = null;
		checkcount++;
		allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 1; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
	}

	public void method45() {
		System.out.println("enter to method45");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xff85b038);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(trainsitlist.get(trainsitlist.size() - 1), SAElist.get(1),
				mSailsMapView.getRoutingManager().getPathPaint());
		templr = null;
		checkcount++;
		showTemplist();
	}

	public void method46(int i) {
		System.out.println("enter to method46");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xffffff55);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(trainsitlist.get(i), trainsitlist.get(i + 1), mSailsMapView
				.getRoutingManager().getPathPaint());
		templr = trainsitlist.get(i + 1);
		checkcount++;
		allroutelist.add((ArrayList<GeoPoint>) saveTemplist());
		templist.clear();
		for (int x = 0; x < allroutelist.size(); x++) {
			for (int y = 1; y < allroutelist.get(x).size(); y++) {
				templist.add(allroutelist.get(x).get(y));
			}
		}
		showTemplist();
	}

	public void method47(int i) {
		System.out.println("enter to method47");
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xffffff55);
		System.out.println(mSailsMapView.getRoutingManager().getPathPaint()
				.getColor());
		method10(trainsitlist.get(i), trainsitlist.get(i + 1), mSailsMapView
				.getRoutingManager().getPathPaint());
		templr = trainsitlist.get(i + 1);
		checkcount++;
		showTemplist();

	}

	public void method51() {
		for (int i = 0; i < trainsitlist.size(); i++) {
			if (lrpointto == trainsitlist.get(i)) {
				trainsitImage.set(i, 2);
				break;
			}
		}
	}

	public void method52() {
		for (int i = 0; i < trainsitlist.size(); i++) {
			if (lrpointto == trainsitlist.get(i)) {
				trainsitImage.set(i, 3);
				break;
			}
		}
	}

	public void method53() {
		for (int i = 0; i < trainsitlist.size(); i++) {
			if (lrpointto == trainsitlist.get(i)) {
				trainsitImage.set(i, 4);
				break;
			}
		}
	}

	public void method54() {
		for (int i = 0; i < trainsitlist.size(); i++) {
			if (lrpointto == trainsitlist.get(i)) {
				trainsitImage.set(i, 5);
				break;
			}
		}
	}

	public void method55() {
		for (int i = 0; i < trainsitlist.size(); i++) {
			if (lrpointto == trainsitlist.get(i)) {
				trainsitImage.set(i, 6);
				break;
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private void initCamera() {
		if (!isPreview) {
			camera = Camera.open(0);
			camera.setDisplayOrientation(90);
		}
		if (camera != null && !isPreview) {
			try {
				Camera.Parameters parameters = camera.getParameters();
				parameters.setPictureSize(screenwidth, screenheight);
				parameters.setPreviewFpsRange(4, 10);
				parameters.setPictureFormat(ImageFormat.JPEG);
				parameters.set("jpeg_quality", 85);
				parameters.setPictureSize(screenwidth, screenheight);
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
			isPreview = true;
		}
	}

	private final class SensorListener implements SensorEventListener {

		private float predegree = 0;

		@Override
		public void onSensorChanged(SensorEvent event) {

			float[] values = event.values;
			float tempangle = 0;
			// int sensorType = event.sensor.getType();

			float yAngle = values[1];
			float zAngle = values[0];
			int x = screenwidth / 2;
			int y = show.back.getHeight() / 2;
			if (Math.abs(zAngle) <= Max_ANGLE) {

				int deltax = (int) ((screenwidth - show.bubble.getWidth()) / 2
						* zAngle / Max_ANGLE);
				x = x - deltax;
			} else if (zAngle > 360 - Max_ANGLE && zAngle < 360) {
				int deltax = (int) ((screenwidth - show.bubble.getWidth()) / 2
						* (360 - zAngle) / Max_ANGLE);
				x = x + deltax;
			} else if (Math.abs(zAngle) > Max_ANGLE && zAngle < 180) {
				x = 0;
			} else {
				x = screenwidth - show.bubble.getWidth();
			}
			double y0, x0, y1, x1;
			y0 = mSailsMapView.getRoutingManager().MY_LOCATION
					.getCenterLatitude();
			x0 = mSailsMapView.getRoutingManager().MY_LOCATION
					.getCenterLongitude();
			if (templr != null) {
				y1 = templr.getCenterLatitude();
				x1 = templr.getCenterLongitude();
			} else {
				if (SAElist != null && SAElist.size() > 1) {
					y1 = SAElist.get(1).getCenterLatitude();
					x1 = SAElist.get(1).getCenterLongitude();
				} else {
					y1 = mSailsMapView.getRoutingManager().MY_LOCATION
							.getCenterLatitude();
					x1 = mSailsMapView.getRoutingManager().MY_LOCATION
							.getCenterLongitude();
				}

			}
			float tempx = (float) ((float) x1 - x0);
			float tempy = (float) ((float) y1 - y0);
			if (x1 - x0 > 0 && y1 - y0 > 0) {
				tempangle = (float) Math.atan(tempx / tempy);
			} else if (x1 - x0 > 0 && y1 - y0 < 0) {
				tempangle = (float) Math.atan(tempx / tempy) + 180;
			} else if (x1 - x0 < 0 && y1 - y0 > 0) {
				tempangle = (float) Math.atan(tempx / tempy) + 360;
			} else if (x1 - x0 < 0 && y1 - y0 < 0) {
				tempangle = (float) Math.atan(tempx / tempy) + 180;
			}
			// System.out.println("tempangle=" + tempangle);
			show.bubblex = x;
			show.bubbley = y;
			show.degrees = event.values[0] - tempangle;
			if (templr == null) {
				show.bitmapcount = 7;
			} else {
				for (int i = 0; i < trainsitlist.size(); i++) {
					if (trainsitlist.get(i) == templr) {
						show.bitmapcount = trainsitImage.get(i);
					}
					break;
				}
			}
			show.postInvalidate();
			float degree = event.values[0];// 存放了方向值
			/** 动画效果 */
			RotateAnimation animation = new RotateAnimation(predegree, degree,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(200);
			// imageView.startAnimation(animation);
			// show.bubble.
			predegree = -degree;

		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

	}

}