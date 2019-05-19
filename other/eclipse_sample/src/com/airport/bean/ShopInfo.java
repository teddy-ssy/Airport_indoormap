package com.airport.bean;

import com.airport.commons.GlobalStrings;

public class ShopInfo {
	public static final String strSplitter = GlobalStrings.entityDivider;
	public static final int Food = 0;
	public static final int Drink = 1;
	public static final int Store = 2;

	private String label = "";
	private String chinese_s = "";
	private int shoptype;
	private int phone;
	private String introduction = "";

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getChinese_s() {
		return chinese_s;
	}

	public void setChinese_s(String chinese_s) {
		this.chinese_s = chinese_s;
	}

	public int getShoptype() {
		return shoptype;
	}

	public void setShoptype(int shoptype) {
		this.shoptype = shoptype;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public ShopInfo(int shoptype, int phone, String chinese_s, String label,
			String introduction) {
		this.shoptype = shoptype;
		this.phone = phone;
		this.chinese_s = chinese_s;
		this.label = label;
		this.introduction = introduction;
	}

	public ShopInfo(String str) {
		String[] arr0 = str.split(strSplitter);
		this.shoptype = Integer.parseInt(arr0[0]);
		this.phone = Integer.parseInt(arr0[1]);
		this.chinese_s = arr0[2];
		this.label = arr0[3];
		this.introduction = arr0[4];
	}

	public String toString() {
		String str = shoptype + strSplitter;
		str += phone + strSplitter;
		str += chinese_s + strSplitter;
		str += label + strSplitter;
		str += introduction + strSplitter;
		return str;
	}
}
