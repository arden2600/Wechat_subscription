package com.whoshell.action.qrcode;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoshell.constant.Constants;
import com.whoshell.service.AccessTokenService;
import com.whoshell.util.HttpClient;
import com.whoshell.util.StringUtil;

/**
 * QrCodeWithParam : 获取微信带参数二维码
 * @author XianSky
 *
 */
@Controller("qrCodeWithParam")
@RequestMapping("/getWxQrcode")
public class QrCodeWithParam {
	
	private Log log = LogFactory.getLog(QrCodeWithParam.class);
	

	/**
	 * getQrCode： 获取微信临时二维码url，将需要传递的场景值放入二维码中scene_id。
	 * 
	 * @param articleId
	 */
	@RequestMapping(value="/getQrCode.html", method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getQrCode(HttpServletRequest request) {
		
		//这里需求是对平台的每篇文章生成一个临时二维码，所以，场景值中传递的文章id
		int articleId = -1;
		try {
			articleId = Integer.parseInt(request.getParameter("articleId"));
		} catch (Exception e1) {
			return "";
		}
		String expire_seconds = "100000";
		String action_name = "QR_SCENE";
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+Constants.ACCESS_TOKEN;
		String responContent = "";
		String getCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
		
		Map<String, Object> formMap = new HashMap<String, Object>();
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		/*
		 * 根据官方带参数二维码文档api进行构造函数，临时二维码中的场景值只能是整型
		 */
		scene.put("scene_id", articleId);
		action_info.put("scene", scene);
		formMap.put("expire_seconds", expire_seconds);
		formMap.put("action_name", action_name);
		formMap.put("action_info", action_info);
		
		String formParam = JSON.toJSONString(formMap);
		log.info("获取带参数二维码ticket请求数据>>"+formParam);

		Map map = new HashMap();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			responContent = HttpClient.requestPost(url, formParam);
			log.info("发送请求后的响应数据>>"+responContent);
			map = objectMapper.readValue(responContent, Map.class);
			//若是access_token过期，刷新access_token
			String errcode = StringUtil.getValueFromMap(map.get("errcode"));
			for(int i = 0; i < 3 ; i++){
				if("40001".equals(errcode)) {
					AccessTokenService.refresh();
					log.info("重新刷新access_token>>>" + Constants.ACCESS_TOKEN);
					responContent = HttpClient.requestPost(url, formParam);
					map = objectMapper.readValue(responContent, Map.class);
					errcode = StringUtil.getValueFromMap(map.get("errcode"));
				}
			}
			String ticket = String.valueOf(map.get("ticket"));
			//需要对url进行编码
			ticket = URLEncoder.encode(ticket,"UTF-8");
			
			getCodeUrl += ticket;
			log.info("获取的带参数二维码图片url>>"+getCodeUrl);

		} catch (Exception e) {
			log.error("获取二维码失败，json转换失败：", e);
		}
		
		return getCodeUrl;
	}

}
