package com.sanposs.project.mqtt;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.sanposs.project.configurer.Config;

/**
 * @类名称：MqUtil
 * @类描述：
 * 创建人：kanghuibin  
 * 创建时间：2018-8-4 下午02:54:41    
 * 修改人：kanghuibin   
 * 修改时间：2018-8-4 下午02:54:41   
 * @修改备注：
 * @version 1.0
 */
public class MqttUtil {
	
	private final static Logger logger = Logger.getLogger(MqttUtil.class);
	
	private static MqttUtil mqUtil = new MqttUtil();
	private static MqttClient client;
	private static int[] qos;
	private static String[] topicFilters;
	
	public static MqttUtil getInstance() {
		if (mqUtil == null) {
			mqUtil = new MqttUtil();
		}
		return mqUtil;
	}
	
	/**
	 * 启动mqtt
	 * @return
	 */
	public boolean start(){
		try {
//			logger.info("*****************初始化MQTT:"+Config.MQTT_HOST+"*****************");
//			// MemoryPersistence设置clientid的保存形式，默认为以内存保存  
//			client = new MqttClient(Config.MQTT_HOST, Config.MQTT_SERVER_NAME, new MemoryPersistence());
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setCleanSession(true);// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接  
//			options.setUserName(Config.MQTT_USER_NAME);
//			options.setPassword(Config.MQTT_PASS_WORD.toCharArray());
//			options.setConnectionTimeout(10);// 设置超时时间 单位为秒 
//			options.setKeepAliveInterval(20);// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制(平台的心跳不使用这边的心跳来判定)
//			client.setCallback(new MqttCallback() {
//				
//						public void messageArrived(String topic,MqttMessage message) throws Exception {
//							String messagebody = new String(message.getPayload(), "UTF-8");
//							MqttReadThread thread = new MqttReadThread(topic, messagebody, client);
//							thread.run();
//						}
//						
//						public void deliveryComplete(IMqttDeliveryToken token) {
//			                //如果是QoS0的消息，token.resp是没有回复的
//							logger.info("消息发送成功! " + ((token == null || token.getResponse() == null) ? "null" : token.getResponse().getKey()));
//			            }
//						
//						public void connectionLost(Throwable cause) {
//							logger.error("连接失败,原因:" + cause);
//			                cause.printStackTrace();
//			            }
//			});
//			client.connect(options);
//			
//			// 订阅
//			qos =new int[]{2, 2};
//			topicFilters = new String[]{Config.MQTT_TOPIC_SUB, Config.MQTT_TOPIC_HEART};
//			client.subscribe(topicFilters, qos);// 多消息订阅
//			//client.subscribe(MqttConfig.MQTT_TOPIC_SUB, 2);// 单个订阅
//			logger.info("初始化MQTT完成.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化MQTT异常"+e);
			return false;
		}
	}
	
	public static void main(String[] args) {
		MqttUtil.getInstance().start();	
	}
	
	public static MqttClient getClient() {
		return client;
	}

	public static void setClient(MqttClient client) {
		MqttUtil.client = client;
	}
}
