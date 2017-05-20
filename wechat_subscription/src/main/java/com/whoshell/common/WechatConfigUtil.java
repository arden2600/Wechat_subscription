package com.whoshell.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoshell.common.service.CoreService;
import com.whoshell.wechatplatform.AesException;
import com.whoshell.wechatplatform.SHA1;

/**
 * WechatConfigUtil : 用于校验微信公众号服务器配置和消息信息的工具
 * @author XianSky
 *
 */

@Controller("WechatConfigUtil")
@Qualifier("WechatConfigUtil")
@RequestMapping("WechatConfigUtil")
public class WechatConfigUtil {

	/*公众号后台配置中填写的token字符串*/
	private final static String token = "whoshell";
	
	private Log log = LogFactory.getLog(WechatConfigUtil.class);
	
	@Autowired
	private CoreService coreService;
	
	/**
	 * checkSignature : 微信服务器合法身份校验
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value="/WCConfig",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkSignature(@RequestParam(value="signature",required=true) String signature,
									 @RequestParam(value="timestamp",required=true) String timestamp,
									 @RequestParam(value="nonce",    required=true) String nonce,
									 @RequestParam(value="echostr",  required=false)String echostr) {
		log.info("接收微信服务器参数信息: signatrue >> " + signature + ",timestamp >>" + timestamp + ",nonce>>" + nonce + ",echostr>>" + echostr);
		
		String tempStr = "-1";
		if(StringUtils.isNotEmpty(signature) && StringUtils.isNotEmpty(timestamp)
				&& StringUtils.isNotEmpty(nonce) && StringUtils.isNotEmpty(echostr)) {
			try {
				tempStr = SHA1.getSHA1(timestamp, nonce, token, "");
				if(StringUtils.isNotEmpty(tempStr) && StringUtils.equals(signature, tempStr)) {
					return echostr;
				}
			} catch (AesException e) {
				log.error("微信配置校验失败,",e);
			}
		}
		return tempStr;
	}
	
	/**
	 * dealWithMessage ： 处理每条公众号与微信用户的信息交互业务
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/WCConfig",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String dealWithMessage(HttpServletRequest request) throws UnsupportedEncodingException {
		//将请求的编码设置为UTF-8(防止中文乱码)
		request.setCharacterEncoding("UTF-8");
		//调用核心业务类接收消息、处理消息
		String respMessage = coreService.processRequest(request);
		return respMessage;
	}
}
