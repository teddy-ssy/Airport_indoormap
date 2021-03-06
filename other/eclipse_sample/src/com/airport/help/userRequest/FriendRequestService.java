package com.airport.help.userRequest;


import com.airport.bean.ChatEntity;
import com.airport.bean.FrdReqNotifItemEntity;
import com.airport.bean.FrdRequestEntity;
import com.airport.bean.TabMsgItemEntity;
import com.airport.bean.UserInfo;
import com.airport.help.chatService.FriendListInfo;
import com.airport.help.message.FrdRequestNotifActivity;
import com.airport.help.message.MainBodyActivity;
import com.airport.help.message.MainTabMsgPage;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class FriendRequestService extends Service{

	private static FriendRequestService mInstance;
	
	private UserInfo mRequester;
	private UserInfo mRequestee;
	private FriendRequestMsgReceiver mFriendRequestReceiver;
	
	public static FriendRequestService getInstance() {
		return mInstance;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		mInstance = this;
		
		IntentFilter ifter=new IntentFilter("com.airport.hello.FRIEND_REQUEST_MSGS");
		mFriendRequestReceiver =new FriendRequestMsgReceiver();
		registerReceiver(mFriendRequestReceiver,ifter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mFriendRequestReceiver);
	}
	
	public void processFriendRequest(String msg0) {
		FrdRequestEntity reqEnt0 = new FrdRequestEntity(msg0);
		mRequester = reqEnt0.getRequester();
		mRequestee = reqEnt0.getRequestee();
		
		MainTabMsgPage.getInstance().onUpdateById(TabMsgItemEntity.FrdReqNotifId, mRequester.getAvatarId(),
				mRequester.getName(), mRequester.getName() + " wants to be your friend",
				ChatEntity.genDate(), true);
		if(MainBodyActivity.getCurPage() == MainBodyActivity.mPageMsg) {
			MainTabMsgPage.getInstance().onUpdateView();
		}
		
		FrdRequestNotifActivity.newNotification(FrdReqNotifItemEntity.mTypeFrdReq, mRequester.getId(), 
				mRequester.getAvatarId(), mRequester.getName(), 
				mRequester.getName() + " wants to be your friend", ChatEntity.genDate(), mRequester.toString());
		if(FrdRequestNotifActivity.getInstance() != null && FrdRequestNotifActivity.getInstance().getIsCurPage() == true) {
			FrdRequestNotifActivity.getInstance().onUpdateView();
		}
	}
	
	public void processFriendRequestResponse(String msg0) {
		FrdRequestEntity reqEnt0 = new FrdRequestEntity(msg0);
		mRequestee = reqEnt0.getRequestee();
		
		if(reqEnt0.isAceepted()) {
			MainTabMsgPage.getInstance().onUpdateById(TabMsgItemEntity.FrdReqNotifId, mRequestee.getAvatarId(),
				mRequestee.getName(), mRequestee.getName() + " accepted your friend request",
				ChatEntity.genDate(), true);
		}
		else {
			MainTabMsgPage.getInstance().onUpdateById(TabMsgItemEntity.FrdReqNotifId, mRequestee.getAvatarId(),
					mRequestee.getName(), mRequestee.getName() + " declined your friend request",
					ChatEntity.genDate(), true);
		}
		
		if(MainBodyActivity.getCurPage() == MainBodyActivity.mPageMsg) {
			MainTabMsgPage.getInstance().onUpdateView();
		}
		
		String msg11;
		if(!reqEnt0.isAceepted()) {
			msg11 = mRequestee.getName() + " has declined your request to be friends";
		} else {
			msg11 = mRequestee.getName() + " has accepted your request to be friends";
			FriendListInfo.getFriendListInfo().uponMakeNewFriend(mRequestee);
		}
		FrdRequestNotifActivity.newNotification(FrdReqNotifItemEntity.mTypeFrdReqResult, mRequestee.getId(), 
				mRequestee.getAvatarId(), mRequestee.getName(), 
				msg11, ChatEntity.genDate(), mRequestee.toString());
		if(FrdRequestNotifActivity.getInstance() != null && FrdRequestNotifActivity.getInstance().getIsCurPage() == true) {
			FrdRequestNotifActivity.getInstance().onUpdateView();
		}		
	}
	
}
