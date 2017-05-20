package com.whoshell.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ResponseUtil : response相应工具类。
 * @author _Fan
 *
 */
public class ResponseUtil {

	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * ajax请求相应输出处理。
	 * @param content
	 * @param response
	 * @param type
	 * @return
	 */
	public static String ajax(String content,HttpServletResponse response, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
