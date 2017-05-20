/**   
 * 类名：MusicMessage
 *
 */
package com.whoshell.common.pojo.message.resp;

/** 
 * MusicMessage: 音乐消息
 * 
 * @version 1.0
 * @author 15989
 * @modified 2017-3-8 v1.0 15989 新建 
 */
public class MusicMessage extends BaseMessage {
	/** 音乐*/
	private Music music;

	/** 音乐*/
	public Music getMusic() {
		return music;
	}

	/** 音乐*/
	public void setMusic(Music music) {
		this.music = music;
	}
	
	
}
