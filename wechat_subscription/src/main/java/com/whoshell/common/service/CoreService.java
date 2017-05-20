package com.whoshell.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.whoshell.common.MessageUtil;
import com.whoshell.common.pojo.message.resp.Article;
import com.whoshell.common.pojo.message.resp.NewsMessage;
import com.whoshell.common.pojo.message.resp.TextMessage;
import com.whoshell.util.StringUtil;



/**
 * CoreService : 微信公众号信息处理逻辑类
 * 当用户向公众帐号发消息时，微信服务器会将消息通过POST方式提交给我们在接口配置信息中填写的URL，
 * 而我们就需要在URL所指向的请求处理类WechatConfigUtil的doPost方法中接收消息、处理消息和响应消息。
 * @author XianSky
 *
 */
@Service("coreService")
public class CoreService {
	
	/*
	 * 未关注微信用户扫码时，对应事件KEY值前缀
	 */
	private static final String UNSUBSCRIBE_SCAN_PREFIX = "qrscene_";
	
	private Log log = LogFactory.getLog(CoreService.class);

	/**
	 * 
	 * processRequest: 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		// 默认返回的文本消息内容
		String respContent = "请求处理异常，请稍后尝试！";
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方账号(open_id)
			String fromUserName = requestMap.get("FromUserName");
			// 公众账号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			log.info(">>>fromUserName：" + fromUserName + "\ttoUserName: "
					+ toUserName + "\tmsgType: " + msgType);
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

				String content = requestMap.get("Content");
				log.info("用户输入内容---" + content);

				if ("公司介绍".equals(content)) {
					Article article = new Article();
					article.setTitle("广州融声信息科技有限公司介绍");
					article.setDescription("专注硬件开发，音频卡研发几十年的互联网高新企业。");
					article.setPicUrl("http://www.excecard.com/images/tiyan_1.jpg");
					article.setUrl("http://www.excecard.com");
					
					return produceSingleNewMessage(fromUserName, toUserName, article);
				}
				 respContent = "想看更多分享，点击菜单进入圈子吧~~";
			}
			// 图片消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息";
			}
			// 地理位置消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息";
			}
			// 音频消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息";
			}
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event").toLowerCase().trim();
				// 事件KEY值
				String eventKey = requestMap.get("EventKey").toLowerCase().trim();
				this.log.info("事件类型>>" + eventType + ",事件eventKy值>>" + eventKey);
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					// 是否是二维码关注
					if (StringUtil.validStr(eventKey)) {
						log.info("----------二维码关注---------" + eventKey);
						respContent = "欢迎关注享必得，想看更多分享，点击菜单进入圈子吧~~<a href=\"http://www.w3school.com.cn\">热点圈子</a>";
						
						if(eventKey.startsWith(UNSUBSCRIBE_SCAN_PREFIX)) {
							/*
							 * 二维码扫码文章推送,事件KEY值，qrscene_为前缀，后面为二维码的参数值
							 */
							String articleId = eventKey.substring(UNSUBSCRIBE_SCAN_PREFIX.length());
							log.info("未关注用户扫码获取key值,文章id=" + articleId);
							
							/*
							 * 推送扫码所在文章url图文信息
							 */
							Article article = new Article();
							article.setTitle("");
							article.setDescription("");
							article.setPicUrl("");
							article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9e49096dbba0159e" +
									"&redirect_uri=https%3A%2F%2Fwx.excecard.com%2FxiangbideWXBG%2FdetailPageAction%2Fdetail.html" +
									"&response_type=code&scope=snsapi_base&state="+""+"#wechat_redirect");

							return produceSingleNewMessage(fromUserName, toUserName, article);
						}

						
					} else {
						log.info("----------搜索关注------------" + eventKey);
						respContent = "欢迎关注享必得，想看更多分享，点击菜单进入圈子吧。" +
								"<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9e49096dbba0159e" +
								"&redirect_uri=https%3A%2F%2Fwx.excecard.com%2FxiangbideWXBG%2FmainPageAction%2FmainPage.html" +
								"&response_type=code&scope=snsapi_base&state=1#wechat_redirect\">热点圈子</a>";
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					log.info("------------取消订阅--------");
					// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单单击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					
					if (eventKey.equals("v1001_about_us")) {
//						respContent = "广州融声信息科技有限公司：专注音频卡研发30年的技术团队。想看更多详情，请回复：公司介绍";

						Article article = new Article();
						article.setTitle("广州融声信息科技有限公司介绍");
						article.setDescription("专注硬件开发，音频卡研发几十年的互联网高新企业。");
						article.setPicUrl("http://www.excecard.com/images/tiyan_1.jpg");
						article.setUrl("http://www.excecard.com");
						
						return produceSingleNewMessage(fromUserName, toUserName, article);
					}

					
				}
				//接收事件推送 扫码（用户已关注）
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					/*
					 * 推送扫码所在文章url图文信息
					 */
					Article article = new Article();
					article.setTitle("");
					article.setDescription("");
					article.setPicUrl("");
					article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9e49096dbba0159e" +
							"&redirect_uri=https%3A%2F%2Fwx.excecard.com%2FxiangbideWXBG%2FdetailPageAction%2Fdetail.html" +
							"&response_type=code&scope=snsapi_base&state="+""+"#wechat_redirect");
					//推送单篇新闻图片消息
					respMessage = produceSingleNewMessage(fromUserName,toUserName,article);
					return respMessage;
					
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXML(textMessage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return respMessage;
	}
	
	/**
	 * produceSingleNewMessage : 在公众号中发布单篇文章
	 * @param fromUserName
	 * @param toUserName
	 * @param article
	 * @return
	 */
	public String produceSingleNewMessage(String fromUserName, String toUserName , Article article) {
		String respMessage = "";
		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		
		List<Article> articleList = new ArrayList<Article>();
		articleList.add(article);
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		respMessage = MessageUtil.newsMessageToXML(newsMessage);
		articleList = null;
		return respMessage;
	}

	
}
