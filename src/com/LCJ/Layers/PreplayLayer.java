package com.LCJ.Layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.transitions.CCTransitionScene;
import org.cocos2d.transitions.CCZoomFlipYTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.Others.BsButton;

public class PreplayLayer extends CCLayer {
	public static CCScene scene() {
		CCScene s = CCScene.node();
		s.addChild(new PreplayLayer());
		return s;
	}
	
	protected PreplayLayer() {
		super();
		
		isTouchEnabled_ = true;
		
		CCSprite bgSp = CCSprite.sprite(G._getImg("load_bg"));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);

		BsButton btnPlay = BsButton.button(G._getImg("btn_play_nor"),
				G._getImg("btn_play_dow"), this, "actionPlay");
		if(G.hpdi == true)
			btnPlay.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(420)));
		else
			btnPlay.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(210)));
		addChild(btnPlay);

		BsButton btnInventory = BsButton.button(G._getImg("btn_invent_nor"),
				G._getImg("btn_invent_dow"), this, "actionInventory");
		if(G.hpdi == true)
			btnInventory.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(220)));
		else
			btnInventory.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(110)));
		addChild(btnInventory);

		BsButton btnSaveQuit = BsButton.button(G._getImg("btn_save_quit_nor"),
				G._getImg("btn_save_quit_dow"), this, "actionSaveQuit");
		if(G.hpdi == true)
			btnSaveQuit.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3), G._getY(220)));
		else
			btnSaveQuit.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3), G._getY(110)));
		addChild(btnSaveQuit);
		btnSaveQuit.setEnable(false);
		
		BsButton btnBack = BsButton.button(G._getImg("btn_back3_nor"),
				G._getImg("btn_back3_dow"), this, "actionBack");
		if(G.hpdi == true)
			btnBack.setPosition(CGPoint.ccp(G._getX(100), G._getY(50)));
		else
			btnBack.setPosition(CGPoint.ccp(G._getX(50), G._getY(25)));
		addChild(btnBack);
		
		if (AppSettings.level > 1)
			if(AppSettings.isStory)
				btnSaveQuit.setEnable(true);
	    else 
	    	btnSaveQuit.setEnable(false);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		System.gc();
		super.onExit();
	}

	public void actionPlay(Object sender) {
		CCDirector.sharedDirector().replaceScene(
				CCZoomFlipYTransition.transition(0.6f, GamePlayLayer.scene(),
						CCTransitionScene.tOrientation.kOrientationRightOver));
	}

	public void actionInventory(Object sender) {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PropertyLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void actionSaveQuit(Object sender) {
			AppSettings.SaveLevelInfo();
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, StartLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void actionBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, StartLayer.scene(),
						ccColor3B.ccBLACK));
	}
}