package com.whoshell.pojo.weixin;
/**
 * CommonButton : 微信公众号菜单中的普通按钮（子按钮）
 * @author XianSky
 *
 */
public class ClickButton extends Button{

	/*子按钮类型*/
	private String type;
	
	/*子按钮对应的key值*/
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
