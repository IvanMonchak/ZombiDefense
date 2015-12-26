package com.LCJ.Others;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.menus.CCMenuItemToggle;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.LCJ.ZombiDefense.G;

public class BsToggleButton extends CCMenu
{
	public BsToggleButton(CCMenuItemToggle menuItem) {
		super(menuItem);
	}

	public static BsToggleButton button(
		String imageOn,String imageOff,
		CCNode target,String selector)
	{
		CCSprite onSprite = CCSprite.sprite(imageOn);
		CCSprite offSprite = CCSprite.sprite(imageOff);
		
		CCSprite copyOnSprite = CCSprite.sprite(imageOn);
		CCSprite copyOffSprite = CCSprite.sprite(imageOff);	
		
		onSprite.setAnchorPoint(CGPoint.ccp(0,0));
		offSprite.setAnchorPoint(CGPoint.ccp(0,0));
		
		CCMenuItemSprite itemOn = CCMenuItemSprite.item(
			onSprite,offSprite,null,null);
		G.setScale(itemOn);
		CCMenuItemSprite itemOff = CCMenuItemSprite.item(
			copyOffSprite,copyOnSprite,null,null);
		G.setScale(itemOff);
		CCMenuItemToggle menuItem = CCMenuItemToggle.item(
			target,selector,itemOn,itemOff);
		return new BsToggleButton(menuItem);
	}

	public void setState(boolean bState)
	{
		CCMenuItemToggle item = (CCMenuItemToggle)children_.get(0);
		if (item.getVisible() && item.isEnabled())
		{
			item.setSelectedIndex(bState? 1 : 0);
		}
	}
}