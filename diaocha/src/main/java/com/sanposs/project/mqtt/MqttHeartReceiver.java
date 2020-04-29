package com.sanposs.project.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class MqttHeartReceiver {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 处理工作消息
	 * @param message
	 * @return 返回0：数据格式问题 返回1：处理成功
	 */
	public int processMessage(String message){
		//TODO 用线程方式来处理接收到的心跳
		JSONObject object = JSON.parseObject(message);
		return 1;
	}
	
	public static void main(String[] args) {
		
	}
}
