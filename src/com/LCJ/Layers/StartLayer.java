package com.LCJ.Layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.ZombiDefense.ZombiDefense;
import com.LCJ.Others.BsButton;
import com.LCJ.Others.BsToggleButton;

public class StartLayer extends CCLayer {
	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new StartLayer());
		return scene;
	}
	CCLabel          	labelHelp;
	CCLayer       		layer_force;
	BsToggleButton 		mSoundBtn;
	CCSprite            boxMessage;

	public StartLayer() {
		super();
		CCSprite bgSp = CCSprite.sprite(G._getImg("title_bg"));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);
		
		CCSprite imgLog = CCSprite.sprite(G._getImg("logo"));
		G.setScale(imgLog);
		if(G.hpdi == true)
			imgLog.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(480)));
		else
			imgLog.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2), G._getY(240)));
		addChild(imgLog);

		BsButton btnSurvival = BsButton.button(G._getImg("btn_story_nor"),
				G._getImg("btn_story_dow"), this, "actionStory");
		if(G.hpdi == false)
			btnSurvival.setPosition(CGPoint.ccp(G._getX(90), G._getY(110)));
		else
			btnSurvival.setPosition(CGPoint.ccp(G._getX(180), G._getY(220)));
		addChild(btnSurvival);

		mSoundBtn = BsToggleButton.button(G._getImg("btn_sound_off"),
				G._getImg("btn_sound_on"), this, "actionSound");
		if(G.hpdi == false)
			mSoundBtn.setPosition(CGPoint.ccp(G._getX(430), G._getY(265)));
		else
			mSoundBtn.setPosition(CGPoint.ccp(G._getX(860), G._getY(530)));
		mSoundBtn.setState(AppSettings.sound);
		addChild(mSoundBtn);

		BsButton btnTutorial = BsButton.button(G._getImg("btn_surv_nor"),
				G._getImg("btn_surv_dow"), this, "actionSurvival");
		if(G.hpdi == false)
			btnTutorial.setPosition(CGPoint.ccp(G._getX(390), G._getY(110)));
		else
			btnTutorial.setPosition(CGPoint.ccp(G._getX(780), G._getY(220)));
		addChild(btnTutorial);

		BsButton btnMoreGames = BsButton.button(G._getImg("btn_more_nor"),
				G._getImg("btn_more_dow"), this, "actionMoreGames");
		if(G.hpdi == false)
			btnMoreGames.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(50)));
		else
			btnMoreGames.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(100)));
		addChild(btnMoreGames);

		BsButton btnBack = BsButton.button(G._getImg("btn_exit_nor"),
				G._getImg("btn_exit_dow"), this, "actionBack");
		if(G.hpdi == false)
			btnBack.setPosition(CGPoint.ccp(G._getX(63), G._getY(265)));
		else
			btnBack.setPosition(CGPoint.ccp(G._getX(126), G._getY(530)));
		addChild(btnBack);
		
		SharedPreferences setting = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE);
		Boolean sound_state = setting.getBoolean("savedSound", false);
		if(sound_state){
			AppSettings.playSound();
			AppSettings.sound = true;
			mSoundBtn.setState(true);
		}else{
			AppSettings.stopSound();
			AppSettings.sound = false;
			mSoundBtn.setState(false);
		}
		
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		labelHelp = null;
		layer_force = null;
		mSoundBtn = null;
		boxMessage = null;

		System.gc();
		super.onExit();
	}

	public void actionSurvival(Object sender) {
		AppSettings.isStory = false;
		AppSettings.isSurvival = true;
		AppSettings.isFirstPlay = false;
		
		AppSettings.level = 1;
		SharedPreferences setting = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE);
		AppSettings.stage  = setting.getInt("savedStage", 0);
		if(AppSettings.stage == 0)
			AppSettings.stage = 1;
	    AppSettings.money = 100/*tutorial ? 370 : 100*/;
	    AppSettings.gunState = 1;
	    AppSettings.wallState1 = 30;
	    AppSettings.wallState2 = 0;
	    AppSettings.wallState3 = 0;
	    AppSettings.wallState4 = 0;
	    AppSettings.boxState = 0;	
	    AppSettings.isFirstBuyGun = false;
	
	    CCDirector.sharedDirector().replaceScene(
			CCFadeTransition.transition(0.6f, LevelLayer.scene(),
					ccColor3B.ccBLACK));
//		AppSettings.InitLevelInfo();
//		CCDirector.sharedDirector().replaceScene(
//				CCFadeTransition.transition(0.6f, SurvivalLayer.scene(),
//						ccColor3B.ccBLACK));
	}

	public void actionStory(Object sender) {
		AppSettings.isStory = true;
		AppSettings.isSurvival = false;
		
		AppSettings.InitGameInfo();
		AppSettings.isFirstPlay = true;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, SurvivalLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void actionMoreGames(Object sender) {
		ZombiDefense dd = (ZombiDefense) CCDirector.sharedDirector().getActivity();
		dd.Help.sendEmptyMessage(ZombiDefense.MORE_GAMES);
		
	}

	public void actionBack(Object sender) {
		AppSettings.stopSound();
		CCDirector.sharedDirector().end();
		Process.killProcess(Process.myPid());
	}

	public void actionSound(Object sender) {
		AppSettings.sound = !AppSettings.sound;
		if (AppSettings.sound)
			AppSettings.playSound();
		else
			AppSettings.stopSound();
		mSoundBtn.setState(AppSettings.sound);
		
		SharedPreferences.Editor editor = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE).edit();
		editor.putBoolean("savedSound", AppSettings.sound);
		editor.commit();
	}
}
