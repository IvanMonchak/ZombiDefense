/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package com.LCJ.Layers;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.Others.BsButton;

public class LevelLayer extends CCLayer {
	public static CCScene scene() {
		CCScene s = CCScene.node();
		s.addChild(new LevelLayer());
		return s;
	}
	public BsButton btnField;
	public BsButton btnRoom;
	public BsButton btnWaveHouse;
	public BsButton btnFactory;
	CCSprite 			m_spStory;
	
	protected LevelLayer() {
		super();
		
		CCSprite bgSp = CCSprite.sprite(G._getImg("level_bg_1"));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		
		BsButton btnField = BsButton.button(G._getImg("level_1"),
				G._getImg("level_1"), this, "actionLevel1");
		if(G.hpdi == true)
			btnField.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(168)));
		else
			btnField.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(84)));
		BsButton btnRoom = BsButton.button(G._getImg("level_2"),
				G._getImg("level_2"), this, "actionLevel2");
		if(G.hpdi == true)
			btnRoom.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(424)));
		else
			btnRoom.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4), G._getY(212)));		
		BsButton btnWaveHouse = BsButton.button(G._getImg("level_3"),
				G._getImg("level_3"), this, "actionLevel3");
		if(G.hpdi == true)
			btnWaveHouse.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3),
				G._getY(424)));
		else
			btnWaveHouse.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3),
				G._getY(212)));
		
		BsButton btnFactory = BsButton.button(G._getImg("level_4"),
				G._getImg("level_4"), this, "actionLevel4");
		if(G.hpdi == true)
			btnFactory.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3),
				G._getY(168)));
		else
			btnFactory.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 * 3),
				G._getY(84)));
		
		btnField.setEnable(true);
		btnRoom.setVisible(false);
		btnWaveHouse.setVisible(false);
		btnFactory.setVisible(false);

		
		CCSprite spriteRoom = CCSprite.sprite(G._getImg("locked"));
		G.setScale(spriteRoom);
		if(G.hpdi == true)
			spriteRoom.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 + 60), G._getY(384)));
		else
			spriteRoom.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 4 + 30), G._getY(192)));
		if(G.hpdi == true)
			spriteRoom.setPosition(CGPoint.ccp(G._getX(308), G._getY(480)));
		else
			spriteRoom.setPosition(CGPoint.ccp(G._getX(154), G._getY(240)));

		CCSprite spriteWaveHouse = CCSprite.sprite(G._getImg("locked"));
		G.setScale(spriteWaveHouse);

		if(G.hpdi == true)
			spriteWaveHouse.setPosition(CGPoint.ccp(G._getX(634),
				G._getY(480)));
		else
			spriteWaveHouse.setPosition(CGPoint.ccp(G._getX(317),
				G._getY(240)));
		
		CCSprite spriteFactory = CCSprite.sprite(G._getImg("locked"));
		G.setScale(spriteFactory);
		
		if(G.hpdi == true)
			spriteFactory.setPosition(CGPoint.ccp(G._getX(674),
				G._getY(200)));
		else
			spriteFactory.setPosition(CGPoint.ccp(G._getX(337),
				G._getY(100)));

		   	
		spriteWaveHouse.setVisible(true);
		spriteRoom.setVisible(true);
		spriteFactory.setVisible(true);

		if (AppSettings.stage > 1) {
		    btnRoom.setVisible(true);
		   spriteRoom.setVisible(false);
		}
		if (AppSettings.stage > 2) {
		   btnWaveHouse.setVisible(true);
		   spriteWaveHouse.setVisible(false);
		}
		if (AppSettings.stage > 3) {
		  btnFactory.setVisible(true);
		  spriteFactory.setVisible(false);
		}
		
		addChild(bgSp);
		addChild(spriteRoom);
		addChild(spriteWaveHouse);
		addChild(spriteFactory);
		addChild(btnField);
		addChild(btnRoom);
		addChild(btnWaveHouse);
		addChild(btnFactory);
		
		btnField.setOpacity(0);
		btnRoom.setOpacity(0);
		btnWaveHouse.setOpacity(0);
		btnFactory.setOpacity(0);
		
		m_spStory = CCSprite.sprite(G._getImg(String.format("%d", AppSettings.stage)));
		G.setScale(m_spStory);
		m_spStory.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(m_spStory);
		if(AppSettings.isStory)
		{
			m_spStory.setVisible(true);
			AppSettings.isFirstLevel = true;
			//isFirstStory = true;
			CCDelayTime delay = CCDelayTime.action(6);
			CCCallFunc nextaction = CCCallFunc.action(this, "spriteStory");
			CCSequence animateSeq = CCSequence.actions(delay, nextaction);
			runAction(animateSeq);
		}else
		{
			m_spStory.setVisible(false);
		}

	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		btnField = null;
		btnRoom = null;
		btnWaveHouse = null;
		btnFactory = null;
		m_spStory = null;
		
		System.gc();
		super.onExit();
	}

	public void spriteStory()
	{
		m_spStory.setVisible(false);
	}
	
	public void actionLevel1(Object sender) {
		AppSettings.stage = 1;
	CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionLevel2(Object sender) {
		AppSettings.stage = 2;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionLevel3(Object sender) {
		AppSettings.stage = 3;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionLevel4(Object sender) {
		AppSettings.stage = 4;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}

}