/**
 * the registered BroadcastReceiver to which the ClientListener send arrived message
 * the only thing it did here is to unwrap the String contained in Intent and send it to
 * Chatting Service by calling its newMsgArrive function
 */

package com.airport.help.chatService;

import com.airport.commons.GlobalMsgTypes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChatMsgReceiver extends BroadcastReceiver {

	private ChatService mParService;

	public ChatMsgReceiver(ChatService mParService0) {
		mParService = mParService0;

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		int msgType = intent.getIntExtra("com.airport.hello.msg_type", 0);
		String msgStr = intent
				.getStringExtra("com.airport.hello.msg_received");

		int type = intent.getIntExtra("com.airport.locationRequest.type", 0);
		String str = intent.getStringExtra("com.airport.locationRequest.msg");
		switch (type) {
		case GlobalMsgTypes.msgLocationRequest:
			System.out.println("ChatMsgReceiver");
			mParService.LocationRequestMsgArrive(str, false);
			break;
		default:
			break;
		}

		switch (msgType) {
		case GlobalMsgTypes.msgPublicRoom:
		case GlobalMsgTypes.msgChattingRoom:
		case GlobalMsgTypes.msgFromFriend:
			mParService.newMsgArrive(msgStr, false);
			break;
		case GlobalMsgTypes.msgUpdateFriendList:
			// FriendListInfo.getFriendListInfo().updateFriendList(msgStr);
			break;
		case GlobalMsgTypes.msgFriendGoOnline:
			FriendListInfo.getFriendListInfo().friendGoOnAndOffline(msgStr, 1);
			break;
		case GlobalMsgTypes.msgFriendGoOffline:
			FriendListInfo.getFriendListInfo().friendGoOnAndOffline(msgStr, 0);
			break;
		default:
			break;
		}
	}

	/*
	 * PublicActivity par;
	 * 
	 * public PublicMsgReceiver(PublicActivity par0) { par=par0; }
	 * 
	 * @Override public void onReceive(Context context, Intent intent) { String
	 * msgStr=intent.getStringExtra ("yuner.example.hello.msg_received");
	 * 
	 * ChatEntity msgEntity=ChatEntity.Str2Ent(msgStr); par.msgs.add(msgEntity);
	 * par.Content.setAdapter(new PublicAdapter(par,par,par.msgs));
	 * par.Content.setSelection(par.msgs.size()-1); }
	 */

	/*
	 * public void sendMyMessage(String st0) { String
	 * div=ChatEntity.strSplitter; ChatEntity
	 * ent0=ChatEntity.Str2Ent(par.type+div+0+div+ 0+div+par.Name+div+
	 * par.Sex+div+ChatEntity.genDate()+div+ st0+div+0);
	 * par.netCon.sendUpload(par.type, ent0);
	 * 
	 * st0=ent0.toString(); Intent intent=new
	 * Intent("yuner.example.hello.MESSAGE_RECEIVED"); intent.putExtra
	 * ("yuner.example.hello.msg_received", st0); par.sendBroadcast(intent); }
	 */
}