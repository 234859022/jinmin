package com.nanwang.project.job;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 全局定时器
 * @author kanghuibin
 *
 */
@Component
@EnableScheduling
public class AccessTokenJob {

    private final Logger logger = Logger.getRootLogger();

    /**
     * 定时获取access_token
     * initialDelay:延迟2s执行
     * fixedDelay:定时1小时50分钟执行
     * 单位为:毫秒  (1s = 1000ms)
     * @throws SQLException 
     */
    @Scheduled(initialDelay = 2000,fixedDelay=7100000)
    public void getAccessToken() throws SQLException{
//        logger.info("==============开始获取access_token===============");
//        String access_token = null;
//        String grant_type = "client_credential";
//        String AppId= Configure.getAppID();
//        String secret= Configure.getSecret();
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;  
//
//        try {  
//            URL urlGet = new URL(url);  
//            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
//            http.setRequestMethod("GET"); // 必须是get方式请求  
//            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
//            http.setDoOutput(true);  
//            http.setDoInput(true);  
//            http.connect();   
//            InputStream is = http.getInputStream();
//            int size = is.available();  
//            byte[] jsonBytes = new byte[size];  
//            is.read(jsonBytes);  
//            String message = new String(jsonBytes, "UTF-8");  
//            JSONObject demoJson = JSONObject.fromObject(message);  
//            access_token = demoJson.getString("access_token");  
//            is.close();
//            logger.info("==============结束获取access_token===============");
//        } catch (Exception e) {  
//                e.printStackTrace();  
//        }
//        logger.info("==============开始写入access_token===============");
//        RedisManager.getInstance().set("AccessToken", access_token);
//        logger.info("==============写入access_token成功===============");
    }
}
