package com.sanposs.project.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2018/12/19/019.
 */
public class HttpUtils {

    private static String charset = "utf-8";
    private static HttpClient httpClient = HttpClients.createDefault();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String doPost(String url, Map<String, String> map) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    /**
          * post请求
       */
        public static JSONObject doPost2(String url,JSONObject json){
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            JSONObject response = null;
            try {
                StringEntity s = new StringEntity(json.toString());
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");//发送json数据需要设置contentType

                post.setEntity(s);


                HttpResponse res = client.execute(post);
                if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        HttpEntity entity = res.getEntity();
                        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                        response = JSONObject.fromObject(result);
                    }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return response;
        }

}