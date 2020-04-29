package com.nanwang.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类
 *
 */
public class DataValidator {

	/**
	 * 验证手机号格式是否正确
	 * @param mobile 手机号
	 * @return 手机号格式正确返回true,否则返回false
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(19[0-9])|(16[0-9])|(147))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * 是否是整数
	 * @param value 验证的值
	 */
	public static boolean isIntege(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^-?[1-9]\\d*$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是正整数
	 * @param value 验证的值
	 */
	public static boolean isPositiveIntege(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[1-9]\\d*$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是负整数
	 * @param value 验证的值
	 */
	public static boolean isNegativeIntege(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^-[1-9]\\d*$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是数字
	 * @param value 验证的值
	 */
	public static boolean isNum(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^([+-]?)\\d*\\.?\\d+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是正数（正整数 + 0）
	 * @param value 验证的值
	 */
	public static boolean isPositiveNum(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[1-9]\\d*|0$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是负数（负整数 + 0）
	 * @param value 验证的值
	 */
	public static boolean isNegativeNum(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^-[1-9]\\d*|0$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是浮点数
	 * @param value 验证的值
	 */
	public static boolean isDecmal(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^([+-]?)\\d*\\.\\d+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是正浮点数
	 * @param value 验证的值
	 */
	public static boolean isPositiveDecmal(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是负浮点数
	 * @param value 验证的值
	 */
	public static boolean isNegativeDecmal(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是浮点数
	 * @param value 验证的值
	 */
	public static boolean isDecmal3(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是非负浮点数（正浮点数 + 0）
	 * @param value 验证的值
	 */
	public static boolean isDecmal4(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是非正浮点数（负浮点数 + 0）
	 * @param value 验证的值
	 */
	public static boolean isDecmal5(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是邮件
	 * @param value 验证的值
	 */
	public static boolean isEmail(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 判断是否为UNID
	 * @param value 输入的值
	 * @return      判断的结果
	 */
	public static boolean isUnid(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p= Pattern.compile("^[a-zA-Z0-9]{0,32}");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是颜色
	 * @param value 验证的值
	 */
	public static boolean isColor(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[a-fA-F0-9]{6}$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是url
	 * @param value 验证的值
	 */
	public static boolean isUrl(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是中文
	 * @param value 验证的值
	 */
	public static boolean isChinese(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是ACSII字符
	 * @param value 验证的值
	 */
	public static boolean isAscii(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[\\x00-\\xFF]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是邮编
	 * @param value 验证的值
	 */
	public static boolean isZipcode(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^\\d{6}$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	
	/**
	 * 是否是ip地址
	 * @param value 验证的值
	 */
	public static boolean isIp(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是非空
	 * @param value 验证的值
	 */
	public static boolean isNotEmpty(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^\\S+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是图片
	 * @param value 验证的值
	 */
	public static boolean isPicture(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是压缩文件
	 * @param value 验证的值
	 */
	public static boolean isRar(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("(.*)\\.(rar|zip|7zip|tgz)$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是日期
	 * @param value 验证的值
	 */
	public static boolean isDate(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是QQ号码
	 * @param value 验证的值
	 */
	public static boolean isQq(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[1-9]*[1-9][0-9]*$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是电话号码的函数(包括验证国内区号,国际区号,分机号)
	 * @param value 验证的值
	 */
	public static boolean isTel(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	 * @param value 验证的值
	 */
	public static boolean isUsername(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^\\w+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是字母
	 * @param value 验证的值
	 */
	public static boolean isLetter(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[A-Za-z]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是大写字母
	 * @param value 验证的值
	 */
	public static boolean isUpperLetter(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[A-Z]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是小写字母
	 * @param value 验证的值
	 */
	public static boolean isLowerLetter(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^[a-z]+$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 是否是价格
	 * @param value 验证的值
	 */
	public static boolean isPrice(String value){
		Pattern p=null;//正则表达式
		Matcher m=null;//操作符表达式
		boolean b=false;
		p=Pattern.compile("^([1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|\\.[0-9]{1,2})$");
		m=p.matcher(value);
		b=m.matches();
		return b;
	}
	
	/**
	 * 检查用户名是否为中文、英文、数字、下划线、@、英文点号等字符组成
	 * @param value
	 * @return
	 */
	public static boolean isUserName(String value) {
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile("^[a-zA-Z0-9@._\u4e00-\u9fa5]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 检查密码是否为英文、数字等字符组成
	 * @param value
	 * @return
	 */
	public static boolean isPassword(String value) {
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile("^[a-zA-Z0-9]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 检查分辨率是否为1024*768的字符串格式
	 * @param value
	 * @return
	 */
	public static boolean isResolution(String value) {
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile("^[0-9]{3,4}[x]{1}[0-9]{3,4}$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 检查是否为mac地址，68-F7-28-85-D1-2C
	 * @param value
	 * @return
	 */
	public static boolean isMac(String value) {
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile("^([0-9A-Z]{2}[-]{1}){5}[0-9A-Z]{2}$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 检查是否为所属系统标识
	 * @param value
	 * @return
	 */
	public static boolean isBelongToAppUnid(String value) {
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile("^([a-z0-9A-Z]+[,a-z0-9A-Z]*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 验证是否为url地址
	 * @param value
	 * @return
	 */
	public static boolean isURL(String value) {
		// 该正则表达式匹配的字符串必须以http://、https://、ftp://开头
		// 该正则表达式能匹配URL或者IP地址（如：http://www.baidu.com 或者 http://192.168.1.1）
		// 该正则表达式能匹配到URL的末尾，即能匹配到子URL
		// （如能匹配：http://www.baidu.com/s?wd=a&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&inputT=1236）
		// 该正则表达式能够匹配端口号
		String regex = "((http|ftp|https)://)"
				+ "(" 
				+ "([a-zA-Z0-9._-]+.[a-zA-Z]{2,6})" // 匹配URL地址www.baidu.com
				+ "|" + "(([0-9]{1,3}.){3}[0-9]{1,3})" // 匹配IP地址192.168.1.1
				+ ")"
				+ "(:[0-9]{1,4})*" // 匹配端口号
				+ "(/[a-zA-Z0-9&%_./-~-]*)?";
		Pattern p = null;//正则表达式
		Matcher m = null;//操作符表达式
		boolean b = false;
		p = Pattern.compile(regex);
		m = p.matcher(value);
		b = m.matches();
		return b;
	}
	
	/**
	 * 特殊字符验证替换
	 * @param param
	 * @return
	 */
	public static String transactSqlInjection(String param) {
		String result = param;
		if (result == null || "".equals(result)){
			return "";
		}
		String regEx = "' + ltrim('') + '| |'|--|;|=|\\<|\\>|\\.|\\#|\\!|\\&|\\||\\$|\\%|\\@|\\\"|\\(|\\)|\\<|\\>|\\+|\\,|select |exec | and | or |insert |delete |update | count | chr | mid | master | truncate | char | declare | sys | dbms ";
		try {
			Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(result);
			result = matcher.replaceAll("");
		} catch (Exception ex) {
			result = "";
		}
		return result;
	}
}

