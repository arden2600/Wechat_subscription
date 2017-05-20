package com.whoshell.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.whoshell.constant.Constants;
import com.whoshell.pojo.weixin.Button;
import com.whoshell.pojo.weixin.ClickButton;
import com.whoshell.pojo.weixin.ComplexButton;
import com.whoshell.pojo.weixin.Menu;
import com.whoshell.pojo.weixin.SubButton;
import com.whoshell.pojo.weixin.ViewButton;
import com.whoshell.util.HttpClient;

/**
 * WechatMenuUtil : 微信公众号按钮处理(增加，删除)工具类
 * @author XianSky
 *
 */
@Controller("wechatMenuUtil")
@RequestMapping("/menu")
public class WechatMenuUtil {

	private Log log = LogFactory.getLog(WechatMenuUtil.class);
	
	/**
	 * createSpecifiedMenu : 创建微信公众号的菜单。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/create.html",method=RequestMethod.GET)
	@ResponseBody
	public String createSpecifiedMenu(HttpServletRequest request, HttpServletResponse response) {
//		String json = "{
//				"button":[
//     {	
//          "type":"click",
//          "name":"关于我们",
//          "key":"V1001_ABOUT_US"
//      },
//      {
//           "name":"菜单",
//           "sub_button":[
//           {	
//               "type":"view",
//               "name":"圈子",
//               "url":"https://wx.example.com/wechatsubscription/MyJsp.jsp"
//            }]
//       }]
// }";
		//一级
		ComplexButton parent = new ComplexButton();
		//二级
		SubButton subButton = new SubButton();
		ClickButton us = new ClickButton();
		us.setName("我们");
		us.setType("click");
		us.setKey("V1001_ABOUT_US");
		
		ViewButton quanzi = new ViewButton();
		quanzi.setName("圈子");
		quanzi.setType("view");
		quanzi.setUrl("https://wx.example.com/wechatsubscription/MyJsp.jsp");
		
		subButton.setName("菜单");
		subButton.setSub_button(new Button[]{quanzi});
		
		parent.setName("button");
		parent.setSub_buttons(new Button[]{us,subButton});
		
		Menu menu = new Menu();
		menu.setButtons(new Button[]{parent});
       
		String submitStr = JSONObject.toJSONString(menu);
		log.info("提交的自定义菜单json字符串:>> " + submitStr);
		JSONObject menuJson = JSONObject.parseObject(submitStr);
		
		StringBuffer requestUrl = new StringBuffer();
		String access_token = Constants.ACCESS_TOKEN;
		requestUrl.append("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=").append(access_token);
		String retStr = HttpClient.postJsonDataRequest(requestUrl.toString(), menuJson);
		log.info("提交菜单后，微信响应数据:>>" + retStr);
		return retStr;
	}
	
	
	/**
	 * deleteMenu : 删除微信公众号自定义菜单。
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete.html",method=RequestMethod.GET)
	@ResponseBody
	public String deleteMenu(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer requestUrl = new StringBuffer();
		String access_token = Constants.ACCESS_TOKEN;
		requestUrl.append("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=").append(access_token);
		String retStr = "";
		try {
			retStr = HttpClient.requestGet(requestUrl.toString());
		} catch (IOException e) {
			log.error("删除微信公众号菜单请求失败" ,e);
			e.printStackTrace();
		}
		return retStr;
	}
}
