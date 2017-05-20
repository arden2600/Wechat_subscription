/**   
 * 类名：DateUtil
 *
 */
package com.whoshell.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
 * DateUtil: 时间工具类
 * 
 * @version 1.0
 * @author zengqaowang
 * @modified 2015-12-11 v1.0 zengqaowang 新建 
 */
public class DateUtil {
	
	/**
	 * 
	 * getDateTimeStr: 得到 yyyy-MM-dd HH:mm:ss 格式当前时间
	 *
	 * @return  yyyy-MM-dd HH:mm:ss 格式当前时间
	 */
	public static String getDateTimeStr() {
		Date dNowTime = Calendar.getInstance().getTime();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdformat.format(dNowTime);
	}
	
	/**
	 * 
	 * getYYYYMMDDStr: 得到 yyyyMMdd格式当前时间
	 *
	 * @return  yyyyMMdd 格式当前时间
	 */
	public static String getYYYYMMDDStr() {
		Date dNowTime = Calendar.getInstance().getTime();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		return sdformat.format(dNowTime);
	}
}
