package com.whoshell.pojo.weixin;
/**
 * CommonButton : 微信公众号菜单中的普通按钮（子按钮）
 * @author XianSky
 *
 */
public class ViewButton extends Button{

	/*子按钮类型*/
	private String type;
	
	/*子按钮对应的url值*/
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
}
