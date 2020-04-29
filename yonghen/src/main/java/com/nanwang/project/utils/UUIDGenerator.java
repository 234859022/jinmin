package com.nanwang.project.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * unid生成工具类
 *
 */
public class UUIDGenerator {
	
	private static final String DEFAULT_FORMAT = "yyyyMMddHHmmss";

 	public UUIDGenerator() { 
    } 
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID2(){
    	
    	String s = UUID.randomUUID().toString(); 
    	//去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }

	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){

		String s = UUID.randomUUID().toString();
		//去掉“-”符号
		return s;
	}

	public static void main(String[] args){
        System.out.println(UUIDGenerator.getUUID());

        try {
			System.out.println(UUIDGenerator.getNo(System.currentTimeMillis()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * 由毫秒获取日期字符串格式（yyyy-MM-dd HH:mm:ss）
	 * @param time 毫秒
	 * @return 日期字符串（yyyy-MM-dd HH:mm:ss）
	 * @throws ParseException
	 */
	public static String getNo(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String format = sdf.format(calendar.getTime()); // 取时分秒
		
		String string = String.valueOf(time).substring(10, 13); // 取毫秒
		
		int i=(int)(Math.random()*900)+100;
		String random = String.valueOf(i); // 三位随机数
		
		return format+string+random;
	}

	/**
	 * 随机生成字符 或 者数字
	 * @return
	 */
	private static String getRandom(){
		String value = "";
		Random random = new Random();

		value += String.valueOf(random.nextInt(10));

		return value;
	}
	/**
	 * 随机生成字符串（包含字符和数字）
	 * @param length 指定长度
	 * @return 返回set
	 */
	public static String getStrAndNum(int length) {
		Random random = new Random();
		String result="";
		for (int i=0;i<length;i++)
		{
			result+=random.nextInt(10);
		}
		return result;
	}

	/**
	 * Md5Encoder
	 *
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static String encode(String string) {
		byte[] hash = new byte[0];
		try {
			hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
}
