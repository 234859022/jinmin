package com.sanposs.project.configurer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sanposs.project.mqtt.MqttUtil;
import com.sanposs.project.redis.RedisManager;


/**
 * @类名称：Mqtt配置启动类
 * @类描述：
 * 创建人：kanghuibin  
 * 创建时间：2018-8-4 下午02:54:41    
 * 修改人：wuyongde   
 * 修改时间：2018-8-4 下午23:54:41   
 * @修改备注：
 * @version 1.0
 */
@Component
public class Config implements CommandLineRunner{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Value("${MQTT_HOST}")
//	private String _MQTT_HOST;
//	@Value("${MQTT_SERVER_NAME}")
//	private String _MQTT_SERVER_NAME;
//	@Value("${MQTT_USER_NAME}")
//	private String _MQTT_USER_NAME;
//	@Value("${MQTT_PASS_WORD}")
//	private String _MQTT_PASS_WORD;
//	@Value("${MQTT_TOPIC_SUB}")
//	private String _MQTT_TOPIC_SUB;
//	@Value("${MQTT_TOPIC_HEART}")
//	private String _MQTT_TOPIC_HEART;
//	//redis
	@Value("${REDIS_IP}")
	private String _REDIS_IP;
	@Value("${REDIS_PORT}")
	private String _REDIS_PORT;
	@Value("${REDIS_ROLE}")
	private String _REDIS_ROLE;
	
	@Value("${PIC}")
	private String pic;
	
	
	//MQTT
	public static String MQTT_HOST;
	public static String MQTT_SERVER_NAME;
	public static String MQTT_USER_NAME;
	public static String MQTT_PASS_WORD;
	public static String MQTT_TOPIC_SUB;
	public static String MQTT_TOPIC_HEART;
	//REDIS
	public static String REDIS_IP;
	public static String REDIS_PORT;
	public static String REDIS_ROLE;
	public static String PIC;
	
	public static RedisManager redis;
	
	public void  init(){
		logger.info("***************初始化配置***********");
//		Config.MQTT_HOST = this._MQTT_HOST;
//		Config.MQTT_SERVER_NAME = this._MQTT_SERVER_NAME;
//		Config.MQTT_USER_NAME = this._MQTT_USER_NAME;
//		Config.MQTT_PASS_WORD = this._MQTT_PASS_WORD;
//		Config.MQTT_TOPIC_SUB = this._MQTT_TOPIC_SUB;
//		Config.MQTT_TOPIC_HEART = this._MQTT_TOPIC_HEART;
		
		Config.REDIS_IP = this._REDIS_IP;
		Config.REDIS_PORT = this._REDIS_PORT;
		Config.REDIS_ROLE = this._REDIS_ROLE;
		Config.PIC=pic;
		Config.redis = new RedisManager();// 单例redis
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MqttUtil.getInstance().start();
	}
	
	/**
	 * Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。
	 */
	public void run(String... arg0) throws Exception {
		init();
	}
}
