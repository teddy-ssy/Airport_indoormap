/**
 * the background Service that process the message sending by sendMyMessage()
 * and coming message by newMsgArrive()
 * 
 * it also manage the message queue for ChatActivity
 */

package com.airport.help.chatService;

import java.util.List;

import com.airport.bean.ChatEntity;
import com.airport.bean.LocationRequestEntity;
import com.airport.bean.UserInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.help.ConnectedApp;
import com.airport.help.chatter.ChatActivity;
import com.airport.help.map.LocationAcceptActivity;
import com.airport.help.message.MainBodyActivity;
import com.airport.help.message.MainTabMsgPage;
import com.airport.help.util.UnsavedChatMsg;
import com.airport.map.MapActivity;
import com.airport.network.NetConnect;
import com.airport.network.NetworkService;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class ChatService extends Service {

	private static ChatService mInstance;

	private ChatActivity mChatActv = null;
	private ChatMsgReceiver mMsgReceiver = null;
	private ChatBinder mBinder = new ChatBinder();

	private int mCurType = 0; // 0 for public room (default), 1 for group room,
								// 2 for friend chatting
	private UserInfo mMyUserInfo;

	private int mFriendId;

	private NetConnect mNetCon;

	private boolean mEnterFromNotification;
	private int mEnterFromNotificationId;

	public static ChatService getInstance() {
		return mInstance;
	}

	public class ChatBinder extends Binder {
		public ChatService getService() {
			return ChatService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mFriendId = -1;

		mInstance = this;

		IntentFilter ifter = new IntentFilter(
				"com.airport.hello.MESSAGE_RECEIVED");
		mMsgReceiver = new ChatMsgReceiver(this);
		ChatService.this.registerReceiver(mMsgReceiver, ifter);

		mMyUserInfo = ConnectedApp.getInstance().getUserInfo();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ChatService.this.unregisterReceiver(mMsgReceiver);
		NetworkService.getInstance().closeConnection();
		mInstance = null;
	}

	public boolean getEnterFromNotification() {
		return mEnterFromNotification;
	}

	public void setEnterFromNotification(boolean b) {
		mEnterFromNotification = b;
	}

	public int getEnterFromNotificationId() {
		return mEnterFromNotificationId;
	}

	public void setEnterFromNotificationId(int id) {
		mEnterFromNotificationId = id;
	}

	public void setBoundChatActivity(ChatActivity act0) {
		mChatActv = act0;
	}

	public void setType(int type) {
		mCurType = type;
	}

	public void setId(int id) {
		mFriendId = id;
	}

	public int getId() {
		return mFriendId;
	}

	public void sendMyMessage(String st0) {
		if (mCurType == GlobalMsgTypes.msgFromFriend) {
			System.out.println(mCurType);
			System.out.println(mMyUserInfo);
			System.out.println(mFriendId);
			System.out.println(st0);
			
			ChatEntity ent0 = new ChatEntity(mCurType, mMyUserInfo, mFriendId,
					st0);
			NetworkService.getInstance().sendUpload(mCurType, ent0.toString());
			newMsgArrive(ent0.toString(), true);
		}
	}

	public void newMsgArrive(String str0, boolean isSelf) {
		ChatEntity msgEntity = new ChatEntity(str0);

		int type = msgEntity.getType();
		// id is the id of the one client is talking to, regardless of him/her
		// being sender or receiver
		int id = msgEntity.getSenderId();
		if (isSelf) {
			id = mFriendId;

			ChatServiceData.getInstance().getCurMsg(type, id).add(msgEntity);
			ChatServiceData.getInstance().getCurIsSelf(type, id).add(isSelf);
			chatActivityDisplayHistory();

			UnsavedChatMsg.getInstance().newMsg(type, id, msgEntity, isSelf);

			if (!ChatActivity.getIsActive() || mFriendId != id) {
				ChatServiceData.getInstance().increUnreadMsgs(id);
			}

			if (!ChatActivity.getIsActive() || mFriendId != id) {
				MainTabMsgPage.getInstance().onUpdateByUserinfo(
						FriendListInfo.getFriendListInfo().getUserFromId(id),
						msgEntity.getContent(), msgEntity.getTime(), true);
			} else {
				MainTabMsgPage.getInstance().onUpdateByUserinfo(
						FriendListInfo.getFriendListInfo().getUserFromId(id),
						msgEntity.getContent(), msgEntity.getTime(), false);
			}

			if (MainBodyActivity.getCurPage() == MainBodyActivity.mPageMsg) {

				MainTabMsgPage.getInstance().onUpdateView();
			}
		} else {

			if (msgEntity.getContent().equals("已发送请求，等待对方接受")) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(ChatService.this, LocationAcceptActivity.class);
				System.out.println(mMyUserInfo.toString());
				intent.putExtra("sender", mMyUserInfo.toString());
				intent.putExtra("receivername", msgEntity.getName());
				intent.putExtra("receiverid", msgEntity.getSenderId());
				startActivity(intent);
			} else {
				ChatServiceData.getInstance().getCurMsg(type, id)
						.add(msgEntity);
				ChatServiceData.getInstance().getCurIsSelf(type, id)
						.add(isSelf);
				chatActivityDisplayHistory();

				UnsavedChatMsg.getInstance()
						.newMsg(type, id, msgEntity, isSelf);

				if (!ChatActivity.getIsActive() || mFriendId != id) {
					ChatServiceData.getInstance().increUnreadMsgs(id);
				}

				if (!ChatActivity.getIsActive() || mFriendId != id) {
					MainTabMsgPage.getInstance().onUpdateByUserinfo(
							FriendListInfo.getFriendListInfo()
									.getUserFromId(id), msgEntity.getContent(),
							msgEntity.getTime(), true);
				} else {
					MainTabMsgPage.getInstance().onUpdateByUserinfo(
							FriendListInfo.getFriendListInfo()
									.getUserFromId(id), msgEntity.getContent(),
							msgEntity.getTime(), false);
				}

				if (MainBodyActivity.getCurPage() == MainBodyActivity.mPageMsg) {
					MainTabMsgPage.getInstance().onUpdateView();
				}
			}

		}

	}

	public void LocationRequestMsgArrive(String str0, boolean isSelf) {

		LocationRequestEntity msg = new LocationRequestEntity(str0);
		if (LocationRequestEntity.send == msg.getType()) {
			ChatEntity msgEntity = new ChatEntity(2, msg.getRequester(),
					msg.getResponsterId(), "已发送请求，等待对方接收", msg.getTime());

			int type = msgEntity.getType();
			// id is the id of the one client is talking to, regardless of
			// him/her
			// being sender or receiver
			int id = msg.getRequester().getId();
			if (isSelf) {
				id = mFriendId;

			} else {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(ChatService.this, MapActivity.class);
				startActivity(intent);
			}
			ChatServiceData.getInstance().getCurMsg(type, id).add(msgEntity);
			ChatServiceData.getInstance().getCurIsSelf(type, id).add(isSelf);
			chatActivityDisplayHistory();

			UnsavedChatMsg.getInstance().newMsg(type, id, msgEntity, isSelf);

			if (!ChatActivity.getIsActive() || mFriendId != id) {
				ChatServiceData.getInstance().increUnreadMsgs(id);
			}

			if (!ChatActivity.getIsActive() || mFriendId != id) {
				MainTabMsgPage.getInstance().onUpdateByUserinfo(
						FriendListInfo.getFriendListInfo().getUserFromId(id),
						msgEntity.getContent(), msgEntity.getTime(), true);
			} else {
				MainTabMsgPage.getInstance().onUpdateByUserinfo(
						FriendListInfo.getFriendListInfo().getUserFromId(id),
						msgEntity.getContent(), msgEntity.getTime(), false);
			}

			if (MainBodyActivity.getCurPage() == MainBodyActivity.mPageMsg) {

				MainTabMsgPage.getInstance().onUpdateView();
			}
		}

	}

	public void chatActivityDisplayHistory() {
		if (mChatActv == null) {
			return;
		}
		if (ChatActivity.getIsActive()) {
			List<ChatEntity> msgs = ChatServiceData.getInstance().getCurMsg(
					mCurType, mFriendId);
			List<Boolean> isSelf = ChatServiceData.getInstance().getCurIsSelf(
					mCurType, mFriendId);

			if (mCurType == GlobalMsgTypes.msgFromFriend) {
				mChatActv.updateListviewHistory(
						msgs,
						isSelf,
						mCurType,
						FriendListInfo.getFriendListInfo().getNameFromId(
								mFriendId));
			}
		}
	}

	public void sendLocationRequest() {
		mCurType = GlobalMsgTypes.msgLocationRequest;
		LocationRequestEntity entity = new LocationRequestEntity(mMyUserInfo,
				mFriendId);
		NetworkService.getInstance().sendUpload(mCurType, entity.toString());
		LocationRequestMsgArrive(entity.toString(), true);
	}
}
