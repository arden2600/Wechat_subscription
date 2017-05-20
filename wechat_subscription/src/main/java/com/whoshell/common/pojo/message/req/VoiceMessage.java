package com.whoshell.common.pojo.message.req;

/**
 * VoiceMessage : 公众号语音消息
 * @author XianSky
 *
 */
public class VoiceMessage  extends BaseMessage{
	//媒体Id
	private String MediaId;
	//媒体格式
	private String Format;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}

	
}
