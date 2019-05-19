package com.airport.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.helpers.LocatorImpl;

import com.airport.bean.UserInfo;
import com.airport.commons.GlobalStrings;

public class LocationRequestEntity {

	private static final int accept = 102;
	private static final int refuse = 103;
	public static final int send = 0;
	public static final int response = 1;

	public UserInfo Requester;
	public int ResponsterId;
	public String Time;
	private int Status;
	private int Type = send;

	public LocationRequestEntity(UserInfo requester, int responsterId) {
		Status = refuse;
		Requester = requester;
		ResponsterId = responsterId;
		Time = getDate();
		Type = send;
	}

	public LocationRequestEntity(UserInfo requester, int responsterId, int type) {
		Status = refuse;
		Requester = requester;
		ResponsterId = responsterId;
		Time = getDate();
		Type = type;
	}

	public LocationRequestEntity(String str) {
		String[] arry = str.split(GlobalStrings.locationRequestDivider);
		Status = Integer.parseInt(arry[0]);
		Requester = new UserInfo(arry[1]);
		ResponsterId = Integer.parseInt(arry[2]);
		Time = arry[3];
		Type = Integer.parseInt(arry[4]);

	}

	public String toString() {
		System.out.println("status>>>>>>>>>>>>>" + Status);
		String str = Status + GlobalStrings.locationRequestDivider;
		System.out.println("Requester.toString()>>>>>>>>>>>>>"
				+ Requester.toString());
		str += Requester.toString() + GlobalStrings.locationRequestDivider;
		str += ResponsterId + GlobalStrings.locationRequestDivider;
		str += Time.toString() + GlobalStrings.locationRequestDivider;
		str += Type + GlobalStrings.locationRequestDivider;
		return str;
	}

	public boolean isAccept() {
		if (Status == accept) {
			return true;
		} else {
			return false;
		}
	}

	public void accept() {
		Status = accept;
	}

	public void refuse() {
		Status = refuse;
	}

	public UserInfo getRequester() {
		return Requester;
	}

	public int getResponsterId() {
		return ResponsterId;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;

	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
		Date date = new Date();
		String timex = dateFormat.format(date);
		return timex;
	}
}
