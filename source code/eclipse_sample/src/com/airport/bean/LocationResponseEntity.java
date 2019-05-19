package com.airport.bean;

import com.airport.commons.GlobalStrings;
import com.sails.engine.LocationRegion;

public class LocationResponseEntity {
	public static final String strSplitter = GlobalStrings.locationRegionDivider;
	private int floornumber = 1;
	private double latitude;
	private double longitude;

	public int getFloornumber() {
		return floornumber;
	}

	public void setFloornumber(int floornumber) {
		this.floornumber = 1;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public LocationResponseEntity() {

	}

	public LocationResponseEntity(int floornumber, double latitude,
			double longtiude) {
		this.floornumber = 1;
		this.latitude = latitude;
		this.longitude = longtiude;
	}

	public String toString() {
		String str = this.floornumber + strSplitter;
		str += this.latitude + strSplitter;
		str += this.longitude + strSplitter;
		return str;
	}

	public String toSendString() {
		String str = "楼层" + this.floornumber + "   &&   ";
		str += "经度" + this.latitude + "   &&   ";
		str += "纬度" + this.longitude + "   &&   ";
		return str;
	}

	public LocationResponseEntity(String str) {
		String[] arry = str.split(strSplitter);
		floornumber = 1;
		latitude = Double.parseDouble(arry[1]);
		longitude = Double.parseDouble(arry[2]);
	}
}
