package com.airport.help;

import com.airport.help.chatService.ChatServiceData;
import com.airport.help.chatService.FriendListInfo;
import com.airport.help.chatService.InitData;
import com.airport.network.NetworkService;



public class CloseAll {
	
	public static void closeAll() {
		ConnectedApp.getInstance().clearAll();
		
		try {
			NetworkService.getInstance().closeConnection();
		} catch (Exception e) {}
		try {
			InitData.closeInitData();
			FriendListInfo.closeFriendListInfo();
			ChatServiceData.closeChatServiceData();
		} catch (Exception e) {}
	}
	
}
