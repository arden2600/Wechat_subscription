/**   
 * 类名：HttpGet
 *
 */
package com.whoshell.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

/**
 * HttpGet: TODO请填写类描述
 * 
 * @version 1.0
 * @author 15989
 * @modified 2016-8-4 v1.0 15989 新建
 */
@Component("httpUtil")
public class HttpUtil {
	private Log log = LogFactory.getLog(HttpUtil.class);
	
	/**
	 * 
	 * executeGet: get方法
	 *
	 * @param url  url地址
	 * @return 结果数据
	 * @throws Exception 
	 */
	public String executeGet(String url) throws Exception {
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			HttpClient client = new DefaultHttpClient();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					log.error(e);
				}
			}
			return content;
		}
	}
	
	
}
