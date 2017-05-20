/**   
 * 类名：TextMessage
 *
 */
package com.whoshell.common.pojo.message.resp;

/** 
 * TextMessage: 文本消息
 * 
 * @version 1.0
 * @author 15989
 * @modified 2017-3-8 v1.0 15989 新建 
 */
public class TextMessage extends BaseMessage {
	/** 回复的消息内容*/
	private String Content;
	/** 回复的消息内容*/
	public String getContent() {
		return Content;
	}
	/** 回复的消息内容*/
	public void setContent(String content) {
		this.Content = content;
	}
	
	
}
