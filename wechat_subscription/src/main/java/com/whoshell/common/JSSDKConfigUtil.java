package com.whoshell.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.whoshell.constant.Constants;
import com.whoshell.util.ConfigService;
import com.whoshell.util.HttpClient;
import com.whoshell.util.StringUtil;

/**
 * JSSDKConfigUtil : 微信公众号页面开发调用JSSDK校验类
 * @author XianSky
 *
 */
@Service("JSSDKConfigUtil")
public class JSSDKConfigUtil {

	private static Log log = LogFactory.getLog(JSSDKConfigUtil.class);
	
	/** 
	  * 方法名：httpRequest</br> 
	  * 详述：发送http请求</br> 
	  * @param requestUrl 
	  * @param requestMethod 
	  * @param outputStr 
	  * @return 说明返回值含义 
	  * @throws 说明发生此异常的条件 
	*/  
	public static Map<String, Object> getWxConfig(HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String appId = Constants.APPID; // 必填，公众号的唯一标识
		String secret = Constants.APPSECRET;

		String doname = ConfigService.getValue("DONAME", "doname");
		String context = doname + request.getContextPath();
		String handlerMappingUrl = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
		
		StringBuffer requestUrl = request.getRequestURL();
		String queryString = request.getQueryString();
		log.info("查询参数>>:" + queryString);
        String fullRequestUrl = "";
        
        /*
         * 若是不通过springmvc进行请求映射的url就不进行处理 
         */
        if(handlerMappingUrl == null || "null".equals(handlerMappingUrl)) {
        	fullRequestUrl = requestUrl.toString();
        } else {	//将springmvc映射路径替换/WEB-INF/下的jsp绝对路径
        	fullRequestUrl = context + handlerMappingUrl;
        }
        
        if(queryString != null && !"null".equals(queryString)) {
   		   fullRequestUrl = fullRequestUrl +"?"+queryString;
   	   }
        
	    log.info("访问的url>>" + fullRequestUrl);
		String access_token = "";
		String jsapi_ticket = "";
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
		String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + secret;

		JSONObject json = HttpClient.httpRequestReturnByJson(url, "GET", null);
		
		if (json != null) {
			// 要注意，access_token需要缓存
			access_token = json.getString("access_token");
//			access_token = Constants.ACCESS_TOKEN;

			url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ access_token + "&type=jsapi";
			json = HttpClient.httpRequestReturnByJson(url, "GET", null);
			if (json != null) {
				jsapi_ticket = json.getString("ticket");
				log.info("得到的jsapi ticket:>>" + jsapi_ticket);
			}
		}
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + timestamp + "&url=" + fullRequestUrl;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sign.getBytes("UTF-8"));
			signature = StringUtil.byteToHex(crypt.digest());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("appId", appId);
		ret.put("timestamp", timestamp);
		ret.put("nonceStr", nonceStr);
		ret.put("signature", signature);
		return ret;
	}
	
  

}
