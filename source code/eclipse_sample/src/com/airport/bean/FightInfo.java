package com.airport.bean;

import org.apache.http.entity.SerializableEntity;

import android.support.v4.os.ParcelableCompat;

import com.airport.commons.GlobalStrings;

public class FightInfo extends ParcelableCompat {

	public static final String strSplitter = GlobalStrings.entityDivider;
	private String fightnumber;
	private String planTakeoffTime;
	private String realTakeoffTime;
	private String planArrivalTime;
	private String realArrivalTime;
	private String takeoffcity;
	private String arrivalcity;
	private String enter;
	private String baggage;

	public FightInfo(String fightnumber, String planTakeoffTime,
			String realTakeoffTime, String planArrivalTime,
			String realArrivalTime, String takeoffcity, String arrivalcity,
			String enter, String baggage) {
		this.fightnumber = fightnumber;
		this.planTakeoffTime = planTakeoffTime;
		this.realTakeoffTime = realTakeoffTime;
		this.planArrivalTime = planArrivalTime;
		this.realArrivalTime = realArrivalTime;
		this.takeoffcity = takeoffcity;
		this.arrivalcity = arrivalcity;
		this.enter = enter;
		this.baggage = baggage;
	}

	public String toString() {
		String str = this.fightnumber + strSplitter;
		str += this.planTakeoffTime + strSplitter;
		str += this.realTakeoffTime + strSplitter;
		str += this.planArrivalTime + strSplitter;
		str += this.realArrivalTime + strSplitter;
		str += this.takeoffcity + strSplitter;
		str += this.arrivalcity + strSplitter;
		str += this.enter + strSplitter;
		str += this.baggage + strSplitter;
		return str;
	}

	public FightInfo(String str) {
		String[] arr0 = str.split(strSplitter);
		this.fightnumber = arr0[0];
		this.planTakeoffTime = arr0[1];
		this.realTakeoffTime = arr0[2];
		this.planArrivalTime = arr0[3];
		this.realArrivalTime = arr0[4];
		this.takeoffcity = arr0[5];
		this.arrivalcity = arr0[6];
		this.enter = arr0[7];
		this.baggage = arr0[8];
	}

	public String getFightnumber() {
		return fightnumber;
	}

	public void setFightnumber(String fightnumber) {
		this.fightnumber = fightnumber;
	}

	public String getPlanTakeoffTime() {
		return planTakeoffTime;
	}

	public void setPlanTakeoffTime(String planTakeoffTime) {
		this.planTakeoffTime = planTakeoffTime;
	}

	public String getRealTakeoffTime() {
		return realTakeoffTime;
	}

	public void setRealTakeoffTime(String realTakeoffTime) {
		this.realTakeoffTime = realTakeoffTime;
	}

	public String getPlanArrivalTime() {
		return planArrivalTime;
	}

	public void setPlanArrivalTime(String planArrivalTime) {
		this.planArrivalTime = planArrivalTime;
	}

	public String getRealArrivalTime() {
		return realArrivalTime;
	}

	public void setRealArrivalTime(String realArrivalTime) {
		this.realArrivalTime = realArrivalTime;
	}

	public String getTakeoffcity() {
		return takeoffcity;
	}

	public void setTakeoffcity(String takeoffcity) {
		this.takeoffcity = takeoffcity;
	}

	public String getArrivalcity() {
		return arrivalcity;
	}

	public void setArrivalcity(String arrivalcity) {
		this.arrivalcity = arrivalcity;
	}

	public String getEnter() {
		return enter;
	}

	public void setEnter(String enter) {
		this.enter = enter;
	}

	public String getBaggage() {
		return baggage;
	}

	public void setBaggage(String baggage) {
		this.baggage = baggage;
	}

}
