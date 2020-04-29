package com.sanposs.project.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor{
	
	//设置json内传输的date格式
	
	
	private String format = "yyyy-MM-dd HH:mm:ss";  
	  
    public JsonDateValueProcessor() {  
  
    }  
  
    public JsonDateValueProcessor(String format) {  
         this.format = format;  
    }  
  
    public Object processArrayValue(Object arg0, JsonConfig arg1) {  
        return process(arg0);  
    }  
  
    private Object process(Object arg0) {  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(arg0);  
    }  
  
    public Object processObjectValue(String key, Object value,  
            JsonConfig jsonConfig) {  
        if (value instanceof java.util.Date) {  
            String str = new SimpleDateFormat(format).format((Date) value);  
            return str;  
        }  
        if (null != value) {  
            return value.toString();  
        }  
        return "";  
    }  
}
