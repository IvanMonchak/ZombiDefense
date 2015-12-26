/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package com.LCJ.Layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.content.SharedPreferences;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.Others.BsButton;

public class SurvivalLayer extends CCLayer {
	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new SurvivalLayer());
		return scene;
	}
	
	BsButton btnLoadGame;
	
	public SurvivalLayer() {
		super();
		CCSprite bgSp = CCSprite.sprite(G._getImg("title_bg_new"));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);
		
		CCSprite imgLog = CCSprite.sprite(G._getImg("logo"));
		G.setScale(imgLog);
		if(G.hpdi == false)
			imgLog.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(240)));
		else
			imgLog.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(480)));
		addChild(imgLog);
		
		BsButton btnNewGame = BsButton.button(G._getImg("btn_new_nor"),
				G._getImg("btn_new_dow"), this, "actionNewGame");
		if(G.hpdi == false)
			btnNewGame.setPosition(CGPoint.ccp(G._getX(130), G._getY(110)));
		else
			btnNewGame.setPosition(CGPoint.ccp(G._getX(260), G._getY(220)));
		addChild(btnNewGame);

		btnLoadGame = BsButton.button(G._getImg("btn_load_nor"),
				G._getImg("btn_load_dow"), this, "actionLoadGame");
		if(G.hpdi == true)
			btnLoadGame.setPosition(CGPoint.ccp(G._getX(700), G._getY(220)));
		else
			btnLoadGame.setPosition(CGPoint.ccp(G._getX(350), G._getY(110)));
		addChild(btnLoadGame);
		btnLoadGame.setEnable(false);

		BsButton btnBack = BsButton.button(G._getImg("btn_back3_nor"),
				G._getImg("btn_back3_dow"), this, "actionBack");
		if(G.hpdi == true)
			btnBack.setPosition(CGPoint.ccp(G._getX(100), G._getY(50)));
		else
			btnBack.setPosition(CGPoint.ccp(G._getX(50), G._getY(25)));
		addChild(btnBack);
//		if(AppSettings.isSurvival == false){
			if (AppSettings.savedLevel == 0)
				btnLoadGame.setEnable(false);
	        else 
	        	if(AppSettings.isStory)
	        		btnLoadGame.setEnable(true);	
//		}
		
	}

	public void actionNewGame(Object sender) {
		if(AppSettings.isStory){
			AppSettings.InitLevelInfo();	
		}else{
			AppSettings.level = 1;
			SharedPreferences setting = 
				CCDirector.sharedDirector().getActivity().
				getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE);
			AppSettings.stage  = setting.getInt("savedStage", 0);
		    AppSettings.money = 100/*tutorial ? 370 : 100*/;
		    AppSettings.gunState = 1;
		    AppSettings.wallState1 = 30;
		    AppSettings.wallState2 = 0;
		    AppSettings.wallState3 = 0;
		    AppSettings.wallState4 = 0;
		    AppSettings.boxState = 0;	
		    AppSettings.isFirstBuyGun = false;
		}
		
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, LevelLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void actionLoadGame(Object sender) {
		AppSettings.LoadLevelInfo();
		AppSettings.isFirstPlay = false;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void actionBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, StartLayer.scene(),
						ccColor3B.ccBLACK));
		AppSettings.isSurvival = false;
		AppSettings.isStory = false;
	}
}
