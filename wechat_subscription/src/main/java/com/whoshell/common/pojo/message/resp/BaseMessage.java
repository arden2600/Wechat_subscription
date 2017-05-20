package com.whoshell.common.pojo.message.resp;

/**
 * BaseMessage : 微信公众号 -->微信用户响应消息基类
 * @author XianSky
 *
 */
public class BaseMessage {
	/*接收账号(接收者OpenId)*/
	private String ToUserName;
	/*开发者微信号*/
	private String FromUserName;
	/*消息类型(text/music/news..)*/
	private String MsgType;
	/*消息创建时间(整型)*/
	private long CreateTime;
	/*0x0001被标志，星标刚收到的信息*/
	private int FuncFlag;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public int getFuncFlag() {
		return FuncFlag;
	}
	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
	
	

}
