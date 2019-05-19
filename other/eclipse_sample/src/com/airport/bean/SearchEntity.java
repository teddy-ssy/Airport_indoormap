package com.airport.bean;

import com.airport.commons.GlobalStrings;

public class SearchEntity {

	private static int LOWER_BOUNDRY_OF_AGE = 5;
	private static int UPPER_BOUNDRY_OF_AGE = 80;
	public static int FEMALE_GENDER = 0;
	public static int MALE_GENDER = 1;
	public static int BOTH_GENDER = 2;
	public static int SEARCH_BY_NAME = 0;
	public static int SEARCH_BY_ELSE = 1;
	public static int SEARCH_BY_ONLINE = 2;
	public static int SEARCH_SHOP_BY_LABEL = 3;
	public static int SEARCH_FIGHT_BY_CODE = 4;
	public static int SEARCH_FIGHT_FROM_AIRPORT = 5;
	public static int SEARCH_FIGHT_ARRIVAL_AIRPORT = 6;
	public static int SEARCH_FIGHT_BY_ARPORT = 7;

	private int searchType = SEARCH_BY_NAME;
	private int ageLower = LOWER_BOUNDRY_OF_AGE;
	private int ageUpper = UPPER_BOUNDRY_OF_AGE;
	private int sex = BOTH_GENDER; // 0 for lady, 1 for guy, 10 for both
	private String name = "xx";
	private int shoptype;
	private int phone;
	private String chinese_s;
	private String label;
	private String introduction;
	private String fightnumber;
	private String takeoffcity;
	private String arrivalcity;
	private String airport;

	public SearchEntity(int type, int lage, int uage, int sex00, String name00) {
		if (type == SEARCH_BY_NAME || type == SEARCH_BY_ELSE
				|| type == SEARCH_BY_ONLINE) {
			this.searchType = type;
		}
		if (lage >= LOWER_BOUNDRY_OF_AGE && lage <= UPPER_BOUNDRY_OF_AGE) {
			this.ageLower = lage;
		}
		if (uage >= LOWER_BOUNDRY_OF_AGE && uage <= UPPER_BOUNDRY_OF_AGE) {
			this.ageUpper = uage;
		}
		if (sex00 == FEMALE_GENDER || sex00 == MALE_GENDER
				|| sex00 == BOTH_GENDER) {
			this.sex = sex00;
		}

		if (name00.length() > 0) {
			this.name = name00;
		}
	}

	public SearchEntity(int type, int shoptype, int phone, String chinese_s,
			String label, String introduction) {
		if (type == SEARCH_SHOP_BY_LABEL) {
			this.searchType = type;
			this.shoptype = shoptype;
			this.phone = phone;
			this.chinese_s = chinese_s;
			this.label = label;
			this.introduction = introduction;
		}
	}

	public SearchEntity(int type, String airport) {
		if (type == SEARCH_FIGHT_BY_ARPORT) {
			this.searchType = type;
			this.airport = airport;
		}

	}

	public SearchEntity(int searchtype, String fightnumber, String takeoffcity,
			String arrivalcity) {
		if (searchtype == SEARCH_FIGHT_FROM_AIRPORT) {
			this.searchType = searchtype;
			this.fightnumber = "-1";
			this.takeoffcity = takeoffcity;
			this.arrivalcity = "-1";
		} else if (searchtype == SEARCH_FIGHT_ARRIVAL_AIRPORT) {
			this.searchType = searchtype;
			this.fightnumber = "-1";
			this.takeoffcity = "-1";
			this.arrivalcity = arrivalcity;

		} else if (searchtype == SEARCH_FIGHT_BY_CODE) {
			this.searchType = searchtype;
			this.fightnumber = fightnumber;
			this.takeoffcity = "-1";
			this.arrivalcity = "-1";
		}
	}

	public SearchEntity(String str0) {
		String[] strArr0 = str0.split(GlobalStrings.entityDivider);
		this.searchType = Integer.parseInt(strArr0[0]);
		if (this.searchType == SEARCH_SHOP_BY_LABEL) {
			this.shoptype = Integer.parseInt(strArr0[1]);
			this.phone = Integer.parseInt(strArr0[2]);
			this.chinese_s = strArr0[3];
			this.label = strArr0[4];
			this.introduction = strArr0[5];
		} else if (this.searchType == SEARCH_FIGHT_ARRIVAL_AIRPORT
				|| this.searchType == SEARCH_FIGHT_BY_CODE
				|| this.searchType == SEARCH_FIGHT_FROM_AIRPORT) {
			this.fightnumber = strArr0[1];
			this.takeoffcity = strArr0[2];
			this.arrivalcity = strArr0[3];
		} else if (this.searchType == SEARCH_FIGHT_BY_ARPORT) {
			this.airport = strArr0[1];
		} else {
			this.ageLower = Integer.parseInt(strArr0[1]);
			this.ageUpper = Integer.parseInt(strArr0[2]);
			this.sex = Integer.parseInt(strArr0[3]);
			this.name = strArr0[4];
		}

	}

	public String toString() {
		String str0 = this.searchType + GlobalStrings.entityDivider;
		if (this.searchType == SEARCH_SHOP_BY_LABEL) {
			str0 += this.shoptype + GlobalStrings.entityDivider;
			str0 += this.phone + GlobalStrings.entityDivider;
			str0 += this.chinese_s + GlobalStrings.entityDivider;
			str0 += this.label + GlobalStrings.entityDivider;
			str0 += this.introduction + GlobalStrings.entityDivider;
		} else if (this.searchType == SEARCH_FIGHT_ARRIVAL_AIRPORT
				|| this.searchType == SEARCH_FIGHT_BY_CODE
				|| this.searchType == SEARCH_FIGHT_FROM_AIRPORT) {
			str0 += this.fightnumber + GlobalStrings.entityDivider;
			str0 += this.takeoffcity + GlobalStrings.entityDivider;
			str0 += this.arrivalcity + GlobalStrings.entityDivider;
		} else if (this.searchType == SEARCH_FIGHT_BY_ARPORT) {
			str0 += this.airport + GlobalStrings.entityDivider;
		} else {
			str0 += this.ageLower + GlobalStrings.entityDivider;
			str0 += this.ageUpper + GlobalStrings.entityDivider;
			str0 += this.sex + GlobalStrings.entityDivider;
			str0 += this.name + GlobalStrings.entityDivider;
		}
		return str0;
	}

	public int getType() {
		return searchType;
	}

	public int getLowerAge() {
		return ageLower;
	}

	public int getUpperAge() {
		return ageUpper;
	}

	public int getSex() {
		return sex;
	}

	public String getName() {
		return name;
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

	public String getChinese_s() {
		return chinese_s;
	}

	public void setChinese_s(String chinese_s) {
		this.chinese_s = chinese_s;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getFightnumber() {
		return fightnumber;
	}

	public void setFightnumber(String fightnumber) {
		this.fightnumber = fightnumber;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

}