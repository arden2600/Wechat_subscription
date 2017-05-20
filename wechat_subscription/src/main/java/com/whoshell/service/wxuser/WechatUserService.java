package com.whoshell.service.wxuser;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoshell.constant.Constants;
import com.whoshell.pojo.User;
import com.whoshell.service.AccessTokenService;
import com.whoshell.util.HttpUtil;
import com.whoshell.util.StringUtil;
/**
 * WechatUserService : 公众平台中获取微信用户信息的业务类
 * @author XianSky
 *
 */
@Service("wechatUserService")
public class WechatUserService {

	private Log log = LogFactory.getLog(WechatUserService.class);
	
	@Autowired
	private HttpUtil httpUtil;
	
	/**
	 * getWechatUserInfo ： 根据openId获取微信用户信息。
	 * @param openId
	 * @return
	 */
	public User getWechatUserInfo(String openId) {
		//获取保存在内存中的全局接口调用access_token
		String accessToken = Constants.ACCESS_TOKEN;
		log.info("全局token>>" + accessToken);
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/user/info?")
		   .append("access_token=").append(accessToken)
		   .append("&openid=").append(openId).append("&lang=zh_CN");
		
		String content = "";
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			for (int i = 1; i <= 3; i++) {
				content = httpUtil.executeGet(url.toString());
				log.info("获取微信用户请求响应信息:>>" + content);
				Map map = objectMapper.readValue(content, Map.class);
				String errcode = StringUtil.getValueFromMap(map.get("errcode"));
				//access_token无效，强制刷新
				if("40001".equals(errcode)) {
					AccessTokenService.refresh();
					log.info("重新刷新access_token>>>" + Constants.ACCESS_TOKEN);
					continue;
				}
				Object mopenId = map.get("openid");
				Object nickName = map.get("nickname");
				log.info("第" + i + "次获取openId=" + openId + "的微信用户昵称：>>"+ nickName);
				if (openId.equals(mopenId) && nickName != null) {
					/*
					 * 获取微信用户基本信息成功，并将信息封装到平台用户对象中。
					 */
//					user = myPageDao.getUserByOpenId(openId);
					user = new User();
					if(user != null) {
						user.setNickname(String.valueOf(nickName));
//						user.setName(String.valueOf(nickName));
						user.setSex((Integer) map.get("sex"));
						user.setPictureUrl(String.valueOf(map.get("headimgurl")));
						user.setOpenId(String.valueOf(mopenId));
						user.setUnionId(String.valueOf(map.get("unionid")));
					}
					log.info("调用微信得到的用户信息:>>" + user.getNickname() + ",photo>>"+ user.getPictureUrl());
					return user;
				}
				log.info("第" + i + "次获取openId=" + openId + "的微信用户信息失败!!");
			}

		} catch (JsonParseException e) {
			log.error("获取微信基本用户信息时,json转换失败：>>", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("http请求执行错误:>>", e);
			e.printStackTrace();
		}
		return user == null ? new User() : user;
	}

}
