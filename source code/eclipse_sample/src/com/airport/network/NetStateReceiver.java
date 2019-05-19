package com.airport.network;



import com.airport.bean.UserInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.help.ConnectedApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetStateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {        	
            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(currentNetworkInfo.isConnected()) {
            	System.out.println("connected again");
            	
            	if(!NetworkService.getInstance().getIsConnected()) {
            		NetworkService.getInstance().setupConnection();
            		
            		UserInfo uu0 = ConnectedApp.getInstance().getUserInfo();
            		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgBackOnline, uu0.toString());
            	}
            } else {
            	NetworkService.getInstance().closeConnection();
            }
        }
	
}