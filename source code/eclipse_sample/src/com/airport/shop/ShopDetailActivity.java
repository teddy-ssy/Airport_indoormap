package com.airport.shop;

import java.util.ArrayList;

import com.airport.bean.ShopInfo;
import com.airport.map.MapActivity;
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

public class ShopDetailActivity extends Activity {
	private TextView textview_shopdetail_type;
	private TextView textview_shopdetail_name;
	private TextView textview_shopdetail_call;
	private TextView textview_shopdetail_introduction;
	private ShopInfo si;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopdetail);
		init_view();
		init_Intent();

	}

	private void init_view() {
		// TODO Auto-generated method stub
		textview_shopdetail_type = (TextView) findViewById(R.id.textview_shopdetail_type);
		textview_shopdetail_name = (TextView) findViewById(R.id.textview_shopdetail_name);
		textview_shopdetail_call = (TextView) findViewById(R.id.textview_shopdetail_call);
		textview_shopdetail_introduction = (TextView) findViewById(R.id.textview_shopdetail_introduction);

	}

	public void onBackPressed() {
		finish();
		super.onBackPressed();

		
	}

	private void init_Intent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		if (intent.getStringArrayListExtra("shopsearchresult") != null) {
			ArrayList<String> shopsearchresult = intent
					.getStringArrayListExtra("shopsearchresult");
			si = new ShopInfo(shopsearchresult.get(0));
			System.out.println(shopsearchresult.get(0));
			System.out.println(si.getShoptype());
			System.out.println(si.getChinese_s());
			System.out.println(si.getPhone());
			System.out.println(si.getIntroduction());
		} else if (intent.getStringExtra("shopinfo") != null) {
			si = new ShopInfo(intent.getStringExtra("shopinfo"));
			System.out.println(si.getShoptype());
			System.out.println(si.getChinese_s());
			System.out.println(si.getPhone());
			System.out.println(si.getIntroduction());
		}

		textview_shopdetail_type.setText(String.valueOf(si.getLabel()));
		textview_shopdetail_name.setText(si.getChinese_s());
		textview_shopdetail_call.setText(String.valueOf(si.getPhone()));
		textview_shopdetail_introduction.setText(si.getIntroduction());
	}

	public void button_shop_addtoroute(View v) {
		Intent intent = new Intent(ShopDetailActivity.this, MapActivity.class);
		intent.putExtra("Extlr", si.getLabel());
		startActivity(intent);
	}
}
