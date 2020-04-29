package com.nanwang.project.utils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil { 
    private final static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 存放全部菜单url
     */
    public static Map<String, String> allMenuUrlMap = null;

    public static <T> T getFirstObject(List<T> list) {
        if (null == list || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    //判断一个字符串是否是整数
    public static boolean isInteger(String str) {
        boolean it = true;
        try {
            Integer.parseInt(str);
            it = true;
        } catch (Exception e) {
            it = false;
        }
        return it;
    }

    //判断一个字符串是否是Double
    public static boolean isDouble(String str) {
        boolean it = true;
        try {
            Double.parseDouble(str);
            it = true;
        } catch (Exception e) {
            it = false;
        }
        return it;
    }

    //判断一个字符串是否是Float
    public static boolean isFloat(String str) {
        boolean it = true;
        try {
            Float.parseFloat(str);
            it = true;
        } catch (Exception e) {
            it = false;
        }
        return it;
    }

    // 返回a到b之間(包括a,b)的任意一個自然数,如果a > b || a < 0，返回-1  
    public static int getRandomInt(int a, int b) {
        if (a > b || a < 0)
            return -1;
        // 下面两种形式等价  
        // return a + (int) (new Random().nextDouble() * (b - a + 1));  
        return a + (int) (Math.random() * (b - a + 1));
    }

    /**
     * @Title:       getValue 
     * @Description: TODO(获取map中key对应的int类型的值，没有返回defaultVal) 
     * @param map
     * @param key
     * @param defaultVal
     * @return 
     * @return int     返回类型
     */
    public static int getValue(Map map, Object key, int defaultVal) {
        if (map == null) {
            return defaultVal;
        } else {
            try {
                Object valO = map.get(key);
                if (valO instanceof String) {
                    return Integer.valueOf(valO.toString());
                } else {
                    return (Integer) valO;
                }
            } catch (Exception e) {
                log.error("", e);
                return defaultVal;
            }
        }
    }

    /**
     * @Title:       getValue 
     * @Description: TODO(获取map中key对应的String类型的值，没有返回defaultVal) 
     * @param map
     * @param key
     * @param defaultVal
     * @return 
     * @return String     返回类型
     */
    public static String getValue(Map map, Object key, String defaultVal) {
        if (map == null) {
            return defaultVal;
        } else {
            try {
                Object valO = map.get(key);
                if (valO == null) {
                    return defaultVal;
                }
                return valO + "";
            } catch (Exception e) {
                log.error("", e);
                return defaultVal;
            }
        }
    }

    /**
     * @Title:       getDownResponse 
     * @Description: TODO(获得下载文件的response) 
     * @param fileName
     * @param request
     * @param response
     * @throws Exception 
     * @return void     返回类型
     */
    public static void getDownResponse(String fileName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/octet-stream;charset=utf-8");
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        else
            fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "filename=" + fileName + ".xls");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param s
     * @return
     */
    public static String xssEncode(String value) {
        if (StringUtils.isBlank(value))
            return value;
        //       System.out.println(value+"==="+StringEscapeUtils.escapeHtml(value));
        //       return StringEscapeUtils.escapeHtml(value);     
        //或者下面的方法直接过滤特殊字符，但有时候不能这么做。        
        //       value = value.replace('<',' ');
        //       value = value.replace('>',' '); 
        //       value = value.replace('"',' ');
        //       value = value.replace('\'',' ');
        //       value = value.replace('/',' ');
        //       value = value.replace('%',' '); 
        //       value = value.replace(';',' '); 
        //       value = value.replace('(',' '); 
        //       value = value.replace(')',' '); 
        //       value = value.replace('&',' '); 
        //       value = value.replace('+','_'); 

        //或者直接转换成全角字符，有时候也不适用。
        StringBuilder sb = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
            case '>':
                sb.append('＞');//全角大于号
                break;
            case '<':
                sb.append('＜');//全角小于号
                break;
            case '\'':
                sb.append('‘');//全角单引号
                break;
            //暂时注释
            //           case '\"':
            //               sb.append('“');//全角双引号
            //               break;
            case '&':
                sb.append('＆');//全角
                break;
            case '\\':
                sb.append('＼');//全角斜线
                break;
            case '#':
                sb.append('＃');//全角井号
                break;
            case '%':
                sb.append('％');//全角％号
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }
    /**
     * 判断字符串是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj){
    	return (obj == null || obj.length()<= 0);
    }
    /**
     * 判断字符串是否不为空
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(String obj){
    	return (!isEmpty(obj));
    }
    
    /**
     * 生成编码
     * @param prefixstr 前缀
     * @return
     */
    public static String createNo(String prefixstr){
//    	String uuid = UUID.randomUUID().toString().replace("-", "");
//        int uuidcode=uuid.hashCode();
//        if(uuid.hashCode()<0){
//        	uuidcode=-uuidcode;
//        }
//        String.format("%011d", uuidcode);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return prefixstr+(sdf.format(new Date()));
    }
}
