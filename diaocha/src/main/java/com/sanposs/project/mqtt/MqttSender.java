package com.sanposs.project.mqtt;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;



/**
 * 类名称：MqttSender    
 * 类描述：消息队列发送端
 * 创建人：kanghuibin  
 * 创建时间：2018-8-4 下午03:54:41    
 * 修改人：wuyongde   
 * 修改时间：2018-8-4 下午03:54:41    
 * 修改备注：    
 * @version 1.0  
 */
public class MqttSender {

	private final static Logger logger = Logger.getLogger(MqttSender.class);
	private MqttClient client;
	public  MqttSender (MqttClient client) {
		if(MqttUtil.getClient()!=null){
			this.client = MqttUtil.getClient();
		}
	}
	
	/**
	 * 发布消息到指定的队列
	 * @param topic :队列名称
	 * @param message :消息
	 * @param qos :消息服务质量 (0:允许消息丢失场景（性能最高）,1:不允许消息丢失，但允许消息重复场景（性能中等）,2:不允许消息丢失，且不允许消息重复场景（性能最差）)
	 */
	public void sendMessage(String topic,String message,int qos){
		logger.info("发送消息,发送至Topic [" + topic + "] , 内容是:[" + message + "] ");
		try {
//			MqttMessage mqttMessage = new MqttMessage(message.getBytes("utf-8"));
//			mqttMessage.setQos(qos);
//			client.publish(topic, mqttMessage);
			
			//TODO fix bug mqtt ping超时(据说是因为机器资源耗尽)
			client.publish(topic, message.getBytes("utf-8"), qos, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
