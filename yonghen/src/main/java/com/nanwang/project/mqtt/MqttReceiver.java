package com.nanwang.project.mqtt;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class MqttReceiver {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String apkCode;
	private MqttSender sender;
	
	/**
	 * 处理工作消息
	 * @param message
	 * @return 返回1：数据格式问题 返回1：处理成功 0:处理失败
	 */
	public int processMessage(String message,MqttClient client){
		JSONObject object = JSON.parseObject(message);
		// 进行消息过滤
		return 1;
	}
	
	
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("deviceNo", "rlsb1");
//		map.put("deviceName", "宝能人脸识别1");
//		map.put("deviceType", "10000");
//		map.put("deviceLong", "");
//		map.put("deviceLatu", "");
//		map.put("devicePos", "泉州市泉秀路");
//		map.put("deviceStatus", "0");// 0:正常 1：故障
//		map.put("deviceSendTime", "2018-08-09 9:00:15");
//		map.put("isEncrypt", "0");
//		map.put("DevicePicUrl", "");
		map.put("deviceNo", "123");
		map.put("control", "openDoor");
		String json = JSON.toJSONString(map);
		System.out.println(json);
	}
	
}
