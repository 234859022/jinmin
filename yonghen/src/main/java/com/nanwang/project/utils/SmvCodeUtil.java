package com.nanwang.project.utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class SmvCodeUtil {
	private final static Logger logger = LoggerFactory.getLogger(SmvCodeUtil.class);
	
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
    //配置您申请的KEY
    public static final String APPKEY ="8517d10b-5410-4dde-9c7e-7ddeb601aeae";
    public static final String SECRECT ="21a9e850e54d4822b397c685698a94a2";
    public static final String URL_SIGN ="http://114.55.40.154:30001/MsgApi/AddSignatureApply";
    public static final String URL_SENDMSG ="http://114.55.40.154:30001/MsgApi/SendMsg";
    
    //2.发送短信
    /**
     * 短信接口
     * @param telephone
     * @param code 2：验证码短信，3：营销短信
     * @param content 【尚宝科技】您当前的短信验证码为:753951
     * @return 返回0失败，返回1成功
     */
    public static int sendMessage(String telephone,String code,String content){
    	int num = 0;//
    	Gson gson = new Gson();
        String result =null;
        String url = URL_SENDMSG;//请求接口地址
        Map<String,String> params = new HashMap<String,String>();//请求参数
        params.put("secret",SECRECT);
        params.put("key",APPKEY);
        params.put("mobile",telephone);//接收短信的手机号码
        params.put("sms_type",code);//短信类型，2：验证码短信，3：营销短信
        params.put("content",content);//短信内容uid
        String json = gson.toJson(params);
        logger.info("本次短信内容为:["+json+"]");
        try {
            result =sendHttpPost(url, json);
            JSONObject object = JSON.parseObject(result);
//            if((boolean)object.get("isSuccess")){
//            	num = 1;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
    
    /**
     * 注短信标头册信息
     * @param signature(短信标头)
     * @return
     * @throws Exception 
     */
    public static String signSms(String signature) throws Exception{
    	Gson gson = new Gson();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("key", APPKEY);
        params.put("secret", SECRECT);
        params.put("signature", signature);
        String json = gson.toJson(params);
        //JSONObject jsonObject = JSONObject.toJSON(params); 
        String result =	sendHttpPost(URL_SIGN,json);
		return result;
    }
    
    /**
     * http post方式请求
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static String sendHttpPost(String url, String body) throws Exception {
    	 CloseableHttpClient httpClient = HttpClients.createDefault();
    	 HttpPost httpPost = new HttpPost(url);
    	 httpPost.addHeader("Content-Type", "application/json");
    	 httpPost.setEntity(new StringEntity(body,"UTF-8"));
    	 CloseableHttpResponse response = httpClient.execute(httpPost);
    	 System.out.println(response.getStatusLine().getStatusCode() + "\n");
    	 HttpEntity entity = response.getEntity();
    	 String responseContent = EntityUtils.toString(entity, "UTF-8"); 
    	 System.out.println(responseContent);
    	 response.close();
    	 httpClient.close();
    	 return responseContent;
   }
    
    public static void main(String[] args) {
    	try {
    		//signSms("智乐迪车联网");
    		sendMessage("15750801052","2","【尚宝科技】您当前的短信验证码为:753951");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }























}
