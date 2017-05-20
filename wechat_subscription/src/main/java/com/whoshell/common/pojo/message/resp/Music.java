/**   
 * 类名：Music
 *
 */
package com.whoshell.common.pojo.message.resp;

/** 
 * Music: 音乐model
 * 
 * @version 1.0
 * @author 15989
 * @modified 2017-3-8 v1.0 15989 新建 
 */
public class Music {
	/** 音乐名称*/
	private String title;
	/** 音乐描述*/
	private String description;
	/** 音乐链接*/
	private String musicUrl;
	/** 高质量音乐链接,wifi环境优先使用该链接播放音乐*/
	private String hqMusicUrl;
	
	/** 音乐名称*/
	public String getTitle() {
		return title;
	}
	/** 音乐名称*/
	public void setTitle(String title) {
		this.title = title;
	}
	/** 音乐描述*/
	public String getDescription() {
		return description;
	}	
	/** 音乐描述*/
	public void setDescription(String description) {
		this.description = description;
	}
	/** 音乐链接*/
	public String getMusicUrl() {
		return musicUrl;
	}
	/** 音乐链接*/
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	/** 高质量音乐链接,wifi环境优先使用该链接播放音乐*/
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	/** 高质量音乐链接,wifi环境优先使用该链接播放音乐*/
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	
	
}
