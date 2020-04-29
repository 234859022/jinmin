package com.sanposs.project.mqtt;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * 类名称：MqttSender    
 * 类描述：MQTT读
 * 创建人：kanghuibin  
 * 创建时间：2018-8-4 下午03:54:41    
 * 修改人：kanghuibin   
 * 修改时间：2018-8-4 下午03:54:41    
 * 修改备注：    
 * @version 1.0  
 */
public class MqttReadThread extends Thread{
	private final static Logger logger = Logger.getLogger(MqttReadThread.class);
	
	String topic ;
	String message ;
	MqttClient client;
	int result = 1;
	
	public MqttReadThread(String topic, String message, MqttClient client) {
		this.topic = topic;
        this.message = message;
        this.client = client;
    }
	
	public void run() {
		logger.info("接收到消息,来至Topic [" + topic + "] , 内容是:[" + message + "] ");
		try {
			// 启动MQ消息接收
			if(topic.indexOf("heart") == -1){
				MqttReceiver mqttReceiver = new MqttReceiver();
				result = mqttReceiver.processMessage(message, client);
			}else{
				MqttHeartReceiver mqttHeartReceiver = new MqttHeartReceiver();
				result = mqttHeartReceiver.processMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == 0){
			logger.info("来自Topic [" + topic + "] , 内容是:[" + message + "]的消息,由于格式错误已经被平台过滤...");
			
		}
	}
	
	public static void main(String[] args) {
		
	}
}
