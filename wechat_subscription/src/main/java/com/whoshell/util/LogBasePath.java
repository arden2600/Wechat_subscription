/**   
 * 类名：LogBasePath
 *
 */
package com.whoshell.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;

/** 
 * LogBasePath: 日志基础路径类
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-12-7 v1.0 15989 新建 
 */
public class LogBasePath implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.clearProperty("logBasePathXBD");
		System.clearProperty("classesPathXBD");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {		
		System.clearProperty("logBasePathXBD");
		System.clearProperty("classesPathXBD");
		String sTempPath = System.getProperty("user.dir");
		String sTemppath2 = "";
		//含有才处理
		if (StringUtils.contains(sTempPath, "bin")) {
			sTemppath2 = sTempPath.substring(0, sTempPath.indexOf("bin"));
		} else {
			sTemppath2 = sTempPath;
		}
		String sProjectName = servletContextEvent.getServletContext().getContextPath();
		//设置classes路径环境
		StringBuffer sClassesPath = new StringBuffer(50);
		sClassesPath.append(sTemppath2.replace("\\", "/")).append("webapps").append(sProjectName)
			.append("/WEB-INF/classes/");
		System.setProperty("classesPathXBD", sClassesPath.toString());
		
		//设置log4j路径		
		StringBuffer sLogPath = new StringBuffer(50);
		sLogPath.append(sTemppath2.replace("\\", "/")).append("webapps").append(sProjectName).append("/log/");
		System.setProperty("logBasePathXBD", sLogPath.toString());
		sLogPath.delete(0, sLogPath.length());
		
		//加载log4j
		System.out.println(">>>sClassesPath: " + sClassesPath.toString());
		StringBuffer sLog4jPath = new StringBuffer();
		sLog4jPath.append(sClassesPath.toString()).append("log4j.xml");
		DOMConfigurator.configure(sLog4jPath.toString());
		sLog4jPath.delete(0, sLog4jPath.length());
		sClassesPath.delete(0, sClassesPath.length());

	}

}
