package com.whoshell.pojo.weixin;

/**
 * ComplexButton : 复杂按钮，也就是父类按钮，内部可以包含多个子按钮
 * @author XianSky
 *
 */
public class ComplexButton extends Button{

	private Button[] sub_buttons;

	public Button[] getSub_buttons() {
		return sub_buttons;
	}

	public void setSub_buttons(Button[] sub_buttons) {
		this.sub_buttons = sub_buttons;
	}


    	
	
}
