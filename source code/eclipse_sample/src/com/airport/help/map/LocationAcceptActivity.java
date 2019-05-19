package com.airport.help.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airport.bean.ChatEntity;
import com.airport.bean.LocationResponseEntity;
import com.airport.bean.UserInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.network.NetworkService;
import com.airport.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sails.engine.LocationRegion;
import com.sails.engine.SAILS;
import com.sails.engine.MarkerManager;
import com.sails.engine.PathRoutingManager;
import com.sails.engine.PinMarkerManager;
import com.sails.engine.SAILSMapView;
import com.sails.engine.core.model.GeoPoint;
import com.sails.engine.overlay.Marker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LocationAcceptActivity extends Activity {

	static SAILS mSails;
	static SAILSMapView mSailsMapView;
	ArrayAdapter<String> adapter;
	byte zoomSav = 0;
	private TextView mTvHeadName;
	private Button buton_Accept;
	private Button button_Decline;
	private UserInfo sender;
	// private Button map_location_accept_statement;
	double latitude, longitude;
	Boolean first = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_location_accept);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		LocationRegion.FONT_LANGUAGE = LocationRegion.NORMAL;
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
						latitude = PathRoutingManager.MY_LOCATION
								.getCenterLatitude();
						System.out.println(latitude);
						longitude = PathRoutingManager.MY_LOCATION
								.getCenterLongitude();
						System.out.println(longitude);
					}

					@Override
					public void onTimeOut() {
						if (!mSails.checkMode(SAILS.WIFI_GFP_IMU))
							mSails.stopLocatingEngine();
						new AlertDialog.Builder(LocationAcceptActivity.this)
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
		((FrameLayout) findViewById(R.id.location_map)).addView(mSailsMapView);
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
										mapViewInitial();
										routingInitial();
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

		Init();
	}

	private void Init() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		String in = intent.getStringExtra("sender");
		sender = new UserInfo(in);
		String receivername = intent.getStringExtra("receivername");
		final int receiverid = intent.getIntExtra("receiverid", 0);
		mTvHeadName = (TextView) findViewById(R.id.map_location_accept_header_name);
		mTvHeadName.setText(receivername);
		buton_Accept = (Button) findViewById(R.id.map_location_accept_btn_accept);
		buton_Accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LocationAcceptActivity.this);
				alertDialogBuilder.setTitle(null);
				alertDialogBuilder
						.setMessage("are you sure to accept the request?")
						.setCancelable(true)
						.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// TODO Auto-generated method stub
										if (!mSails.isLocationEngineStarted()) {
											mSails.startLocatingEngine();
										}
										createway(
												PathRoutingManager.MY_LOCATION,
												PathRoutingManager.MY_LOCATION);

										int floorname = mSails.getFloorNumber();
										System.out.println(mSailsMapView
												.getMapViewPosition()
												.getCenter());
										latitude = mSailsMapView
												.getMapViewPosition()
												.getCenter().latitude;
										longitude = mSailsMapView
												.getMapViewPosition()
												.getCenter().longitude;
										System.out.println(mSails
												.getFloorNumber());
										LocationResponseEntity locationresponseEntity = new LocationResponseEntity(
												floorname, latitude, longitude);
										String str = locationresponseEntity
												.toSendString();

										ChatEntity chatentity = new ChatEntity(
												GlobalMsgTypes.msgFromFriend,
												sender, receiverid, str);
										NetworkService
												.getInstance()
												.sendUpload(
														GlobalMsgTypes.msgFromFriend,
														chatentity.toString());

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
				AlertDialog alterDialog = alertDialogBuilder.create();
				alterDialog.show();
			}
		});
		button_Decline = (Button) findViewById(R.id.map_location_accept_btn_decline);
		button_Decline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LocationAcceptActivity.this);
				alertDialogBuilder.setTitle(null);
				alertDialogBuilder
						.setMessage("are you sure to accept the request?")
						.setCancelable(true)
						.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// TODO Auto-generated method stu
										String str = "请求失败";
										ChatEntity chatentity = new ChatEntity(
												GlobalMsgTypes.msgFromFriend,
												sender, receiverid, str);
										NetworkService
												.getInstance()
												.sendUpload(
														GlobalMsgTypes.msgFromFriend,
														chatentity.toString());
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.cancel();
									}
								});
				AlertDialog alterDialog = alertDialogBuilder.create();
				alterDialog.show();
			}
		});
		// map_location_accept_statement = (Button)
		// findViewById(R.id.map_location_accept_statement);
		/*
		 * map_location_accept_statement .setOnClickListener(new
		 * View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (!mSails.isLocationEngineStarted()) {
		 * mSails.startLocatingEngine();
		 * mSailsMapView.setLocatorMarkerVisible(true); mSailsMapView
		 * .setMode(SAILSMapView.LOCATION_CENTER_LOCK |
		 * SAILSMapView.FOLLOW_PHONE_HEADING);
		 * 
		 * } System.out.println(mSailsMapView.getMapViewPosition().getCenter());
		 * latitude = mSailsMapView.getMapViewPosition().getCenter().latitude;
		 * longitude = mSailsMapView.getMapViewPosition().getCenter().longitude;
		 * System.out.println(mSails.getFloorNumber());
		 * createway(PathRoutingManager.MY_LOCATION,
		 * PathRoutingManager.MY_LOCATION); } });
		 */
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

		// Auto Adjust suitable map zoom level and position to best view
		// position.
		mSailsMapView.autoSetMapZoomAndView();

		// set location region click call back.
		mSailsMapView
				.setOnRegionClickListener(new SAILSMapView.OnRegionClickListener() {
					@Override
					public void onClick(List<LocationRegion> locationRegions) {
						LocationRegion lr = locationRegions.get(0);
						// begin to routing
						if (mSails.isLocationEngineStarted()) {
							// set routing start point to current user location.
							mSailsMapView.getRoutingManager().setStartRegion(
									PathRoutingManager.MY_LOCATION);

							// set routing end point marker icon.
							mSailsMapView
									.getRoutingManager()
									.setTargetMakerDrawable(
											Marker.boundCenterBottom(getResources()
													.getDrawable(
															R.drawable.destination)));

							// set routing path's color.
							mSailsMapView.getRoutingManager().getPathPaint()
									.setColor(0xFF35b3e5);

						} else {
							mSailsMapView
									.getRoutingManager()
									.setTargetMakerDrawable(
											Marker.boundCenterBottom(getResources()
													.getDrawable(
															R.drawable.map_destination)));
							mSailsMapView.getRoutingManager().getPathPaint()
									.setColor(0xFF85b038);
							if (mSailsMapView.getRoutingManager()
									.getStartRegion() != null)
								;

						}

						// set routing end point location.
						mSailsMapView.getRoutingManager().setTargetRegion(lr);

						// begin to route.
						mSailsMapView.getRoutingManager().enableHandler();
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
					public void onLongClick(List<LocationRegion> locationRegions) {
						if (mSails.isLocationEngineStarted())
							return;
						mSailsMapView.getMarkerManager().clear();
						mSailsMapView.getRoutingManager().setStartRegion(
								locationRegions.get(0));
						mSailsMapView
								.getMarkerManager()
								.setLocationRegionMarker(
										locationRegions.get(0),
										Marker.boundCenter(getResources()
												.getDrawable(
														R.drawable.start_point)));
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
						System.out.println("arrival");
						System.out.println(mSailsMapView.getRoutingManager()
								.getCurrentFloorRoutingPathNodes());
					}

					@Override
					public void onRouteSuccess() {
						System.out.println("RouteSuccess");
						System.out.println(mSailsMapView.getRoutingManager()
								.getCurrentFloorRoutingPathNodes());
					}

					@Override
					public void onRouteFail() {
						System.out.println("RouteFail");
						System.out.println(mSailsMapView.getRoutingManager()
								.getCurrentFloorRoutingPathNodes());

					}

					@Override
					public void onPathDrawFinish() {
					}

					@Override
					public void onTotalDistanceRefresh(int distance) {

					}

					@Override
					public void onReachNearestTransferDistanceRefresh(
							int distance, int nodeType) {

					}

					@Override
					public void onSwitchFloorInfoRefresh(
							List<PathRoutingManager.SwitchFloorInfo> infoList,
							int nearestIndex) {

					}

				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSailsMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSailsMapView.onPause();
	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
		((FrameLayout) findViewById(R.id.location_map)).removeAllViews();
	}

	public void createway(LocationRegion start, LocationRegion end) {
		System.out.println("enter to createway");
		mSailsMapView.getRoutingManager().setStartRegion(start);
		mSailsMapView.getRoutingManager().setTargetMakerDrawable(
				Marker.boundCenterBottom(getResources().getDrawable(
						R.drawable.map_destination)));
		mSailsMapView.getRoutingManager().getPathPaint()
				.set(mSailsMapView.getRoutingManager().getPathPaint());
		mSailsMapView.getRoutingManager().setTargetRegion(end);

		mSailsMapView.getRoutingManager().getPathPaint().setColor(0xFF85b038);
		if (mSailsMapView.getRoutingManager().enableHandler()) {
		}
		if (mSailsMapView.getRoutingManager().enableHandler()) {
			List<GeoPoint> gplist = mSailsMapView.getRoutingManager()
					.getCurrentFloorRoutingPathNodes();
			mSailsMapView.autoSetMapZoomAndView(gplist);
			System.out.println(gplist);
		}
	}

}