/**   
 * 类名：EncryptPropertyPlaceholderConfigurer
 *
 */
package com.whoshell.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.whoshell.util.NamePasswordEncryptUtil;



/** 
 * EncryptPropertyPlaceholderConfigurer: 属性配置文件中部分属性加密解析类
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-4-28 v1.0 15989 新建 
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	
	private Log log = LogFactory.getLog(EncryptPropertyPlaceholderConfigurer.class);
	
	/**
	 * 定义属性文件中加密过的属性
	 */
	private static String[] encryptPropertyNameStr = {"user","password"};
	
	/**
	 * convertProperty:属性值转换
	* @param propertyName  属性名称
	* @param propertyValue  属性值
	* @return 属性值
	 */
	protected String convertProperty(String propertyName,String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = NamePasswordEncryptUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}
	
	/**
	 * 
	 * isEncryptProp: 判断当前属性是否为加密属性
	 *
	 * @param propName	当前属性
	 * @return true/false
	 */
	public boolean isEncryptProp(String propName) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(propName)) {
			for (String tempPropName:encryptPropertyNameStr) {
				if (StringUtils.equals(propName, tempPropName)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
