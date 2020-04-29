package com.sanposs.project.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.sanposs.project.configurer.Config;
import com.sanposs.project.utils.SerializeUtil;

public class RedisManager {

	private final static Logger logger = Logger.getLogger(RedisManager.class);

	protected static RedisManager redisManager = new RedisManager();
	/**
	 * 非切片客户端链接
	 */
	//protected  Jedis jedis;

	/**
	 * 非切片链接池
	 */
	public  JedisPool jedisPool;

	/**
	 * 切片客户端链接
	 */
	private ShardedJedis shardedJedis;

	/**
	 * 切片链接池
	 */
	private ShardedJedisPool shardedJedisPool;

	/**
	 * 构造函数
	 */
	public RedisManager(){
		logger.info("*****************初始化redis:"+Config.REDIS_IP+"*****************");
		initialPool();
		//initialShardedPool();
		//shardedJedis = shardedJedisPool.getResource();
		logger.info("*****************初始化redis:"+Config.REDIS_IP+"成功*****************");
	}

	public static RedisManager getInstance(){
		if(redisManager == null){
			redisManager =  new RedisManager();
		}
		return redisManager;
	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool(){
		try {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			//最大连接数, 默认8个
			config.setMaxTotal(500);
			//最大能够保持idel状态的对象数 
			config.setMaxIdle(50);
			//最小空闲连接数
			config.setMinIdle(30);
			//当池内没有返回对象时，最大等待时间  
			config.setMaxWaitMillis(100000);

			//当调用borrow Object方法时，是否进行有效性检查 
			config.setTestOnBorrow(true);

			jedisPool = new JedisPool(config, Config.REDIS_IP, Integer.valueOf(Config.REDIS_PORT), 100000);
			//jedis = jedisPool.getResource();

		} catch (Exception e) {
			logger.info("初始化非切片池异常"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 初始化切片池
	 */
	@SuppressWarnings("unused")
	private void initialShardedPool(){

		try {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			//最大连接数, 默认8个
			config.setMaxTotal(20);
			//最大能够保持idel状态的对象数 
			config.setMaxIdle(5);
			//最小空闲连接数
			config.setMinIdle(0);
			//当池内没有返回对象时，最大等待时间  
			config.setMaxWaitMillis(1000l);
			//当调用borrow Object方法时，是否进行有效性检查 
			config.setTestOnBorrow(false);
			// slave链接
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			shards.add(new JedisShardInfo(Config.REDIS_IP, Integer.valueOf(Config.REDIS_PORT), Config.REDIS_ROLE));
			// 构造池
			shardedJedisPool = new ShardedJedisPool(config, shards);
		} catch (Exception e) {
			logger.info("初始化切片池异常"+e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 保存字符串
	 * @param key
	 * @param value
	 */
	public void set(String key,String value){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}
	}

	/**
	 * 根据key获取字符串
	 * @param key
	 */
	public String get(String key){
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}
		
	}

	/**
	 * 保存实体
	 * @param key
	 * @param value
	 */
	public  void setObject(String key,Object value){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			jedis.set(key.getBytes(), SerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}
	}

	/**
	 * 根据key获取缓存中实体
	 * @param key
	 * @return
	 */
	public  Object getObject(String key){
		Object object = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(key.getBytes());
			if(value != null){
				object = SerializeUtil.unserialize(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}
		return object;
	}

	/**
	 * 删除离线消息
	 * @param key
	 */
	public void delObject(String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}

	}

	/**
	 * 清空数据
	 */
	public void flushDB(){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.flushDB();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}

	}
	
	/**
	 * 设备key的过期时间
	 * @param key 针对某个key
	 * @param seconds几秒后过期
	 */
	public void expire(String key,int seconds){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
                jedis.close();
            }
		}

	}
	
	public static void main(String[] args) {
		//String json = "{\"deviceNo\":\"2018090310000test\",\"DeviceLong\":\"118.60036234\",\"DeviceLati\":\"24.90165238\",\"speed\":\"120\",\"switch\":\"1\",\"temp\":\"23\",\"deviceType\":\"10003\",\"deviceName\":\"宝能人脸识别4\"}";
//		RedisManager redis = new RedisManager();
		Config.redis.set("real_heart_2018090310000test", "2018-11-01 16:04:00");
		//redis.set("real_message_2018090310010Car001", json);
//		System.out.println("没有存时间之前:"+redis.get("real_message_2018090310000test"));
//		redis.expire("real_heart_2018090310000test", 60);
//		System.out.println(redis.get("real_message_2018090310000test"));
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("存时间之后:"+redis.get("001"));
	}
	
}
