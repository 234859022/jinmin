package com.nanwang.project.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.nanwang.project.constants.Constants;

/**
 * 请求对象的工具类
 */
public class RequestUtils {
	
	/**
	 * 过滤自定义的html特殊标签、字符
	 * @param str 字符串
	 * @return
	 */
	public static String htmlEscapeCustom(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		
		String resultStr = str;
		
		resultStr = resultStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		resultStr = resultStr.replaceAll("eval\\((.*)\\)", "");
		resultStr = resultStr.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		resultStr = resultStr.replaceAll("'", "&#39;");
		resultStr = resultStr.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
		
		return resultStr;
	}
	
	/**
	 * 反转义自定义的html特殊标签、字符
	 * @param str 字符串
	 * @return
	 */
	public static String htmlUnescapeCustom(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		
		String resultStr = str;
		
		resultStr = resultStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		resultStr = resultStr.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
		resultStr = resultStr.replaceAll("&#39;", "'");
		
		return resultStr;
	}

	
	/**
	 * 获取Email地址
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return          Email地址，email地址不正确，则返回null
	 */
	public static String getEmail(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return "";
		}
		if(DataValidator.isEmail(paramValue)){
			return paramValue;
		}
		return null;
	}
	
	/**
	 * 获取QQ地址
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return          Email地址，QQ不正确，则返回0
	 */
	public static long getQQ(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return 0;
		}
		if(DataValidator.isQq(paramValue)){
			return Long.parseLong(paramValue);
		}
		return 0;
	}
	
	/**
	 * 获取编码过的完整的URL，包括参数
	 * @param request 请求对象
	 * @return        编码过的完整URL
	 */
	public static String getEncodeFullUrl(HttpServletRequest request){
		String fullUrl = getFullUrl(request);
		try {
			String encodedFullUrl = URLEncoder.encode(fullUrl, Constants.DEFAULT_CHARSET);
			return encodedFullUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前页面的完整的路径
	 * @param request 请求对象
	 * @return        完整路径的URL
	 */
	public static String getFullUrl(HttpServletRequest request){
		String fullUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if(StringUtils.isNotEmpty(queryString)){
			fullUrl += "?" + queryString;
		}
		return fullUrl;
	}
	
	/**
	 * 获取request前一个页面的编码过的URL
	 * @param request 请求对象
	 * @return        URL
	 */
	public static String getEncodeRefererUrl(HttpServletRequest request){
		String fullUrl = request.getHeader("Referer");
		try {
			String encodedFullUrl = URLEncoder.encode(fullUrl, Constants.DEFAULT_CHARSET);
			return encodedFullUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取UNID的标识符
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return          结果集
	 */
	public static String getUnidParam(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return "";
		}
		if(DataValidator.isUnid(paramValue)){
			return paramValue;
		}
		return null;
	}
	
	/**
	 * 获取序列号的参数
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return
	 */
	public static String getSerialNumParam(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return "";
		}
		if(DataValidator.isNum(paramValue)){
			return paramValue;
		}
		return null;
	}
	
	/**
	 * 获取Xss过滤的参数
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return
	 */
	public static String getXssFilterParam(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(paramValue.length() + 16);  
        for (int i = 0, len = paramValue.length(); i < len; i++) {  
            char c = paramValue.charAt(i);  
            switch (c) {  
            case '>':  
                sb.append("＞");// 转义大于号   
                break;  
            case '<':  
                sb.append("＜");// 转义小于号   
                break;  
            case '\'':  
                sb.append("＇");// 转义单引号   
                break;  
            case '\"':  
                sb.append("＂");// 转义双引号   
                break;  
            case '&':  
                sb.append("＆");// 转义&   
                break;  
            default:  
                sb.append(c);  
                break;  
            }  
        }  
        
		return sb.toString();
	}
	
	/**
	 * 反转Xss过滤的参数
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return
	 */
	public static String getUnescapeXssFilterParam(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(paramValue.length() + 16);  
        for (int i = 0, len = paramValue.length(); i < len; i++) {  
            char c = paramValue.charAt(i);  
            switch (c) {  
            case '＞':  
                sb.append(">");// 反转义大于号   
                break;  
            case '＜':  
                sb.append("<");// 反转义小于号   
                break;  
            case '＇':  
                sb.append("\'");// 反转义单引号   
                break;  
            case '＂':  
                sb.append("\"");// 反转义双引号   
                break;  
            case '＆':  
                sb.append("&");// 反转义&   
                break;  
            default:  
                sb.append(c);  
                break;  
            }  
        }  
        
		return sb.toString();
	}
	
	/**
	 * 获取经过XSS过滤的内容
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return          XSS过滤的结果
	 */
	public static String getParameterFilterXSS(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
	    if (StringUtils.isEmpty(paramValue)) {
	      return "";
	    }
	    paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	    paramValue = paramValue.replaceAll("eval\\((.*)\\)", "");
	    paramValue = paramValue.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	    paramValue = paramValue.replaceAll("'", "&#39;");
	    paramValue = paramValue.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
	    
	    return paramValue;
	}
	
	/**
	 * 获取所有经过XSS过滤的内容
	 * @param request   请求对象
	 * @param paramName 参数名称
	 * @return          XSS过滤的结果
	 */
	public static String getParameterFilterAllXSS(HttpServletRequest request, String paramName){
		String paramValue = request.getParameter(paramName);
	    if (StringUtils.isEmpty(paramValue)) {
	      return "";
	    }
	    paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	    paramValue = paramValue.replaceAll("eval\\((.*)\\)", "");
	    paramValue = paramValue.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	    paramValue = paramValue.replaceAll("'", "&#39;");
	    paramValue = paramValue.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
	    paramValue = paramValue.replaceAll("script", "");
	    
	    return paramValue;
	}
	
	/**
	 * 获取整形的参数对象
	 * @param request      请求的对象
	 * @param paramName    参数名称
	 * @param defaultValue 默认的值
	 * @return             结果
	 */
	public static int getIntParam(HttpServletRequest request, String paramName, int defaultValue){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return 0;
		}
		if(DataValidator.isNum(paramValue)){
			return Integer.parseInt(paramValue);
		}else{
			return defaultValue;
		}
	}
	
	/**
	 * 获取长整形的内容
	 * @param request      请求的对象
	 * @param paramName    参数名称
	 * @param defaultValue 默认的值
	 * @return             长整形的内容，
	 */
	public static long getLongParam(HttpServletRequest request, String paramName, long defaultValue){
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isEmpty(paramValue)) {
			return 0;
		}
		if(DataValidator.isNum(paramValue)){
			return Long.parseLong(paramValue);
		}else{
			return defaultValue;
		}
	}
	
	/**
	 * 获取整形的参数对象
	 * @param request      请求的对象
	 * @param paramName    参数名称
	 * @return             结果,不是整形则返回为0
	 */
	public static int getIntParam(HttpServletRequest request, String paramName){
		return getIntParam(request, paramName, 0);
	}
}
