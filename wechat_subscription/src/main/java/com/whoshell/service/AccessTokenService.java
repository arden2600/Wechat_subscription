package com.whoshell.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoshell.constant.Constants;
import com.whoshell.util.HttpUtil;

/**
 * AccessTokenService : 定时刷新获取微信接口调用的token。
 * @author _Fan
 *
 */
@Service("accessTokenService")
public class AccessTokenService {

	private Log log = LogFactory.getLog(AccessTokenService.class);
	
	@Autowired
	private HttpUtil httpUtil;
	
	/**
	 * execute : 定时器调用的执行方法。
	 * 调用获取access_toke微信接口API
	 * 详情：
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN
	 */
	public void execute() {
		getAccessToken();
	}
	
	/**
	 * getAccessToken : 实际调用微信API接口的方法 。
	 * @throws Exception 
	 */
	private void getAccessToken() {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential")
		   .append("&appid=").append(Constants.APPID)
		   .append("&secret=").append(Constants.APPSECRET);
		log.info("获取全局accesss_token的请求:>>" + url.toString());
		try {
			String content;
			ObjectMapper objectMapper = new ObjectMapper();
			/*
			 * 发送请求获取access_token，最多发送3次请求进行获取。
			 */
			for(int i = 1; i <= 3; i++) {
				if(httpUtil == null) {
					httpUtil = new HttpUtil();
				}
				content = httpUtil.executeGet(url.toString());
				try {
					Map map = objectMapper.readValue(content, Map.class);
					Object at = map.get("access_token");
					log.info("第" + i + "次定时器获取全局access_token:>>" + at);
					if(null != at) {
						//刷新内存中的全局ACCESS_TOKEN值。
						Constants.ACCESS_TOKEN = String.valueOf(at);
						log.info("全局access_token刷新成功!!");
						break;
					}
					log.info("全局access_token刷新失败!!");
				} catch (Exception e) {
					log.error("获取全局access_token时,json转换失败：" + e.getMessage());
					break;
				}
			}
	
		} catch (Exception e) {
			log.error("获取全局access_token失败：" + e.getMessage());
		}
		
	}
	
	/**
	 * refresh: 提供一个入口，进行强制手动刷新token。
	 */
	public static void refresh() {
		new AccessTokenService().getAccessToken();
	}
}

