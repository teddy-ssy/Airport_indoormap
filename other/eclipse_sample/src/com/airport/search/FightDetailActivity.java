package com.airport.search;

import com.airport.bean.FightInfo;
import com.airport.page.MainActivity;
import com.airport.R;
import com.airport.page.UserActivity;
import com.airport.page.WelcomeActivity;
import com.airport.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FightDetailActivity extends Activity {
	private TextView fightnumber;
	private int fightcode;
	private TextView startcity;
	private TextView arrivalcity;
	private TextView plantakeoff;
	private TextView realtakeoff;
	private TextView planarrival;
	private TextView realarrival;
	private TextView enter;
	private TextView baggage;
	private FightInfo fightinfo = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fightdetail);
		Intent intent = getIntent();
		fightinfo = new FightInfo(intent.getStringExtra("fightinfo"));
		fightnumber = (TextView) findViewById(R.id.textview_flight_fightnumber);
		startcity = (TextView) findViewById(R.id.textview_flight_takeoff);
		arrivalcity = (TextView) findViewById(R.id.textview_flight_arrival);
		plantakeoff = (TextView) findViewById(R.id.textview_flight_planstarttime);
		realtakeoff = (TextView) findViewById(R.id.textview_flight_realstarttime);
		planarrival = (TextView) findViewById(R.id.textview_flight_planarrivaltime);
		realarrival = (TextView) findViewById(R.id.textview_flight_realarrivaltime);
		enter = (TextView) findViewById(R.id.textview_flight_entrance);
		baggage = (TextView) findViewById(R.id.textview_flight_buggage);
		fightnumber.setText(fightinfo.getFightnumber());
		startcity.setText(fightinfo.getTakeoffcity());
		arrivalcity.setText(fightinfo.getArrivalcity());
		plantakeoff.setText(fightinfo.getPlanTakeoffTime());
		realtakeoff.setText(fightinfo.getRealTakeoffTime());
		planarrival.setText(fightinfo.getPlanArrivalTime());
		realarrival.setText(fightinfo.getRealArrivalTime());
		enter.setText(fightinfo.getEnter());
		baggage.setText(fightinfo.getBaggage());

	}

	public void button_fight_addfight(View v) {
		Intent intent = new Intent(FightDetailActivity.this,
				UserActivity.class);
		Constants.ENTERANCE = fightinfo.getEnter();
		Constants.TAKEOFF = fightinfo.getPlanTakeoffTime();
		Constants.ARRIVAL = fightinfo.getPlanArrivalTime();
		Constants.ENDAIRPORT = fightinfo.getArrivalcity();
		Constants.FIGHTNUMBER = fightinfo.getFightnumber();
		startActivity(intent);
	}

	public void onBackPressed() {
		finish();
		super.onBackPressed();

		Intent intent = new Intent(FightDetailActivity.this, WelcomeActivity.class);
		startActivity(intent);
	}
}
