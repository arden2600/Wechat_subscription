package com.whoshell.common.pojo.message.req;

/**
 * TextMessage : 微信公众号文本消息
 * @author XianSky
 *
 */
public class TextMessage extends BaseMessage{
	
	//消息内容(注意首字母大写)
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
