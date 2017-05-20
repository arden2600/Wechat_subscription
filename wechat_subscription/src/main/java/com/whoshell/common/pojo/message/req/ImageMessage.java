package com.whoshell.common.pojo.message.req;

/**
 * ImageMessage: 微信公众号中的图片消息
 * @author XianSky
 *
 */
public class ImageMessage  extends BaseMessage{
	
	//图片链接(首字母大写)
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	

}
