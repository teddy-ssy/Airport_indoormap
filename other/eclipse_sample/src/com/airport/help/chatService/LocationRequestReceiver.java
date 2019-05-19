package com.airport.help.chatService;

import com.airport.commons.GlobalMsgTypes;
import com.airport.help.chatter.ChatActivity;
import com.airport.map.MapActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationRequestReceiver extends BroadcastReceiver {
	private ChatService chatservice;

	public LocationRequestReceiver(ChatService chatservice) {
		this.chatservice = chatservice;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int type = intent.getIntExtra("com.airport.locationRequest.type", 0);
		String str = intent.getStringExtra("com.airport.locationRequest.msg");
		switch(type){
		case GlobalMsgTypes.msgLocationRequest:
			System.out.println("LocationRequestReceiver");
			chatservice.LocationRequestMsgArrive(str, false);
			break;
		}
	}

}
