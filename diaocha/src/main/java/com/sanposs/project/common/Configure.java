package com.sanposs.project.common;

public class Configure {
	private static String key = "2hfr7mYFeMdnsCkS0bH9LLUTXJxMqTxL";
	
	//小程序ID
	private static String appID = "wx2c945fa6c472fb1a";
	//商户号
	private static String mch_id = "1521978291";
	//
	private static String secret = "382d981fa33bc9f7ccb87efc9e83929c";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
