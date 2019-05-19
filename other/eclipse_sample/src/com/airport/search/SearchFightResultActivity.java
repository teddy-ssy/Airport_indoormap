package com.airport.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.airport.bean.FightInfo;
import com.airport.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchFightResultActivity extends Activity {
	private ListView listview_fightresult;
	private List<String> listfight;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchfightresult);
		Intent intent = getIntent();
		listfight = (ArrayList<String>) intent
				.getSerializableExtra("listfight");
		String[] fightname = new String[listfight.size()];
		for (int i = 0; i < listfight.size(); i++) {
			FightInfo fightinfo = new FightInfo(listfight.get(i));
			String number = fightinfo.getFightnumber();
			fightname[i] = String.valueOf(number);
		}
		listview_fightresult = (ListView) findViewById(R.id.listview_fightresult);
		ArrayAdapter<String> adapter1 = new ArrayAdapter(this,
				R.layout.searchfightresult_listdetail, fightname);
		listview_fightresult.setAdapter(adapter1);
		listview_fightresult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FightInfo fightinfo = new FightInfo(listfight.get(position));
				Intent intent = new Intent(SearchFightResultActivity.this,
						FightDetailActivity.class);
				intent.putExtra("fightinfo", fightinfo.toString());
				startActivity(intent);
			}
		});
	}
}
