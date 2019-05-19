package com.airport.help.message;

import java.util.ArrayList;

import com.airport.bean.SearchEntity;
import com.airport.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class MainTabFriendsPage {

	private static MainTabFriendsPage mInstance;

	private View mViewOfPage;
	private Context mContext0;

	private RelativeLayout mLayoutByName;
	private RelativeLayout mLayoutByOnline;

	public static MainTabFriendsPage getInstance() {
		if (mInstance == null) {
			mInstance = new MainTabFriendsPage();
		}
		return mInstance;
	}

	private MainTabFriendsPage() {
	}

	public void onInit(View view, Context cont) {
		mViewOfPage = view;
		mContext0 = cont;
	}

	public void onCreate() {

		mInstance = this;

		mLayoutByName = (RelativeLayout) mViewOfPage
				.findViewById(R.id.main_tab_friends_find_by_name);
		mLayoutByOnline = (RelativeLayout) mViewOfPage
				.findViewById(R.id.main_tab_friends_find_by_online);

		mLayoutByName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainBodyActivity.getInstance().gotoSearchBynameActivity();
			}
		});

		mLayoutByOnline.setOnClickListener(new View.OnClickListener() {
			@Override
			/*
			 * public void onClick(View v) {
			 * MainBodyActivity.getInstance().gotoSearchByonlineActivity(); }
			 */
			public void onClick(View v) {

				Log.d("start search by name",
						"+++++++++++++++++++++++++++++++++++++++++++"
								+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				String searchName = "";
				SearchEntity s_ent0 = new SearchEntity(
						SearchEntity.SEARCH_BY_ONLINE, -1, -1, -1, searchName);
				MainBodyActivity.getInstance().startSearch(s_ent0);
			}
		});

	}
}
