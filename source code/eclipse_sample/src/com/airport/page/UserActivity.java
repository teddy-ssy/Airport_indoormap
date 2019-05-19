package com.airport.page;

import com.airport.R;
import com.airport.map.MapActivity;
import com.airport.twodimensioncode.CaptureActivity;
import com.airport.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class UserActivity extends Activity {
	private TextView textview_prak_detail;
	private TextView textview_entrance;
	private TextView textview_timetakeoff;
	private TextView textview_timelanding;
	private TextView textview_tragecity;
	private TextView textview_fightnumber;
	
	private String CAR = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userpage);
		init_View();
	}

	private void init_View() {
		// TODO Auto-generated method stub
		textview_prak_detail = (TextView) findViewById(R.id.textview_prak_detail);
		if (Constants.CAR != null) {
			textview_prak_detail.setText(Constants.CAR);
		} else {
			textview_prak_detail.setText("无");
		}
		textview_entrance = (TextView) findViewById(R.id.textview_entrance);

		if (Constants.ENTERANCE != null) {
			textview_entrance.setText(Constants.ENTERANCE);
		} else {
			textview_entrance.setText("无");
		}
		textview_timetakeoff = (TextView) findViewById(R.id.textview_timetakeoff);

		if (Constants.TAKEOFF != null) {
			textview_timetakeoff.setText(Constants.TAKEOFF);
		} else {
			textview_timetakeoff.setText("无");
		}
		textview_timelanding = (TextView) findViewById(R.id.textview_timelanding);

		if (Constants.ARRIVAL != null) {
			textview_timelanding.setText(Constants.ARRIVAL);
		} else {
			textview_timelanding.setText("无");
		}
		textview_tragecity = (TextView) findViewById(R.id.textview_tragecity);

		if (Constants.ENDAIRPORT != null) {
			textview_tragecity.setText(Constants.ENDAIRPORT);
		} else {
			textview_tragecity.setText("无");
		}
		textview_fightnumber = (TextView) findViewById(R.id.textview_aircompany);
		if (Constants.FIGHTNUMBER != null) {
			textview_fightnumber.setText(Constants.FIGHTNUMBER);
		} else {
			textview_fightnumber.setText("无");
		}
	}

	public void button_updatecar(View v) {
		Intent intent = new Intent(UserActivity.this, CaptureActivity.class);

		startActivity(intent);
	}

	public void button_createwaytoentrance(View v) {
		Intent intent = new Intent(UserActivity.this, MapActivity.class);
		intent.putExtra("Extlr", Constants.ENTERANCE);
		startActivity(intent);
	}

	public void button_scancard(View v) {
		Intent intent = new Intent(UserActivity.this, CaptureActivity.class);

		startActivity(intent);
	}

	public void onBackPressed() {
		finish();
		super.onBackPressed();

		Intent intent = new Intent(UserActivity.this, WelcomeActivity.class);
		startActivity(intent);
	}

}
