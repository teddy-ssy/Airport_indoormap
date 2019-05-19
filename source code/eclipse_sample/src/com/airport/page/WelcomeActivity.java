package com.airport.page;

import java.util.ArrayList;
import java.util.List;

import com.airport.R;
import com.airport.bean.FightInfo;
import com.airport.bean.SearchEntity;
import com.airport.commons.GlobalMsgTypes;
import com.airport.commons.GlobalStrings;
import com.airport.help.chatService.ChatService;
import com.airport.help.message.MainBodyActivity;
import com.airport.help.message.MainBodyService;
import com.airport.help.userRequest.FriendRequestService;
import com.airport.network.NetConnect;
import com.airport.network.NetStateReceiver;
import com.airport.network.NetworkService;
import com.airport.search.SearchresultActivity;
import com.airport.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends Activity {
	boolean mMsg9Received;
	String mSearchedString;
	EditText editview_ip;
	Button button_ip;
	private NetStateReceiver netStateReceiver;
	private static WelcomeActivity instance;

	public static WelcomeActivity getInstance() {
		return instance;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		instance = this;
		editview_ip = (EditText) findViewById(R.id.editview_ip);
		button_ip = (Button) findViewById(R.id.button_ip);
		if (Constants.HAS_IP) {
			button_ip.setVisibility(View.INVISIBLE);
			editview_ip.setVisibility(View.INVISIBLE);
			init_Service();
			init_intent();
			init();
		}
	}

	private void init_intent() {
		// TODO Auto-generated method stub

		if (getIntent() != null) {
			Intent intent = getIntent();
			String str = intent.getStringExtra("out");

			if (str != null) {
				System.out.println(str);
				System.exit(1);
			}
		}
	}

	private void init_Service() {
		// TODO Auto-generated method stub
		NetworkService.getInstance().onInit(this);
		NetworkService.getInstance().setupConnection();
		// Intent intentTemp = new Intent(WelcomeActivity.this,
		// ChatService.class);
		// startService(intentTemp);
		// intentTemp = new Intent(WelcomeActivity.this,
		// FriendRequestService.class);
		// startService(intentTemp);
		// MainBodyService.getInstance().onInit(this);
		netStateReceiver = new NetStateReceiver();
		registerReceiver(netStateReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private void init() {
		// TODO Auto-generated method stub
		String airport = Constants.AIRPORT;
		SearchEntity SE = new SearchEntity(SearchEntity.SEARCH_FIGHT_BY_ARPORT,
				Constants.AIRPORT);
		startSearch(SE);
	}

	public void startSearch(SearchEntity sEnt0) {
		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgSearchPeople,
				sEnt0.toString());
		System.out.println("strsender=" + sEnt0.toString());
		mMsg9Received = false;
		while (!mMsg9Received) {
		}
		String[] arr0 = mSearchedString.split(GlobalStrings.searchListDivider);
		if (Integer.parseInt(arr0[0]) == SearchEntity.SEARCH_FIGHT_BY_ARPORT) {
			gotoFightSearchByAirportResult(mSearchedString);
		}

	}

	private void gotoFightSearchByAirportResult(String str) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		String[] arr0 = str.split(GlobalStrings.searchListDivider);
		int num1 = Integer.parseInt(arr0[1]);
		int num2 = Integer.parseInt(arr0[2]);
		ArrayList<String> listtakeoff = new ArrayList<String>();
		ArrayList<String> listarrival = new ArrayList<String>();
		for (int i = 0; i < num1; i++) {
			listtakeoff.add(arr0[i + 3]);
		}
		for (int i = 0; i < num2; i++) {
			listarrival.add(arr0[i + 3 + num1]);
		}
		intent.putStringArrayListExtra("takeoff", listtakeoff);
		intent.putStringArrayListExtra("arrival", listarrival);
		startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
		overridePendingTransition(R.anim.my_slide_right_in,
				R.anim.my_slide_left_out);
	}

	public void onReceiveSearchList(String msg0) {
		mSearchedString = msg0;
		mMsg9Received = true;
	}

	public void button_ip(View v) {
		NetConnect.mHostIp = editview_ip.getText().toString();
		init_Service();
		init_intent();
		init();
		Constants.HAS_IP = true;
	}
}
