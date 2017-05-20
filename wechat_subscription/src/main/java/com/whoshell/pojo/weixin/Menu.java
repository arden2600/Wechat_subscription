package com.whoshell.pojo.weixin;

/**
 * Menu : 微信公众号的整个菜单：菜单对象数量以及内部按钮数量的限制参考官网
 * @author XianSky
 *
 */
public class Menu {

	//菜单中的按钮选项
	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}

}
