package com.whoshell.pojo.weixin;

/**
 * SubButton : 包含sub_button属性的二级菜单组
 * @author XianSky
 *
 */
public class SubButton extends Button{
	
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
	

}
