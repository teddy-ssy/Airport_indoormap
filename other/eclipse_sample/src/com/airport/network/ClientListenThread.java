package com.airport.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.airport.bean.ChatEntity;
import com.airport.bean.UserInfo;
import com.airport.commons.GlobalMsgTypes;
import com.airport.commons.GlobalStrings;
import com.airport.help.RegisterActivity;
import com.airport.help.chatService.InitData;
import com.airport.help.message.MainBodyActivity;
import com.airport.map.MapActivity;
import com.airport.page.MainActivity;
import com.airport.page.WelcomeActivity;
import com.airport.twodimensioncode.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ClientListenThread extends Thread {
	private Context mContext0;
	private Socket mSocket0;

	private InputStreamReader mInStrRder0;
	private BufferedReader mBuffRder0;

	public ClientListenThread(Context par, Socket s) {
		this.mContext0 = par;
		this.mSocket0 = s;
	}

	public void run() {
		super.run();
		try {
			mInStrRder0 = new InputStreamReader(mSocket0.getInputStream(),
					"gb2312");
			mBuffRder0 = new BufferedReader(mInStrRder0);
			String resp = null;

			while (true) {
				if ((resp = mBuffRder0.readLine()) != null) {
					int msgType = Integer.parseInt(resp); // type of message
															// received

					String actualMsg = mBuffRder0.readLine();
					actualMsg = actualMsg.replace(
							GlobalStrings.replaceOfReturn, "\n");
					System.out.println("从服务器接收到的信息>>>>>>>>>>>>>>>>>>>>>>>>>>>"
							+ actualMsg);
					switch (msgType) {
					case GlobalMsgTypes.msgPublicRoom:
						/* falls through */
					case GlobalMsgTypes.msgChattingRoom:
						/* falls through */
					case GlobalMsgTypes.msgFromFriend:
						/*
						 * try here to secure for the possible message with
						 * first input character as "\n"
						 */
						uponReceivedMsg();
						Log.d("message received" + actualMsg,
								"++++++++++++++++++++"
										+ "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

						ChatEntity entTemp = new ChatEntity(actualMsg);
						Intent intent = new Intent(
								"com.airport.hello.MESSAGE_RECEIVED");
						intent.putExtra("com.airport.hello.msg_received",
								entTemp.toString());
						intent.putExtra("com.airport.hello.msg_type", msgType);
						mContext0.sendBroadcast(intent);
						break;
					case GlobalMsgTypes.msgHandShake:
						try {
							UserInfo usrInfo = new UserInfo(actualMsg);
							InitData.getInitData().msg3Arrive(
									usrInfo.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case GlobalMsgTypes.msgHandSHakeFriendList:
						try {
							InitData.getInitData().msg5Arrive(actualMsg);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case GlobalMsgTypes.msgUpdateFriendList:
						Intent intentp = new Intent(
								"com.airport.hello.MESSAGE_RECEIVED");
						intentp.putExtra("com.airport.hello.msg_received",
								actualMsg);
						intentp.putExtra("com.airport.hello.msg_type",
								msgType);
						mContext0.sendBroadcast(intentp);
						break;
					case GlobalMsgTypes.msgSignUp:
						RegisterActivity.getInstance().uponRegister(actualMsg);
						break;
					case GlobalMsgTypes.msgSearchPeople:
						System.out.println("进入msgSearchPeople");
						System.out.println("是否存在welcomeactivity"
								+ WelcomeActivity.getInstance());
						if (MainBodyActivity.getInstance() != null) {
							MainBodyActivity.getInstance().onReceiveSearchList(
									actualMsg);
							System.out.println("存在mainbodyactivity");
						}
						if (MainActivity.getInstance() != null) {
							MainActivity.getInstance().onReceiveSearchList(
									actualMsg);
							System.out.println("存在mainactivity");
						}
						if (WelcomeActivity.getInstance() != null) {
							WelcomeActivity.getInstance().onReceiveSearchList(
									actualMsg);
							System.out.println("存在welcomeactivity");
						}
						if (MapActivity.getInstance() != null) {
							MapActivity.getInstance().onReceiveSearchList(
									actualMsg);
							System.out.println("存在mapactivity");
						}
						if (CaptureActivity.getInstance() != null) {
							CaptureActivity.getInstance().onReceiveSearchList(
									actualMsg);
							System.out.println("存在captureactivity");
						}

						break;
					case GlobalMsgTypes.msgFriendshipRequest:
						uponReceivedMsg();
						Intent intent2 = new Intent(
								"com.airport.hello.FRIEND_REQUEST_MSGS");
						intent2.putExtra("com.airport.hello.msg_received",
								actualMsg);
						intent2.putExtra("com.airport.hello.msg_type",
								msgType);
						mContext0.sendBroadcast(intent2);
						break;
					case GlobalMsgTypes.msgFriendshipRequestResponse:
						uponReceivedMsg();
						Log.d("response comes",
								"+++++++++++++++++++++++++++++"
										+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

						Intent intent3 = new Intent(
								"com.airport.hello.FRIEND_REQUEST_MSGS");
						intent3.putExtra("com.airport.hello.msg_received",
								actualMsg);
						intent3.putExtra("com.airport.hello.msg_type",
								msgType);
						mContext0.sendBroadcast(intent3);
						break;
					case GlobalMsgTypes.msgFriendGoOnline:
						// FriendListInfo.getFriendListInfo().friendGoOnAndOffline(actualMsg,
						// 1);
						Intent intent6 = new Intent(
								"com.airport.hello.MESSAGE_RECEIVED");
						intent6.putExtra("com.airport.hello.msg_received",
								actualMsg);
						intent6.putExtra("com.airport.hello.msg_type",
								msgType);
						mContext0.sendBroadcast(intent6);
						break;
					case GlobalMsgTypes.msgFriendGoOffline:
						Intent intent7 = new Intent(
								"com.airport.hello.MESSAGE_RECEIVED");
						intent7.putExtra("com.airport.hello.msg_received",
								actualMsg);
						intent7.putExtra("com.airport.hello.msg_type",
								msgType);
						mContext0.sendBroadcast(intent7);
						break;
					case GlobalMsgTypes.msgLocationRequest:
						uponReceivedMsg();
						System.out.println("得到定位请求");
						Log.d("message received" + actualMsg,
								"++++++++++++++++++++"
										+ "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
						Intent intent8 = new Intent(
								"com.airport.locationRequest.MESSAGERECEIVED");
						intent8.putExtra("com.airport.locationRequest.msg",
								actualMsg);
						intent8.putExtra("com.airport.locationRequest.type",
								msgType);
						mContext0.sendBroadcast(intent8);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void uponReceivedMsg() {
		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgMsgReceived,
				"xxxxx");
	}

	public void closeBufferedReader() {
		try {
			mBuffRder0 = null;
		} catch (Exception e) {
		}
	}
}
