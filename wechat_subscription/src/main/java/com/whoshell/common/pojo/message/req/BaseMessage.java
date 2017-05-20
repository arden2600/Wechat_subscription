package com.whoshell.common.pojo.message.req;

/**
 * BaseMessage : 微信公众号中封装消息内容的基类
 * 				(处理从微信用户发送过来的消息)
 * @author XianSky
 *
 */
public class BaseMessage {

	/*开发者微信号*/
	private String toUserName;
	
	/*公众号用户，发送账号:openId*/
	private String fromUserName;
	
	/*消息创建时间(整型)*/
	private long createTime;
	
	/*消息类型(text/image/location/link/audio..)*/
	private String msgType;
	
	/*消息标识id,64位整型*/
	private long msgId;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	
}
