/**   
 * 类名：StringUtil
 *
 */
package com.whoshell.util;

import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * StringUtil: 字符串工具类
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-8-9 v1.0 15989 新建
 */
public class StringUtil {

	private static Log log = LogFactory.getLog(StringUtil.class);

	/**
	 * 
	 * splitStr: 分割字符串
	 *
	 * @param str 待分割字符串 @param sSplitSign 分割符号 @return @throws
	 */
	public static String[] splitStr(String str, String sSplitSign) {
		String[] oSplitSignArray = new String[] {};
		if (str != null && sSplitSign != null && StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(sSplitSign)) {
			oSplitSignArray = str.split(sSplitSign);
		} else {
			log.error(">>>数据有误，错误数据str为：" + str + "\tsSplitSign为：" + sSplitSign);
		}
		return oSplitSignArray;
	}

	/**
	 * getCharacterPosition : 查询字符串中指定符号指定次数出现的位置。
	 * 
	 * @param string
	 * @param sign
	 * @param n
	 * @return
	 */
	public static int getCharacterPosition(String string, String sign, int n) {

		Matcher slashMatcher = Pattern.compile(sign).matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"/"符号第n次出现的位置
			if (mIdx == n) {
				break;
			}
		}
		return slashMatcher.start();
	}

	/**
	 * validStr : 校验字符串是否为空且是否是"null"字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validStr(String str) {
		return (StringUtils.isNotEmpty(str) && !"null".equals(str)) ? true : false;
	}

	/**
	 * getValueFromMap : 根据key从map中获取数据时候，若是取出value为空,返回null。
	 * 
	 * @param value
	 * @return
	 */
	public static String getValueFromMap(Object value) {
		if (null == value) {
			return null;
		}
		return String.valueOf(value);
	}

	/**
	 * byteToHex : 将字节数组转成十六进制字符串
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;

	}
}
