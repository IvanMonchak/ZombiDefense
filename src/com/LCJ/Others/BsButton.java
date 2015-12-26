package com.LCJ.Others;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCNode;

import com.LCJ.ZombiDefense.G;

public class BsButton extends CCMenu
{
	public BsButton(CCMenuItem menuItem) {
		super(menuItem);
	}

	public static  BsButton button(
		String normalImage,String selectedImage,
		CCNode target,String sel)
	{
		CCMenuItemImage menuItem = CCMenuItemImage.item(
			normalImage,selectedImage, target, sel);
		G.setScale(menuItem);
		
		return new BsButton(menuItem);
	}
	
	public static  BsButton button(
		String normalImage,String selectedImage,
		CCNode target,String sel, boolean bSmall)
	{
		CCMenuItemImage menuItem = CCMenuItemImage.item(
			normalImage,selectedImage, target, sel);
		G.setScale(menuItem, bSmall);
		
		return new BsButton(menuItem);
	}


	public void setEnable(boolean bEnable)
	{
		isTouchEnabled_ = bEnable;
		setOpacity( bEnable ? 255 : 80 );
	}
}