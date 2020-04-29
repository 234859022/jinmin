package com.nanwang.project.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nanwang.project.constants.Constants;

/**
 * 日期工具类

 */
public class DateUtils {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String SIMPLE_FORMAT = "yyyy-MM-dd";
	
	private static final String SIMPLE_SHORT_FORMAT = "yyyyMMdd";
	
	private static final String YM_FORMAT = "yyyy-MM";
	
	
	private static final String MAIL_FORMAT = "yyyy年MM月dd日 HH:mm:ss";

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	// 时间格式数组
	private static String[] formatArray = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH:mm:ss", "yy-MM-dd HH:mm", "yyyyMMdd HH:mm", "yyyy-MM-dd HH" };


	public static boolean sameDate(Date d1, Date d2) {
		if(null == d1 || null == d2)
			return false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		return  cal1.getTime().equals(cal2.getTime());
	}

	/**
	 * 由日期字符串格式（yyyy-MM-dd HH:mm:ss）获取毫秒
	 * @param dateStr 日期字符串（yyyy-MM-dd HH:mm:ss）
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static long parseDateStrToTime(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		return sdf.parse(dateStr).getTime();
	}

	/**
	 * Date 转STRING （yyyy-MM-dd HH:mm:ss）
	 * @param date 日期
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static String parseToDateStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  sdf.format(date);
	}

	/**
	 * Date 转STRING （yyyy-MM-dd HH:mm:ss）
	 * @param date 日期
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static long parseToDateStrFromDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= 0;
		try {
			time = parseDateStrToTime(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * Date 转STRING （yyyy-MM-dd HH:mm:ss）
	 *
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static String parseToDateStrZero(String strDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
		String result =  strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +  strtodate.getDay() + " 00:00:00";
		return result;
	}
	/**
	 * Date 转STRING （yyyy-MM-dd HH:mm:ss）
	 *
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static String parseToDateStrZero(String strDate,String timeStr){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
		String result = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ timeStr;
		return result;
	}
	public static  String nextDay(String day){
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day1 = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day1 + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		 return dayAfter;
	}

	public static  String minusHour(String dateTime,String startTimeStr, String endTimeStr){
		String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
		String result2 = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ endTimeStr;
		try {
			String preDate = preDay(parseTimeToDateStr(getTimeCurMillis()));
			strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(preDate)).toString().split("-");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String result = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ startTimeStr;
		long startTime = 0;
		long endTime =0;

		try {
			startTime = parseDateStrToTime(result);
			endTime = parseDateStrToTime(result2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(startTime > endTime){
			long temp = startTime;
			startTime = endTime;
			endTime = temp;
		}

		long between = endTime - startTime;
		String dayAfter = null;
		try {
			dayAfter = parseTimeToDateStr(parseDateStrToTime(dateTime) - between);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dayAfter;
	}

	public static  String preDay(String day){
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day1 = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day1- 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return dayAfter;
	}

	public static  String preDayEnd(String day,String start,String end){
		Calendar c = Calendar.getInstance();
		Date date = null;
		String dayAfter = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
			String result2 = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ end;
			if(getTimeDiffFlag(parseDateStrToTime(day),parseDateStrToTime(result2)) >=0){
				c.setTime(date);
				int day1 = c.get(Calendar.DATE);
				c.set(Calendar.DATE, day1- 1);

				 dayAfter = (new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())) +" " + start;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dayAfter;
	}

	public static  String toDayBegin(String day,String start,String end,boolean toEndFlag){
		Calendar c = Calendar.getInstance();
		Date date = null;
		String dayAfter = day;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(day)).toString().split("-");
			String result2 = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ start;
			if(toEndFlag)
				result2 = strNow[0] +"-"+ strNow[1] +"-"+ strNow[2] +" "+ end;
			if(getTimeDiffFlag(parseDateStrToTime(day),parseDateStrToTime(result2)) <0){
				dayAfter =result2;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dayAfter;
	}

	/**
	 * Date 转STRING （yyyy-MM-dd HH:mm:ss）
	 * @param strDate 日期
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static Date parseToDate(String strDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	/**
	 * 由日期字符串格式（yyyy-MM-dd）获取毫秒
	 * @param dateStr 日期字符串（yyyy-MM-dd）
	 * @return 毫秒
	 * @throws ParseException
	 */
	public static long parseSimpleDateStrToTime(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_FORMAT);
		return sdf.parse(dateStr).getTime();
	}
	
	/**
	 * 由毫秒获取日期字符串格式（yyyy-MM-dd HH:mm:ss）
	 * @param time 毫秒
	 * @return 日期字符串（yyyy-MM-dd HH:mm:ss）
	 * @throws ParseException
	 */
	public static String parseTimeToDateStr(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 由毫秒获取日期字符串格式（yyyy-MM-dd HH:mm:ss）
	 * @param time 毫秒
	 * @return 日期字符串（yyyy-MM-dd HH:mm:ss）
	 * @throws ParseException
	 */
	public static String parseTimeToMailDateStr(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(MAIL_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 由毫秒获取日期字符串格式（yyyy-MM-dd）
	 * @param time 毫秒
	 * @return 日期字符串（yyyy-MM-dd）
	 * @throws ParseException
	 */
	public static String parseTimeToSimpleDateStr(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 由毫秒获取日期字符串格式（yyyyMMdd）
	 * @param time 毫秒
	 * @return 日期字符串（yyyyMMdd）
	 * @throws ParseException
	 */
	public static String parseTimeToSimpleShortDateStr(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_SHORT_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 由毫秒获取日期字符串格式（yyyy-MM）
	 * @param time 毫秒
	 * @return 日期字符串（yyyy-MM）
	 * @throws ParseException
	 */
	public static String parseTimeToYMDateStr(long time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(YM_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取发布时间
	 * @param time
	 * @return
	 */
	public static String getPublishTimeStr(long time) {
		String timeStr = null;
		long curTime = System.currentTimeMillis();
		long timeDiff = curTime - time;
		
		Calendar today = Calendar.getInstance();
		int nowYear = today.get(Calendar.YEAR);
		
		Date date = new Date(time);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
		
		if(timeDiff > 0 && timeDiff < 60*1000L){
			timeStr = "刚刚";
		} else if(timeDiff >= 60*1000L && timeDiff < 60*60*1000L){
			timeStr = timeDiff/60/1000 +"分钟前";
		} else if(timeDiff >= 60*60*1000L && timeDiff < 24*60*60*1000L){
			timeStr = timeDiff/60/60/1000 +"小时前";
		} else if(timeDiff >= 24*60*60*1000L && timeDiff < 7*24*60*60*1000L){
			timeStr = timeDiff/24/60/60/1000 +"天前";
		} else if(timeDiff >= 7*24*60*60*1000L && nowYear == year){
			timeStr = DateUtils.dateToString(date, "MM月dd日");
		} else{
			timeStr = DateUtils.dateToString(date, "yy年MM月dd日");
		}
		
		return timeStr;
	}
	
	/**
	 * 将日期转成某种格式的字符串
	 * @param date 日期
	 * @param format 格式
	 * @returnString
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 获取一天开始时间，00:00:00 000
	 */
	public static long getDayBeginTime(long time) {
		
		if (time == 0) {
			return time;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTimeInMillis();
	}

	/**
	 * 获取一天结束时间，23:59:59 999
	 */
	public static long getDayEndTime(long time) {
		
		if (time == 0) {
			return time;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTimeInMillis();
	}

	/**
	 * 获取一天结束时间，23:59:59 999
	 */
	public static long getDayYestime(long time) {

		if (time == 0) {
			return time;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);


		int day1 = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day1- 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 计算时间差，返回时分秒
	 * @param startTime 开始
	 * @param endTime 结束
	 * @return
	 */
	public static String getTimeDiff(long startTime, long endTime){
		
		if(startTime > endTime){
			long temp = startTime;
			startTime = endTime;
			endTime = temp;
		}
		
		long between = endTime - startTime;
        long hour = (between / (60 * 60 * 1000));
        long min = ((between / (60 * 1000)) - hour * 60);
        long s = (between / 1000 - hour * 60 * 60 - min * 60);
        return hour + "小时" + min + "分" + s + "秒";
	}
	
	/**
	 * 获取某年某月的第一天的开始时间
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public static long getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
 		cal.set(Calendar.MINUTE, 0);
 		cal.set(Calendar.SECOND, 0);
 		cal.set(Calendar.MILLISECOND, 0);
 		
 		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取某年某月的最后一天的结束时间
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public static long getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();     
	    cal.set(Calendar.YEAR, year);     
	    cal.set(Calendar.MONTH, month-1);     
	    cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
	    cal.set(Calendar.HOUR_OF_DAY, 23);
 		cal.set(Calendar.MINUTE, 59);
 		cal.set(Calendar.SECOND, 59);
 		cal.set(Calendar.MILLISECOND, 999);
 		
 		return cal.getTimeInMillis();
	}
	/**
	 * 获取某年某月某天的开始时间
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public static long getFirstDayOfDay(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month-1);  
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
 		cal.set(Calendar.MINUTE, 0);
 		cal.set(Calendar.SECOND, 0);
 		cal.set(Calendar.MILLISECOND, 0);
 		
 		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取某年某月某天的结束时间
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public static long getLastDayOfDay(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();     
	    cal.set(Calendar.YEAR, year);     
	    cal.set(Calendar.MONTH, month-1);     
	    cal.set(Calendar.DAY_OF_MONTH, day);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
 		cal.set(Calendar.MINUTE, 59);
 		cal.set(Calendar.SECOND, 59);
 		cal.set(Calendar.MILLISECOND, 999);
 		
 		return cal.getTimeInMillis();
	}

	/**
	 * @return 返回今毫秒数
	 */
	public static long getTimeCurMillis() {
		try {
			Date curDate = new Date();
			return curDate.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

    /**
     * @return 返回今天下午五点的毫秒数
     */ 
    public static long getTimeMillis() { 
        try { 
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss"); 
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd"); 
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " 23:59:59");
            return curDate.getTime(); 
        } catch (ParseException e) { 
            e.printStackTrace(); 
        } 
        return 0; 
    }

	/**
	 * @return 返回今天下午五点的毫秒数
	 */
	public static Date getEndTimeMillis() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " 23:59:59");
			return curDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return 返回今天下午五点的毫秒数
	 */
	public static Date getEndMonthMillis() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String startTimeStr = parseTimeToDateStr(getTimeMillis());
			String[]  strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(startTimeStr)).toString().split("-");
			String result = strNow[0] + "-" + strNow[1] + "-" + "31 " + "23:59:59";
			Date curDate = dateFormat.parse(result);
			return curDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 获取一个月后的时间
     * @param time
     * @return
     */
    public static long getMonthLaterTime(long time) {
    	Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.MONTH, 1);
		
		return cal.getTimeInMillis();
    }

	/**
	 * 获取n分钟后的时间
	 * @param timeafter
	 * @return
	 */
	public static long getMinLaterTime(int timeafter) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimeCurMillis());
		cal.add(Calendar.MINUTE,0- timeafter);

		return cal.getTimeInMillis();
	}


	/**
     * 获取三个月前的时间
     * @param time
     * @return
     */
    public static long getThreeMonthBeforeTime(long time) {
    	Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.MONTH, -3);
		
		return cal.getTimeInMillis();
    }
    
    /**
     * @param timeStr 今天某个时间点，如 17:00:00
     * @return 返回今天某个时间点的毫秒数
     */ 
    public static long getTodayTimeMillis(String timeStr) { 
        try { 
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss"); 
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd"); 
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + timeStr);
            return curDate.getTime(); 
        } catch (ParseException e) { 
            e.printStackTrace(); 
        } 
        return 0; 
    }

    /**
    * 过去年每个月开始时间与结束时间
    * @return List<Map<String,Object>>    返回类型
    * @author zjunbao
    * @throws
     */
    public static List<Map<String, Object>> getMonthTimeByYear(int year){
//    	Calendar today = Calendar.getInstance();
//    	int nowYaer = today.get(Calendar.YEAR);
//    	int nowMonth = today.get(Calendar.MONTH) + 1;
//    	if(year > nowYaer){
//    		return null;
//    	}
    	List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	Map<String, Object> map;
    	for(int i = 1 ; i <= 12 ; i++){
    		map = new HashMap<String, Object>();
    		map.put("beginTime", getFirstDayOfMonth(year, i));
    		map.put("endTime", getLastDayOfMonth(year, i));
/*    		if(year == nowYaer && i == nowMonth){
    			map.put("endTime", System.currentTimeMillis());
    			result.add(map);
    			break;
    		}*/
    		result.add(map);
    	}
    	return result;
	}
    
    public static List<Map<String, Object>> getDayTimeByMonth(int year, int month){
//    	Calendar today = Calendar.getInstance();
//    	int nowYaer = today.get(Calendar.YEAR);
//    	if(year > nowYaer){
//    		return null;
//    	}
//    	int nowMonth = today.get(Calendar.MONTH)+1;
//    	int nowDay = today.get(Calendar.DAY_OF_MONTH);
    	
    	Calendar cal =  Calendar.getInstance();
    	cal.set(Calendar.YEAR, year);     
	    cal.set(Calendar.MONTH, month-1); 
	    
    	int lastDay = cal.getActualMaximum(Calendar.DATE);
    	
    	List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	Map<String, Object> map;
    	for(int i = 1 ; i <= lastDay; i++){
    		map = new HashMap<String, Object>();
    		map.put("beginTime", getFirstDayOfDay(year, month, i));
    		map.put("endTime", getLastDayOfDay(year, month, i));
//    		if(year == nowYaer && month == nowMonth && i == nowDay){
//    			map.put("endTime", System.currentTimeMillis());
//    			result.add(map);
//    			break;
//    		}
    		result.add(map);
    	}
    	return result;
    }

	/**
	 * 计算时间差，返回时分秒
	 * @param startTime 开始
	 * @param endTime 结束
	 * @return
	 */
	public static String getTimeDiffFlagTrans(String startTime, String endTime) {
		Date now = new Date();
		String theDate = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(startTime)).toString();

		String theDate2 = new SimpleDateFormat("HH:mm:ss").format(parseToDate(endTime)).toString();

		Date date2 = parseToDate(theDate+ " " + theDate2);

		return parseToDateStr(date2);

	}

	public static  Map minusHourToNight(String dateTime,String startTimeStr, String endTimeStr,boolean flag) {

		String[]  strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(dateTime)).toString().split("-");
		String result = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + startTimeStr;
		String result2 = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + endTimeStr;
		String midNight = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + "23:59:59";
		long startTime = 0;
		long endTime = 0;
		long theTime = 0;
		long midTime = 0;
		Map timeMap = new HashedMap();
		try {
			theTime = parseDateStrToTime(dateTime);
			startTime = parseDateStrToTime(result);
			endTime = parseDateStrToTime(result2);
			midTime =parseDateStrToTime(midNight);


			if(flag) { 
				if (getTimeDiffFlag(theTime, startTime) < 0) {//超过过夜开始  theTime 大于 startTime 不分段
					long min = getTimeCheckSpan(theTime, midTime, 0);
					timeMap.put(Constants.nightTimeCode, min);
					timeMap.put(Constants.normalTimeCode, 0);
					logger.info("-------分解时长" + min+ "（" +dateTime+ " -"+midNight+"）----------");
				} else {
					long min = getTimeCheckSpan(startTime, midTime, 0);
					timeMap.put(Constants.nightTimeCode, min);
					logger.info("-------分解时长1," + min+ "（" +result+ " -"+midNight+"）----------");
					min = getTimeCheckSpan(theTime, startTime, 0);
					timeMap.put(Constants.normalTimeCode, min);
					logger.info("-------分解时长2," + min+ "（" +dateTime+ " -"+result+"）----------");
				}
			}
			else {
				midNight = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + "00:00:00";
				midTime =parseDateStrToTime(midNight);
				if (getTimeDiffFlag(endTime, theTime) < 0) {//没超过结束  endTimeStr 大于 theTime 不分段
					long min = getTimeCheckSpan(theTime, midTime, 0);
					timeMap.put(Constants.nightTimeCode, min);
					timeMap.put(Constants.normalTimeCode, 0);
					logger.info("-------分解时长" + min+ "（" +dateTime+ " -"+midNight+"）----------");
				}else{
					long min = getTimeCheckSpan( midTime,endTime, -2 );
					timeMap.put(Constants.nightTimeCode, min);
					logger.info("-------分解时长1," + min+ "（" +midNight+ " -"+result2+"）----------");
					min = getTimeCheckSpan(theTime, endTime, 0);
					timeMap.put(Constants.normalTimeCode, min);
					logger.info("-------分解时长2," + min+ "（" +result2+ " -"+dateTime+"）----------");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeMap;
	}

	public static  long calcDayHour(String startTimeStr, String endTimeStr) {
		String[]  strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(startTimeStr)).toString().split("-");
		String result = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + "23:59:59";
		strNow = new SimpleDateFormat("yyyy-MM-dd").format(parseToDate(endTimeStr)).toString().split("-");
		String result2 = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + "00:00:00";
		long startTime = 0;
		long endTime = 0;
		try {
			startTime = parseDateStrToTime(result);
			endTime = parseDateStrToTime(result2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long min = getTimeCheckSpan(startTime,endTime,0 );
		return  min;
	}


	/**
	 * 计算时间差，返回天数
	 * @param startTime 开始
	 * @param endTime 结束
	 * @return
	 */
	public static long getDayDiffFlag(long startTime, long endTime) {

		if (startTime > endTime) {
			long temp = startTime;
			startTime = endTime;
			endTime = temp;
		}

		String[]  strNow = new SimpleDateFormat("yyyy-MM-dd").format(startTime).toString().split("-");
		String result = strNow[0] + "-" + strNow[1] + "-" + strNow[2] + " " + "23:59:59";

		long between = 0;
		try {
			between = endTime - parseDateStrToTime(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long hour = (between / (60 * 60 * 1000));

		return  hour/24;
	}

	/**
	 * 计算时间差，返回时分秒
	 * @param startTime 开始
	 * @param endTime 结束
	 * @return
	 */
	public static long getTimeDiffFlag(long startTime, long endTime) {

		if (startTime > endTime) {
			long temp = startTime;
			startTime = endTime;
			endTime = temp;

			return  -1;
		}

		long between = endTime - startTime;
		long hour = (between / (60 * 60 * 1000));
		long min = ((between / (60 * 1000)) - hour * 60);
		long s = (between / 1000 - hour * 60 * 60 - min * 60);

		long minSpan = (between / (60 *  1000));

		return minSpan;
	}


	/**
	 * 计算时间差，判断是否已过抢占间隔
	 * @param startTime 开始
	 * @param endTime 结束
	 * @return
	 */
	public static long getTimeCheckSpan(long startTime, long endTime,long timespan) {
		if (startTime > endTime) {
			long temp = startTime;
			startTime = endTime;
			endTime = temp;
		}
		long between = endTime - startTime;
		long min = (between / (60 * 1000)) - timespan;
		return  min;
	}
	
	/**
	 * @param milliSeconds
	 *            毫秒数
	 * @return 格式化后的时间字符串 yyyy-MM-dd
	 */
	public static String getTimeStr_yyyy_MM_dd(Long milliSeconds) {

		return secondsToDateStr(milliSeconds, "yyyy-MM-dd");
	}
	
	// 检测一个时间格式是否为合法格式
	private static boolean isRightFormat(String formatStr) {
		boolean isRight = false;
		int j = formatArray.length;
		for (int i = 0; i < j; i++) {
			if (formatArray[i].equalsIgnoreCase(formatStr)) {
				isRight = true;
				break;
			}
		}
		return isRight;
	}

	/**
	 * @todo 将数值时间格式化为字符串
	 * @param milliSeconds
	 * @param formatStr
	 * @return
	 */
	public static String secondsToDateStr(Long milliSeconds, String formatStr) {

		if (milliSeconds == null)
			return "";
		if (isRightFormat(formatStr) == false) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

			if (milliSeconds.longValue() > 1) {
				GregorianCalendar gCalendar = new GregorianCalendar();
				gCalendar.setTimeInMillis(milliSeconds.longValue());
				return dateFormat.format(gCalendar.getTime());
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}

	}
	
    public static void main(String[] args) {
    	for (Map<String, Object> map : getDayTimeByMonth(2017,12)) {
    		System.out.println(map);
		}
		
	}
}
