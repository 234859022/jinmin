package com.sanposs.project.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class AjaxUtil {
  
 
  
    /** 
     * @param response
     * @param outputString 
     * @throws java.io.IOException 
     */  
    public static void ajaxOutput(HttpServletResponse response, String outputString) throws IOException {  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter printWriter = response.getWriter();
        printWriter.write(outputString);  
        printWriter.flush();  
        printWriter.close();
    }  
    
    /** 
     * @param response
     * @param outputInt
     * @throws java.io.IOException
     */  
    public static void ajaxOutputRint(HttpServletResponse response, int outputInt) throws IOException {  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter printWriter = response.getWriter();
        printWriter.write(outputInt);
        printWriter.flush();
        printWriter.close();
    }  
    
    /** 
     *@param String
     */ 
	public static String decodeUTF8(String someStr) {
		String newStr = null;
		if(someStr!=null&&someStr.equals(""))
			return "";
		if(someStr!=null&&!someStr.equals("")) {
			try {
				newStr = URLDecoder.decode(someStr,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return newStr;
	}
	
	

    /** 
     *@param String
     */ 
	public static String encodeUTF8(String someStr) {
		String newStr = null;
		if(someStr!=null&&someStr.equals(""))
			return "";
		if(someStr!=null&&!someStr.equals("")) {
			try {
				newStr = URLEncoder.encode(someStr,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return newStr;
	}
}  
