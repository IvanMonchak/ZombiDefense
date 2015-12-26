/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package com.LCJ.Layers;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.config.ccMacros;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCLabel.TextAlignment;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;
import org.cocos2d.utils.javolution.MathLib;

import android.util.Log;
import android.view.MotionEvent;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.ZombiDefense.ZombiDefense;
import com.LCJ.Objects.boxObject;
import com.LCJ.Objects.bulletObject;
import com.LCJ.Objects.zombieObject;
import com.LCJ.Others.BsButton;

public class GamePlayLayer extends CCLayer {
	public static CCScene scene() {
		CCScene s = CCScene.node();
		s.addChild(new GamePlayLayer());
		return s;
	}

	CCSprite m_spBack;
	CCLabel labelS;
	CCLabel labelS_shadow;
	CCLabel label1;
	CCLabel labelLevel;
	CCLabel labelLevel_shadow;
	CCLabel labelMoney;
	CCLabel labelMoney_shadow;
	CCLayer  layer_force;
	CCLabel labelTutorial;
	CCLabel labelTutorial1;
	BsButton m_btnStart;
	BsButton m_btnReload;
	BsButton m_btnContinue;
	BsButton m_btnFacebook;
	CCSprite boxMessage;
	CCSprite m_spBox;
	CCSprite m_spComplate;
	CCSprite m_spBox_enable;
	CCLabel labelBox;
	CCLabel labelBox_shadow;
	CCLabel labelBoxHelp;
	CCLabel labelBoxHelp_shadow;
	CCSprite m_spHero;
	CCSprite m_spWall;
	List<CCSprite> arrayZombie;
	List<CCSprite> arrayBullet;
	List<CCSprite> arrayBox;
	boolean isStart;
	int gunState;
	int wallState;
	int bullet;
	int kills;
	boolean isEnd;
	boolean isFail;
	boolean isGameOver;
	CCLabel labelEnd;
	CCLabel labelEndHelp;
	int levelZombieNum;
	int timer;
	CCLabel labelHelp;

	CCSprite m_spMoveBox;
	int touchState;
	CCSprite m_spWallHealth;
	
	float oldX, oldY;

	int isLastTutorial;
	CCTexture2D[][] m_txtHeros = new CCTexture2D[4][2];
	boolean isFire;
	
	boolean isBoxPick;
	
	CCSprite            boxMessage1;
	CCSprite            boxMessage2;
	BsButton            m_btnYes;
	BsButton            m_btnYes1;
	BsButton            m_btnNo;
	boolean             isTutorial = false;
	ZombiDefense dd = (ZombiDefense) CCDirector.sharedDirector().getActivity();

	//20120221
	boolean isRifle = false;
	CGPoint pressPoint;
	
//	boolean isStart = false;
	
	public GamePlayLayer() {
		super();
		isTouchEnabled_ = true;
		arrayZombie = new ArrayList<CCSprite>();
		arrayBullet = new ArrayList<CCSprite>();
		arrayBox = new ArrayList<CCSprite>();
		createBackground();
		createButtons();
		isStart = false;
		isEnd = false;
		isFail = false;
		isLastTutorial = 0;
		touchState = 0;
		initZombies();


		if(AppSettings.isFirstPlay && AppSettings.isStory){
			String strhelp = "You'll be starting with the pistol. You have 7 shots in a clip but unlimited reloads. You click to shoot so get that finger ready to kill some zombies!";
			labelTutorial1.setString(strhelp);
			labelTutorial1.setVisible(true);
			boxMessage.setVisible(true);
			m_btnYes1.setVisible(true);
			labelBoxHelp.setVisible(false);
			labelBoxHelp_shadow.setVisible(false);
		}
		
		setWallHealth();
		if (AppSettings.isStory && AppSettings.isSwapGun) {
			isEnd = true;
			
			labelBox.setVisible(false);
			labelBox_shadow.setVisible(false);
			labelBoxHelp.setVisible(false);
			labelBoxHelp_shadow.setVisible(false);
			labelLevel.setVisible(false);
			labelLevel_shadow.setVisible(false);
			labelMoney.setVisible(false);
			labelS.setVisible(false);
			label1.setVisible(false);
			labelMoney_shadow.setVisible(false);
			labelS_shadow.setVisible(false);
			m_btnReload.setVisible(false);
			m_btnStart.setVisible(false);
			m_spBox.setVisible(false);
			m_spBox_enable.setVisible(false);
			m_spHero.setVisible(false);
			//m_spWall.setVisible(false);
			//m_spWallHealth.setVisible(false);
			
			AppSettings.isTutoMsgShown = true;
			labelTutorial1.setString("Alrighty! Now let's try changing weapons.\nSwipe up or down over your character for a different gun.");
			labelTutorial1.setVisible(true);
			boxMessage1.setVisible(true);
			if (G.hpdi == true){
				boxMessage1.setPosition(G._getX(G.DEFAULT_W/2), G._getY(380));
			}else{
				boxMessage1.setPosition(G._getX(G.DEFAULT_W/2), G._getY(190));
			}
			CCDelayTime delay = CCDelayTime.action(5);
			CCCallFunc nextaction = CCCallFunc.action(this, "TurtoialSwipe");
			CCSequence animateSeq = CCSequence.actions(delay, nextaction);
			runAction(animateSeq);
      }
		if (G.hpdi == true){
			labelEnd = CCLabel.makeLabel("GAME OVER", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 100);
			G.setScale(labelEnd);
			labelEnd.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(G.DEFAULT_H /2 )));
		}else{
			labelEnd = CCLabel.makeLabel("Game OVER", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 50);
			G.setScale(labelEnd);
			labelEnd.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(G.DEFAULT_H /2)));
		}
		labelEnd.setColor(ccColor3B.ccRED);
		addChild(labelEnd);
		labelEnd.setVisible(false);
		
		m_spComplate = CCSprite.sprite(G._getImg("complate"));
		G.setScale(m_spComplate);
		m_spComplate.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H / 2)));
		addChild(m_spComplate);
		m_spComplate.setVisible(false);
		
		scheduleUpdate();
	}
	
	public void labelHelp()
	{
		AppSettings.isTutoMsgShown = false;
		//m_btnStart.setEnable(true);
		layer_force.setVisible(false);
		labelTutorial.setVisible(false);
		boxMessage2.setVisible(false);
		gotoMain();
	}
	
	public void initZombies() {

		for (int i = 0; i < arrayZombie.size(); i++) {
			zombieObject zObj = (zombieObject) arrayZombie.get(i);
			removeChild(zObj, true);
		}
		arrayZombie.clear();
		levelZombieNum = 0;
		kills = AppSettings.level * 3;
		for (int i = 0; i < arrayBullet.size(); i++) {
			zombieObject bObj = (zombieObject) arrayBullet.get(i);
			removeChild(bObj, true);
		}
		arrayBullet.clear();
		for (int i = 0; i < arrayBox.size(); i++) {
			zombieObject aObj = (zombieObject) arrayBox.get(i);
			removeChild(aObj, true);
		}
		arrayBox.clear();
	}

	public void createZombies() {
		boolean fCreate = true;
		float nCreateHeight = G._getX(G.DEFAULT_W
				- (G.DEFAULT_W / (AppSettings.level + 3)));

		for (int i = 0; i < arrayZombie.size(); i++) {
			zombieObject zObj = (zombieObject) arrayZombie.get(i);
			if (zObj.getPosition().x > nCreateHeight)
				fCreate = false;
		}

		if (fCreate && (levelZombieNum < (AppSettings.level * 3))) {
			int nRand = 0;
			int nYDelta = 0;
			if(G.hpdi == true){
				nRand = 200;nYDelta = 160;
			}else{
				nRand = 100;nYDelta = 80;
			}
			int yPos = (int) (MathLib.random(0, (nRand - 1)) % nRand); // arc4random()
			zombieObject zObj = zombieObject.createZombie(G._getX(G.DEFAULT_W),
					G._getY(yPos + nYDelta));
			zObj.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H / 2)));
			addChild(zObj, 1);
			arrayZombie.add(zObj);
			levelZombieNum++;
		}
		// arrayZombie.sortUsingFunction:arrangeZombiePos context:self];
		// arrayZombie.
		// sort();
		for (int i = 0; i < arrayZombie.size(); i++) {
			zombieObject zObj = (zombieObject) arrayZombie.get(i);
			removeChild(zObj, true);
			zObj.setPosition(CGPoint.ccp(zObj.x, zObj.y));
			addChild(zObj, (arrayZombie.size() - i));
		}
	}

	public void createBullet(float x, float y) {
		// arrayBullet = new ArrayList<CCSprite>();
		bulletObject zObj = bulletObject.createBullet(x, y, gunState);
		// zObj=(bulletObject) CCSprite.sprite(G._getImg("bullet"));
		zObj.setPosition(CGPoint.ccp(zObj.sposx, zObj.sposy));
		addChild(zObj, 2);
		arrayBullet.add(zObj);
	}

	public void createBox(float x, float y) {
		if (x < G._getX(G.DEFAULT_W / 3) || y > G._getX(G.DEFAULT_H * 9 / 16)) {
			if(touchState == 3){
				boxObject zObj = boxObject.createBox(x, y);
				G.setScale(zObj);
				zObj.setPosition(CGPoint.ccp(oldX, oldY));
				addChild(zObj);
				arrayBox.add(zObj);
			}
			return;
		}
		boxObject zObj = boxObject.createBox(x, y);
		G.setScale(zObj);
		zObj.setPosition(CGPoint.ccp(zObj.x, zObj.y));
		addChild(zObj);
		arrayBox.add(zObj);
		if(isBoxPick){
			int add = AppSettings.boxState--;
			Log.d("Gameplayer", "boxStat"+add);
			labelBox.setString(String.format("%d", AppSettings.boxState));
			labelBox_shadow.setString(String.format("%d", AppSettings.boxState));
			isBoxPick = false;
		}
	}

	public void createBackground() {
		String strBack = String.format("map%d", AppSettings.stage);
		CCSprite bgSp = CCSprite.sprite(G._getImg(strBack));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);

		m_spBox = CCSprite.sprite(G._getImg("btn_buy_box1"));
		G.setScale(m_spBox);
		if (G.hpdi == true)
			m_spBox.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(560)));
		else
			m_spBox.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(280)));
		addChild(m_spBox);

		m_spBox_enable = CCSprite.sprite(G._getImg("btn_buy_box"));
		G.setScale(m_spBox_enable);
		if (G.hpdi == true)
			m_spBox_enable.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(560)));
		else
			m_spBox_enable.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(280)));
		addChild(m_spBox_enable);
		m_spBox_enable.setVisible(false);

		if (AppSettings.wallState1 > 0) {
			wallState = 1;
		}
		if (AppSettings.wallState2 > 0) {
			wallState = 2;
		}
		if (AppSettings.wallState3 > 0) {
			wallState = 3;
		}
		if (AppSettings.wallState4 > 0) {
			wallState = 4;
		}
		if (wallState > 0 && wallState < 5) {
			String strShelt = String.format("shelt%d", wallState);
			m_spWall = CCSprite.sprite(G._getImg(strShelt));
			G.setScale(m_spWall);
			if (G.hpdi == true)
				m_spWall.setPosition(CGPoint.ccp(G._getX(180), G._getY(220)));
			else
				m_spWall.setPosition(CGPoint.ccp(G._getX(90), G._getY(110)));
			addChild(m_spWall);

			String strShelt1 = String.format("bar_health%d", wallState);
			m_spWallHealth = CCSprite.sprite(G._getImg(strShelt1));
			G.setScale(m_spWallHealth);
			m_spWallHealth.setPosition(G._getX(20), G._getY(0));
			m_spWallHealth.setAnchorPoint(0, 0);
			addChild(m_spWallHealth);
		}

		if (AppSettings.gunState == 1) {
			gunState = 1;
			bullet = 7;
		}
		if ((AppSettings.gunState / 10) % 10 == 1) {
			gunState = 2;
			bullet = 6;
		}
		if ((AppSettings.gunState / 100) % 10 == 1) {
			gunState = 3;
			bullet = 20;
		}
		if ((AppSettings.gunState / 1000) % 10 == 1) {
			gunState = 4;
			bullet = 5;
		}

		String strGun = String.format("hgun_%d_1", gunState);
		m_spHero = CCSprite.sprite(G._getImg(strGun));
		G.setScale(m_spHero);
		if (G.hpdi == true)
			m_spHero.setPosition(CGPoint.ccp(G._getX(130), G._getY(260)));
		else
			m_spHero.setPosition(CGPoint.ccp(G._getX(65), G._getY(130)));
		addChild(m_spHero);

		for (int i = 0; i < 4; i++) {
			String strHero = String.format("hgun_%d_1", i + 1);
			m_txtHeros[i][0] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strHero));
			strHero = String.format("hgun_%d_2", i + 1);
			m_txtHeros[i][1] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strHero));
		}
	}

	public void changeGun() {
		if (gunState == 1) {
			if ((AppSettings.gunState / 10)%10 == 1){ 
				gunState = 2; 
				AppSettings.playEffect(AppSettings.kCockgun2);
			} else if ((AppSettings.gunState / 100)%10 == 1){ 
				gunState = 3;
				AppSettings.playEffect(AppSettings.kCockgun2);
			} else if ((AppSettings.gunState / 1000)%10 == 1){ 
				gunState = 4;
				AppSettings.playEffect(AppSettings.kCockgun2);
			}
		} else if (gunState == 2) {
			if ((AppSettings.gunState / 100)%10 == 1) {
				gunState = 3;
				AppSettings.playEffect(AppSettings.kCockgun2);
			} else if ((AppSettings.gunState / 1000)%10 == 1) {
				gunState = 4;
				AppSettings.playEffect(AppSettings.kCockgun2);
			} else { 
				gunState = 1;
				AppSettings.playEffect(AppSettings.kCockgun2);
			}
		} else if (gunState == 3) {
			if ((AppSettings.gunState / 1000)%10 == 1) {
				gunState = 4;
				AppSettings.playEffect(AppSettings.kCockgun2);
			} else {
				gunState = 1;
				AppSettings.playEffect(AppSettings.kCockgun2);
			}
		} else if(gunState == 4){
			gunState = 1;
			AppSettings.playEffect(AppSettings.kCockgun2);
		}
			
		if (gunState == 1) {
			bullet = 7;
		} else if (gunState == 2) {
			bullet = 6;
		} else if (gunState == 3) {
			bullet = 20;
		} else if (gunState == 4) {
			bullet = 5;
		}
		
		m_spHero.setTexture(m_txtHeros[gunState - 1][0]);
		CGRect rect = CGRect.zero();
		rect.size = m_spHero.getTexture().getContentSize();
		m_spHero.setTextureRect(rect);
	}

	public void createButtons() {
		m_btnStart = BsButton.button(G._getImg("btn_wave_nor"),
				G._getImg("btn_wave_dow"), this, "actionStart");
		if (G.hpdi == true)
			m_btnStart.setPosition(CGPoint.ccp(G._getX(780), G._getY(560)));
		else
			m_btnStart.setPosition(CGPoint.ccp(G._getX(390), G._getY(280)));
		addChild(m_btnStart);

		m_btnReload = BsButton.button(G._getImg("btn_reload_nor"),
				G._getImg("btn_reload_dow"), this, "actionReload");
		if (G.hpdi == true)
			m_btnReload.setPosition(CGPoint.ccp(G._getX(780), G._getY(560)));
		else
			m_btnReload.setPosition(CGPoint.ccp(G._getX(390), G._getY(285)));
		addChild(m_btnReload);
		m_btnReload.setVisible(false);
		
		
		boxMessage1 = CCSprite.sprite(G._getImg("boxmessage_line"));
		G.setScale(boxMessage1);
		boxMessage1.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(boxMessage1);
		boxMessage1.setVisible(false);
		
		boxMessage2 = CCSprite.sprite(G._getImg("boxmessage_line_1"));
		G.setScale(boxMessage2);
		boxMessage2.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(boxMessage2);
		boxMessage2.setVisible(false);
		
		if (G.hpdi == true){
			labelLevel_shadow = CCLabel.makeLabel(String.format("WAVE %d", AppSettings.level), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 92);
			G.setScale(labelLevel_shadow);
			labelLevel_shadow.setPosition(CGPoint.ccp(G._getX(180), G._getY(570)));
		}else{
			labelLevel_shadow = CCLabel.makeLabel(String.format("WAVE %d", AppSettings.level), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 46);
			G.setScale(labelLevel_shadow);
			labelLevel_shadow.setPosition(CGPoint.ccp(G._getX(90), G._getY(285)));
		}
		
		labelLevel_shadow.setColor(ccColor3B.ccRED);
		addChild(labelLevel_shadow);
		
		if (G.hpdi == true){
			labelLevel  = CCLabel.makeLabel(String.format("WAVE %d", AppSettings.level), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 90);
			G.setScale(labelLevel);
			labelLevel.setPosition(CGPoint.ccp(G._getX(180), G._getY(570)));
		}else{
			labelLevel = CCLabel.makeLabel(String.format("WAVE %d", AppSettings.level), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 45);
			G.setScale(labelLevel);
			labelLevel.setPosition(CGPoint.ccp(G._getX(90), G._getY(285)));
		}
		
		labelLevel.setColor(ccColor3B.ccBLACK);
		addChild(labelLevel);
		
		if (G.hpdi == true){
			labelBox_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 82);
			G.setScale(labelBox_shadow);
			labelBox_shadow.setPosition(CGPoint.ccp(G._getX(478), G._getY(578)));
		}else{
			labelBox_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 41);
			G.setScale(labelBox_shadow);
			labelBox_shadow.setPosition(CGPoint.ccp(G._getX(239), G._getY(289)));
		}
		
		labelBox_shadow.setColor(ccColor3B.ccRED);
		addChild(labelBox_shadow);
		
		if (G.hpdi == true){
			labelBox  = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 80);
			G.setScale(labelBox);
			labelBox.setPosition(CGPoint.ccp(G._getX(478), G._getY(578)));
		}else{
			labelBox = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 40);
			G.setScale(labelBox);
			labelBox.setPosition(CGPoint.ccp(G._getX(239), G._getY(289)));
		}
		
		labelBox.setColor(ccColor3B.ccBLACK);
		addChild(labelBox);

		
		
		//money
		if (G.hpdi == true){
			labelMoney_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 76);
			G.setScale(labelMoney_shadow);
			labelMoney_shadow.setPosition(CGPoint.ccp(G._getX(200), G._getY(570)));
		}else{
			labelMoney_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 38);
			G.setScale(labelMoney_shadow);
			labelMoney_shadow.setPosition(CGPoint.ccp(G._getX(100), G._getY(285)));
		}
		
		labelMoney_shadow.setColor(ccColor3B.ccRED);
		addChild(labelMoney_shadow);
		labelMoney_shadow.setVisible(false);
		
		if (G.hpdi == true){
			labelMoney  = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 74);
			G.setScale(labelMoney);
			labelMoney.setPosition(CGPoint.ccp(G._getX(200), G._getY(570)));
		}else{
			labelMoney = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 37);
			G.setScale(labelMoney);
			labelMoney.setPosition(CGPoint.ccp(G._getX(100), G._getY(285)));
		}
		
		labelMoney.setColor(new ccColor3B(8, 139, 8));
		addChild(labelMoney);
		labelMoney.setVisible(false);
			
		//$
		if (G.hpdi == true){
			label1 = CCLabel.makeLabel("1", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky1"), 64);
			G.setScale(label1);
			label1.setPosition(CGPoint.ccp(G._getX(80), G._getY(570)));
		}else{
			label1 = CCLabel.makeLabel("1", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky1"), 32);
			G.setScale(label1);
			label1.setPosition(CGPoint.ccp(G._getX(40), G._getY(285)));
		}
		
		label1.setColor(new ccColor3B(8, 139, 8));
		addChild(label1);
		label1.setVisible(false);
		
		if (G.hpdi == true){
			labelS_shadow = CCLabel.makeLabel("S", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 64);
			G.setScale(labelS_shadow);
			labelS_shadow.setPosition(CGPoint.ccp(G._getX(80), G._getY(570)));
		}else{
			labelS_shadow = CCLabel.makeLabel("S", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 32);
			G.setScale(labelS_shadow);
			labelS_shadow.setPosition(CGPoint.ccp(G._getX(40), G._getY(285)));
		}
		
		labelS_shadow.setColor(ccColor3B.ccRED);
		addChild(labelS_shadow);
		labelS_shadow.setVisible(false);
		
		if (G.hpdi == true){
			labelS  = CCLabel.makeLabel("S", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 60);
			G.setScale(labelS);
			labelS.setPosition(CGPoint.ccp(G._getX(80), G._getY(570)));
		}else{
			labelS = CCLabel.makeLabel("S", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 30);
			G.setScale(labelS);
			labelS.setPosition(CGPoint.ccp(G._getX(40), G._getY(285)));
		}
		
		labelS.setColor(new ccColor3B(8, 139, 8));
		addChild(labelS);
		labelS.setVisible(false);
		
		if (G.hpdi == true){
			labelEndHelp = CCLabel.makeLabel("click continue", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 40);
			G.setScale(labelEndHelp);
			labelEndHelp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(G.DEFAULT_H /4 )));
		}else{
			labelEndHelp = CCLabel.makeLabel("click continue", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 20);
			G.setScale(labelEndHelp);
			labelEndHelp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(G.DEFAULT_H /4)));
		}
		labelEndHelp.setColor(ccColor3B.ccRED);
		addChild(labelEndHelp);
		labelEndHelp.setVisible(false);
	
		layer_force = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 255), G._scaleX*G.DEFAULT_W, G._scaleY*G.DEFAULT_H);
		layer_force.setPosition(G._getX(G.DEFAULT_W/2),G._getY(G.DEFAULT_H/2));

		layer_force.setRelativeAnchorPoint(true);
		((CCColorLayer) layer_force).setOpacity(150);
		addChild( layer_force);
		layer_force.setVisible(false);
		
		boxMessage = CCSprite.sprite(G._getImg("boxmessage"));
		G.setScale(boxMessage);
		boxMessage.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(boxMessage);
		boxMessage.setVisible(false);
		
		if (G.hpdi == true){
			labelTutorial = CCLabel.makeLabel("", 
			CGSize.make(G.DEFAULT_W/4*3, G.DEFAULT_H/2),
			TextAlignment.CENTER, G._getFont("Rocky"), 36);
			G.setScale(labelTutorial);
			labelTutorial.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(360)));
		}else{
			labelTutorial = CCLabel.makeLabel("", 
				CGSize.make(G.DEFAULT_W/4*3, G.DEFAULT_H/2),
				TextAlignment.CENTER, G._getFont("Rocky"), 18);
			G.setScale(labelTutorial);
			labelTutorial.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(180)));
		}
		
		labelTutorial.setColor(ccColor3B.ccBLACK);
		addChild(labelTutorial);
		labelTutorial.setVisible(false);
		
		//-------box help------//
		if (G.hpdi == true){
			labelBoxHelp_shadow = CCLabel.makeLabel("Drag to place", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 41);
			G.setScale(labelBoxHelp_shadow);
			labelBoxHelp_shadow.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H * 3 / 4-2)));
		}else{
			labelBoxHelp_shadow = CCLabel.makeLabel("Drag to place", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 20);
			G.setScale(labelBoxHelp_shadow);
			labelBoxHelp_shadow.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H * 3 / 4-2)));
		}
		labelBoxHelp_shadow.setColor(ccColor3B.ccRED);
		labelBoxHelp_shadow.setOpacityModifyRGB(false);
		addChild(labelBoxHelp_shadow);
		
		if (G.hpdi == true){
			labelBoxHelp = CCLabel.makeLabel("Drag to place", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 40);
			G.setScale(labelBoxHelp);
			labelBoxHelp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H * 3 / 4)));
		}else{
			labelBoxHelp = CCLabel.makeLabel("Drag to place", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 20);
			G.setScale(labelBoxHelp);
			labelBoxHelp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
					G._getY(G.DEFAULT_H * 3 / 4)));
		}

		labelBoxHelp.setColor(new ccColor3B(8, 139, 8));
		labelBoxHelp.setOpacityModifyRGB(false);
		addChild(labelBoxHelp);
		//---------------//
		
		if (G.hpdi == true){
			labelTutorial1 = CCLabel.makeLabel("", 
			CGSize.make(G.DEFAULT_W/4*3, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 36);
			G.setScale(labelTutorial1);
			labelTutorial1.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(380)));
		}else{
			labelTutorial1 = CCLabel.makeLabel("", 
				CGSize.make(G.DEFAULT_W/4*3, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 18);
			G.setScale(labelTutorial1);
			labelTutorial1.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(190)));
		}
		
		labelTutorial1.setColor(ccColor3B.ccBLACK);
		addChild(labelTutorial1);
		
		//--tutorial--yes or No buttons------//
		m_btnYes1 = BsButton.button(G._getImg("btn_yes_nor"),
				G._getImg("btn_yes_dow"), this, "actionYes1");
		if(G.hpdi == true)
			m_btnYes1.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2),	G._getY(140)));
		else
			m_btnYes1.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2),	G._getY(70)));
		addChild(m_btnYes1);
		m_btnYes1.setVisible(false);
		
		m_btnYes = BsButton.button(G._getImg("btn_yes_nor"),
				G._getImg("btn_yes_dow"), this, "actionYes");
		if(G.hpdi == true)
			m_btnYes.setPosition(CGPoint.ccp(G._getX(360),	G._getY(140)));
		else
			m_btnYes.setPosition(CGPoint.ccp(G._getX(180),	G._getY(70)));
		addChild(m_btnYes);
		m_btnYes.setVisible(false);
		
		m_btnNo = BsButton.button(G._getImg("btn_no_nor"),
				G._getImg("btn_no_dow"), this, "actionNo");
		if(G.hpdi == true)
			m_btnNo.setPosition(CGPoint.ccp(G._getX(600),	G._getY(140)));
		else
			m_btnNo.setPosition(CGPoint.ccp(G._getX(300),	G._getY(70)));
		addChild(m_btnNo);
		m_btnNo.setVisible(false);
		//-------------//
		
		m_btnContinue = BsButton.button(G._getImg("btn_continue_nor"),
				G._getImg("btn_continue_dow"), this, "actionContinue");
		if (G.hpdi == true)
			m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(140)));
		else
			m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(70)));
		addChild(m_btnContinue);
		m_btnContinue.setVisible(false);
		
		m_btnFacebook = BsButton.button(G._getImg("btn_facebook_nor"),
				G._getImg("btn_facebook_dow"), this, "actionFacebook");
		if (G.hpdi == true)
			m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(140)));
		else
			m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(70)));
		addChild(m_btnFacebook);
		m_btnFacebook.setVisible(false);
		
		
		if (AppSettings.boxState == 0) {
			labelBoxHelp_shadow.setVisible(false);
			labelBoxHelp.setVisible(false);
		}
		levelEnable();
	}

	public void startGame() {
		layer_force.setVisible(false);
		labelTutorial1.setVisible(false);
		AppSettings.isTutoMsgShown = false;
		m_spBox.setVisible(false);
		m_spBox_enable.setVisible(true);
		isStart = true;
		labelLevel.setVisible(false);
		labelLevel_shadow.setVisible(false);
		labelS.setVisible(true);
		label1.setVisible(true);
		labelMoney.setVisible(true);
		labelS_shadow.setVisible(true);
		labelMoney_shadow.setVisible(true);
		
//		unscheduleAllSelectors();
//		schedule("tick", 1.0f / 90.0f);
//		schedule("tick",(float)0.001);
		// AppSettings.tutorial = false;
	}
	
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		m_spBack = null;
		labelS = null;
		labelS_shadow = null;
		label1 = null;
		labelLevel = null;
		labelLevel_shadow = null;
		labelMoney = null;
		labelMoney_shadow = null;
		layer_force = null;
		labelTutorial = null;
		labelTutorial1 = null;
		m_btnStart = null;
		m_btnReload = null;
		m_btnContinue = null;
		boxMessage = null;
		m_spBox = null;
		m_spComplate = null;
		m_spBox_enable = null;
		labelBox = null;
		labelBox_shadow = null;
		labelBoxHelp = null;
		labelBoxHelp_shadow = null;
		m_spHero = null;
		m_spWall = null;
		arrayZombie = null;
		arrayBullet = null;
		arrayBox = null;
		labelEnd = null;
		labelEndHelp = null;
		labelHelp = null;
		m_spMoveBox = null;
		m_spWallHealth = null;
		m_txtHeros  = null;
		boxMessage1 = null;
		boxMessage2 = null;
		m_btnYes = null;
		m_btnYes1 = null;
		m_btnNo = null;
		//CCDirector.sharedDirector().end();
		unscheduleUpdate();//("tick");
		unschedule("tick1");
		
		System.gc();
		super.onExit();
		
	}

	public void actionStart(Object sender) {
		m_btnStart.setVisible(false);
		if (AppSettings.isStory && AppSettings.isFirstPlay) {
			AppSettings.isTutoMsgShown = true;
//			layer_force.setVisible(true);
//			labelTutorial.setVisible(false);
//			String strhelp = "You'll be starting with the shotgun.\nFor level 1 a shotgun kills a zombie in 3 shots long range and 1 shot close range.\nFor each gun you have 7 shots per clip. After 1 shot you get the option to reload with the top Reload button.\nIt's click to shoot so get that finger ready to shoot some zombies!";
//			labelTutorial1.setString(strhelp);
//			labelTutorial1.setVisible(true);
//			boxMessage.setVisible(true);
//			m_btnYes.setVisible(true);
//			m_btnNo.setVisible(true);
			labelBoxHelp.setVisible(false);
			labelBoxHelp_shadow.setVisible(false);
			AppSettings.isFirstPlay = false;
			isTutorial = true;
			startGame();
			return;
		}
		
		AppSettings.isTutoMsgShown  = false;
		m_spBox.setVisible(false);
		m_spBox_enable.setVisible(true);
		isStart = true;
		labelLevel.setVisible(false);
		labelLevel_shadow.setVisible(false);
		labelS.setVisible(true);
		label1.setVisible(true);
		labelMoney.setVisible(true);
		labelS_shadow.setVisible(true);
		labelMoney_shadow.setVisible(true);
		labelBoxHelp.setVisible(false);
		labelBoxHelp_shadow.setVisible(false);
//		unscheduleAllSelectors();
//		schedule("tick", 1.0f / 90.0f);
//		schedule("tick",(float)0.001);
	}

	public void actionYes1(Object sender){
		labelTutorial1.setVisible(false);
		
		boxMessage.setVisible(false);
		m_btnYes1.setVisible(false);
	}
	
	public void actionYes(Object sender)
	{
		if(isTutorial == false){
			isTutorial = true;
			return;
		}
		labelTutorial.setVisible(false);
		AppSettings.isFirstPlay = false;
		
		boxMessage.setVisible(false);
		m_btnYes.setVisible(false);
		m_btnNo.setVisible(false);
		
		if(isTutorial == true){
			labelTutorial1.setVisible(false);
			startGame();	
		}else{
			labelBoxHelp.setVisible(true);
			labelBoxHelp_shadow.setVisible(true);
		}
	}
	
	public void actionNo(Object sender)
	{
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, StartLayer.scene(),
						ccColor3B.ccBLACK));
		AppSettings.isStory = false;
		AppSettings.isSurvival= false;
		AppSettings.isFirstPlay = false;
	}
	
	public void actionFacebook(Object sender){
		ZombiDefense activity = (ZombiDefense) CCDirector.sharedDirector().getActivity();
		activity.Help.sendEmptyMessage(ZombiDefense.SHOW_FACEBOOK_DLG);
	}
	
	public void actionContinue(Object sender){
		if(isGameOver == true){
			isGameOver = false;
			labelEnd.setVisible(false);
			CCDirector.sharedDirector()
			.replaceScene(CCFadeTransition.transition(0.6f,
			StartLayer.scene(),ccColor3B.ccBLACK));
			
//			unschedule("tick");
			unschedule("tick1");
			System.gc();
			
			return;
		}
			
		if (!isFail) {
			if(AppSettings.level == 10 && AppSettings.stage == 4) {
				CCDirector.sharedDirector().replaceScene(
						CCFadeTransition.transition(1.0f,
						StartLayer.scene(),ccColor3B.ccBLACK));
			}else{
				AppSettings.level++;
				if (AppSettings.level == 11) {
					AppSettings.LevelUp();
					CCDirector.sharedDirector().replaceScene(
						CCFadeTransition.transition(0.6f,
						LevelLayer.scene(), ccColor3B.ccBLACK));
				} else {
					CCDirector.sharedDirector().replaceScene(
						CCFadeTransition.transition(1.0f,
						PreplayLayer.scene(),ccColor3B.ccBLACK));
				}
			}
		} else {
	//			if (AppSettings.tutorial) {
	//				AppSettings.isTutoMsgShown = false;
	//			}
				CCDirector.sharedDirector()
					.replaceScene(CCFadeTransition.transition(0.6f,
					PreplayLayer.scene(),ccColor3B.ccBLACK));
			}
//		unschedule("tick");
		unschedule("tick1");
		System.gc();
//		}
	}
	public void actionReload(Object sender) {
		if (gunState == 1) {
			bullet = 7;
		} else if (gunState == 2) {
			bullet = 6;
		} else if (gunState == 3) {
			bullet = 20;
		} else if (gunState == 4) {
			bullet = 5;
		}

		AppSettings.playEffect(AppSettings.kCockgun1);
		m_btnReload.setVisible(false);
	}

	public void levelEnable() {
	}

	public void TurtoialSwipe() {
		AppSettings.isTutoMsgShown = false;
		AppSettings.isTutorialEnd = false;
		isLastTutorial = 1;
		boxMessage1.setVisible(false);
		m_spHero.setVisible(true);
		labelTutorial1.setVisible(false);
		
		
	}

	public void gameNext() {
		AppSettings.level++;
		if(AppSettings.isSurvival == true)
			return;
		AppSettings.SaveLevelInfo();
		if (AppSettings.level == 11) {
			AppSettings.LevelUp();
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.6f, LevelLayer.scene(),
							ccColor3B.ccBLACK));
		} else {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
							ccColor3B.ccBLACK));
		}

	}

	public void gameTutorial() {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, StartLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void gameEnd() {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void gameEndProc() {
		if (isFail) {
		} else {
			AppSettings.money += G.MONEY_BONUS;
			labelMoney.setString(String.format("%d", AppSettings.money));
			labelMoney_shadow.setString(String.format("%d", AppSettings.money));
		}

		AppSettings.boxState += arrayBox.size();
		for (int i = (arrayBox.size() - 1); i >= 0; i--) {
			boxObject car = (boxObject) arrayBox.get(i);
			removeChild(car, true);
			arrayBox.remove(car);
			car.removeSelf();
		}

		for (int i = (arrayZombie.size() - 1); i >= 0; i--) {
			zombieObject car = (zombieObject) arrayZombie.get(i);
			removeChild(car, true);
			arrayZombie.remove(car);
			car.releaseZombie();
			car.removeSelf();
		}

		for (int i = (arrayBullet.size() - 1); i >= 0; i--) {
			bulletObject car = (bulletObject) arrayBullet.get(i);
			removeChild(car, true);
			arrayBullet.remove(car);
			car.removeSelf();
		}

		labelBox.setVisible(false);
		labelBox_shadow.setVisible(false);
		labelBoxHelp.setVisible(false);
		labelBoxHelp_shadow.setVisible(false);
		labelLevel.setVisible(false);
		labelLevel_shadow.setVisible(false);
		labelMoney.setVisible(false);
		labelS.setVisible(false);
		label1.setVisible(false);
		labelMoney_shadow.setVisible(false);
		labelS_shadow.setVisible(false);
		m_btnReload.setVisible(false);
		m_btnStart.setVisible(false);
		m_spBox.setVisible(false);
		m_spBox_enable.setVisible(false);
		m_spHero.setVisible(false);

		if (wallState > 0 && wallState < 5) {
			m_spWallHealth.setVisible(false);
			m_spWall.setVisible(false);
		}
//		unschedule("tick");
		unscheduleUpdate();
		{
			if(isGameOver == true){
				if (!AppSettings.isStory) {
					if (G.hpdi == true){
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/3), G._getY(140)));
						m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W*2/3), G._getY(140)));
					}else{
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/3), G._getY(70)));
						m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W*2/3), G._getY(70)));
					}
					m_btnContinue.setVisible(true);
					m_btnFacebook.setVisible(true);
				} else {
					if (G.hpdi == true){
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(140)));
					}else{
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(70)));
					}
					m_btnContinue.setVisible(true);
				}
				boxMessage.setVisible(true);
				labelEnd.setVisible(true);
				AppSettings.InitLevelInfo();
				return;
			}
			if (!isFail) {
				if(AppSettings.level == 10 && AppSettings.isStory && AppSettings.stage == 4){ //added in 2012/05/02 "&& AppSettings.isStory"
					m_spComplate.setVisible(true);
					if (G.hpdi == true){
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/3), G._getY(140)));
						m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W*2/3), G._getY(140)));
					}else{
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/3), G._getY(70)));
						m_btnFacebook.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W*2/3), G._getY(70)));
					}
					m_btnContinue.setVisible(true);
					m_btnFacebook.setVisible(true);
				}else{
					int rando = (int) (Math.random() * 8);
					String message;
					if (rando < 1) {
						message = String
								.format("\"Look, you're the one who got me out in the armpit of the world chasing your galloping cadavers.\" - Peter, City of the Living Dead (1980)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 2) {
						message = String
								.format("\"To beat death, you've got to know death.\" - Schatzi, Night of the Dead: Leben Tod (2006)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 3) {
						message = String
								.format("\"You've got a pretty mouth.\" - Tallahassee, Zombieland (2009)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 4) {
						message = String
								.format("\"What do you think? Zombie Kill of the Week?\" - Tallahassee, Zombieland (2009)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 5) {
						message = String
								.format("\"Okay, she's not even your ex-girlfriend anymore. She's a puddle of blood and guts...\" - Blake, The Mad (2007)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 6) {
						message = String
								.format("\"You weren't very nice Mrs. Henderson, but I'm sorry you're dead.\" - Timmy, Fido (2006)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else if (rando < 7) {
						message = String
								.format("\"If we hole up I want to be somewhere familiar, I want to know where the exits are, and I want to be allowed to smoke.\" - Ed, Shaun of the Dead (2004)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					} else {
						message = String
								.format("\"I can smell. I've got a keen sense of smell like a dog. I'm a top breeder. I smell.\" - Fighter, Versus (2000)\n\nYou successfully annihilated Wave %d.",
										AppSettings.level);
					}
					labelTutorial.setString(message);
					if (G.hpdi == true){
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(140)));
//						m_btnFacebook.setPosition(G.DEFAULT_W*2/3, G._getY(140));
					}else{
						m_btnContinue.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W/2), G._getY(70)));
//						m_btnFacebook.setPosition(G.DEFAULT_W*2/3, G._getY(70));
					}
					m_btnContinue.setVisible(true);
				}
			} else if (AppSettings.stage <= 4) { // lose
				labelTutorial.setString("Sorry we have to leave you here, but it just ain't right to eat your wife's and daughter's brains.\" - Luke, Undead or Alive (2007)\nYou have unlocked Level 15. Feel free to continue playing this level, but the next is waiting!");
			} else { // lose last level'
				labelTutorial.setString("I'm not great at farewells, so, uh, that'll do, pig.\" - Tallahassee, Zombieland (2009)\nYou have beaten Zombie Defense!\nIf you're still bloodthirst, feel free to continue on.");
			}
			boxMessage.setVisible(true);
			labelTutorial.setVisible(true);
			m_btnContinue.setVisible(true);
		}
		if (!AppSettings.isSurvival)
			AppSettings.SaveLevelInfo();
	}

	public void waitLastTutorial() {
		AppSettings.isTutoMsgShown = false;
		m_spHero.setVisible(true);
	}

	public void quitTutorial() {
//		labelTutorial.setString("One more time");
//		if (G.hpdi == true){
//			labelTutorial.setPosition(G._getX(G.DEFAULT_W/2), G._getY(320));
//		}else{
//			labelTutorial.setPosition(G._getX(G.DEFAULT_W/2), G._getY(160));
//		}
//		
//		labelTutorial.setVisible(true);
//		boxMessage2.setVisible(true);
		isLastTutorial = 1;
		AppSettings.isTutorialEnd = true;
		CCDelayTime delay = CCDelayTime.action(2);
		CCCallFunc nextaction = CCCallFunc.action(this, "labelHelp");
		CCSequence animateSeq = CCSequence.actions(delay, nextaction);
		runAction(animateSeq);
		return;
	}

	public void gotoMain() {
//		AppSettings.isTutorialEnd = true;
		AppSettings.isSwapGun = false;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PropertyLayer.scene(),
						ccColor3B.ccBLACK));
	}

	public void tick1(float dt){
		if(isRifle == true){
			if (bullet == 0) {
				//AppSettings.playEffect(AppSettings.kCockgun2);
				return ;
			}
			AppSettings.playEffect(AppSettings.kRifle);
			createBullet((int) pressPoint.x,
				(int) pressPoint.y);
			if (bullet > 0) {
				bullet--;
				m_btnReload.setVisible(true);
			}
			isFire = true;
		}
	}
	
	
	public void update(float dt) {
//	public void tick(float dt) {
		if(isStart == false) return;
		createZombies();
		boolean isHit = false;

		for (int i = 0; i < arrayBullet.size(); i++) {
			bulletObject car = (bulletObject) arrayBullet.get(i);
			car.moveBullet();

		for (int ii = (arrayZombie.size() - 1); ii >= 0; ii--) {
			zombieObject car1 = (zombieObject) arrayZombie.get(ii);
			if (car1.status != 1) {
				continue;
			}
			if (car1.touchZombie((float) car.sposx, (float) car.sposy,
						gunState)) {
				if (car1.health <= 0) {
					car1.ani = 0;
					car1.status = 2;
				}
				isHit = true;
				break;
			}
		}

		if (car.sposx > G._getX(G.DEFAULT_W)
					|| car.sposy > G._getY(G.DEFAULT_H)) {
			isHit = true;
		}

		if (isHit) {
			removeChild(car, true);
			arrayBullet.remove(car);
			car.removeSelf();
			break;
		}
	}
	if (AppSettings.isStory) {
		AppSettings.totalTime ++;
	}
	timer++;
	if (timer >= 6) {
		timer = 0;
	}

	if (timer != 0) {
		return;
	}

	if (isFire) {
		m_spHero.setTexture(m_txtHeros[gunState - 1][1]);
//		if (gunState != 3) {
			isFire = false;
//		}
	} 
	else {
		m_spHero.setTexture(m_txtHeros[gunState - 1][0]);
	}

	 CGRect rect = CGRect.zero();
	 rect.size = m_spHero.getTexture().getContentSize();
	 m_spHero.setTextureRect(rect);

		for (int i = 0; i < arrayZombie.size(); i++) {
			zombieObject car = (zombieObject) arrayZombie.get(i);

			boolean fBoxHit = false;
			car.isStop = false;
			for (int k = arrayBox.size() - 1; k >= 0; k--) {
				boxObject box = (boxObject) arrayBox.get(k);
				if (box.state == 5) {
					removeChild(box, true);
					arrayBox.remove(box);
					box.removeSelf();
					continue;
				}
				if (CGRect.intersects(box.rcHit, car.rcHit)) {
					box.changeBoxState();
					fBoxHit = true;
					car.isStop = true;
					// break;
				}
			}
			if (fBoxHit) {
				// continue;
			}
			 float lengthshe =0;
			 float lengthzombi = 0;
			if(G.hpdi == true){
				lengthshe = G._getX(G.DEFAULT_W / 4);
				lengthzombi = car.x - (car.type == 0 ? car.getContentSize().width/6 : car.getContentSize().width/8);
			}else{
				lengthshe = G._getX(G.DEFAULT_W / 4);
				lengthzombi = car.x - (car.type == 0 ? car.getContentSize().width/2 : car.getContentSize().width/4);
			}
			if (car.status == 1
					&& lengthzombi < lengthshe) // attention
			{
				car.isStop = true;
				car.moveZombie();
				car.setPosition(CGPoint.ccp(car.x, car.y));
				int curHealth = 0;
				if (wallState == 4) {
					AppSettings.wallState4--;
					curHealth = AppSettings.wallState4;
				} else if (wallState == 3) {
					AppSettings.wallState3--;
					curHealth = AppSettings.wallState3;
				} else if (wallState == 2) {
					AppSettings.wallState2--;
					curHealth = AppSettings.wallState2;
				} else if (wallState == 1) {
					AppSettings.wallState1--;
					curHealth = AppSettings.wallState1;
				}
				setWallHealth();
				if (curHealth <= 0) {
					if (!changeWall()) {
						// gameover
						isEnd = true;
						isFail = true;
						isGameOver = true;
						gameEndProc();
						return;
					}
				}
			} else if (car.status == 3) {
				car.moveZombie();
			} else if (car.status == 4) {
				removeChild(car, true);
				arrayZombie.remove(car);
				car.removeSelf();
				if (AppSettings.stage == 1) {
					AppSettings.money += 6;
				} else if (AppSettings.stage == 2) {
					AppSettings.money += 4;
				} else if (AppSettings.stage == 3) {
					AppSettings.money += 3;
				} else if (AppSettings.stage == 4) {
					AppSettings.money += 2;
				}
				labelMoney.setString(String.format("%d", AppSettings.money));
				labelMoney_shadow.setString(String.format("%d", AppSettings.money));
				kills--;
				if (kills <= 0) {
					isEnd = true;
					isFail = false;
					gameEndProc();
				}
			} else {
				car.moveZombie();
				car.setPosition(car.x, car.y);
			}
		}
	}

	public void setWallHealth() {
		int maxHealth = 0;
		int curHealth = 0;
		if (wallState == 4) {
			maxHealth = 100;
			curHealth = AppSettings.wallState4;
		} else if (wallState == 3) {
			maxHealth = 60;
			curHealth = AppSettings.wallState3;
		} else if (wallState == 2) {
			maxHealth = 40;
			curHealth = AppSettings.wallState2;
		} else if (wallState == 1) {
			maxHealth = 30;
			curHealth = AppSettings.wallState1;
		}
		int maxHealthWidth = 120;
		if(G.hpdi == true)
			maxHealthWidth = 240;
		else
			maxHealthWidth = 120;
		if (wallState > 0 && wallState < 5) {
			CGRect rect = m_spWallHealth.getTextureRect();
			rect.size.width = (float) maxHealthWidth * (float) curHealth
					/ (float) maxHealth;
			m_spWallHealth.setTextureRect(rect);
		}
	}

	public boolean changeWall() {
		boolean ret = false;

		if (wallState == 4) {
			AppSettings.wallState4 = 0;
			if (AppSettings.wallState3 > 0) {
				wallState = 3;
				ret = true;
			} else if (AppSettings.wallState2 > 0) {
				wallState = 2;
				ret = true;
			} else if (AppSettings.wallState1 > 0) {
				wallState = 1;
				ret = true;
			}
		} else if (wallState == 3) {
			AppSettings.wallState3 = 0;
			if (AppSettings.wallState2 > 0) {
				wallState = 2;
				ret = true;
			} else if (AppSettings.wallState1 > 0) {
				wallState = 1;
				ret = true;
			}
		} else if (wallState == 2) {
			AppSettings.wallState2 = 0;
			if (AppSettings.wallState1 > 0) {
				wallState = 1;
				ret = true;
			}
		} else if (wallState == 1) {

		}

		if (ret) {
			removeChild(m_spWall, true);
			m_spWall.removeSelf();

			removeChild(m_spWallHealth, true);
			m_spWallHealth.removeSelf();
			if (wallState > 0 && wallState < 5) {
				String strShelt = String.format("shelt%d", wallState);
				m_spWall = CCSprite.sprite(G._getImg(strShelt));
				G.setScale(m_spWall);
				if(G.hpdi == true)
					m_spWall.setPosition(G._getX(180), G._getY(220));
				else
					m_spWall.setPosition(G._getX(90), G._getY(110));
				addChild(m_spWall);

				String strhealth = String.format("bar_health%d", wallState);
				m_spWallHealth = CCSprite.sprite(G._getImg(strhealth));
				G.setScale(m_spWallHealth);
				if(G.hpdi == true)
					m_spWallHealth.setPosition(G._getX(20), G._getY(0));
				else
					m_spWallHealth.setPosition(G._getX(10), G._getY(0));
				m_spWallHealth.setAnchorPoint(0, 0);
				addChild(m_spWallHealth);

				setWallHealth();
			}
		}
		return ret;
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if(isStart && touchState == 0 && gunState == 3){
			isRifle = false;
			unschedule("tick1");//AllSelectors();
		}
		if (touchState == 0 || AppSettings.isTutoMsgShown || isLastTutorial > 3)
			return false;
		if (touchState == 2 || touchState == 3) {
			CGPoint convertedLocation = CCDirector.sharedDirector()
					.convertToGL(CGPoint.ccp(event.getX(), event.getY()));
			try {
				if (m_spMoveBox != null) {
					Log.d("m_spMoveBox", "m_spMoveBox !null");
					removeChild(m_spMoveBox, true);
					m_spMoveBox.removeSelf();
					createBox((float) convertedLocation.x,
							(float) convertedLocation.y);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		CGPoint convertedLocation = CCDirector.sharedDirector()
			.convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		pressPoint.x = convertedLocation.x;
		pressPoint.y = convertedLocation.y;
		if (touchState == 0 || AppSettings.isTutoMsgShown || isLastTutorial > 3)
			return false;
		if (touchState == 1) {
			changeGun();
			touchState = 0;
			return true;
		} else if (touchState == 2 || touchState == 3) {
			
			try {
				if (m_spMoveBox!=null) {
					m_spMoveBox.setPosition((float) convertedLocation.x,
							(float) convertedLocation.y);
				}
			} catch (Exception e) {
				
			}

		}else{
			pressPoint.x = convertedLocation.x;
			pressPoint.y = convertedLocation.y;
		}
		return CCTouchDispatcher.kEventHandled;
	}

	@Override
	public boolean ccTouchesCancelled(MotionEvent event) {
//		if (touchState == 0 || AppSettings.isTutoMsgShown || isLastTutorial > 3) return;
//	    if (touchState == 2 || touchState == 3) {
//	            if (touchState == 2) {
//	                removeChild(m_spMoveBox, true);
//	                m_spMoveBox.removeSelf();
//	            }
//	        }
//	        if(1 == touches.count) 
//	        {
//	            CGPoint point = [touch locationInView:[touch view]];
//	            CGPoint convertedLocation = [[CCDirector sharedDirector] convertToGL:point];
//	            
//	            //            if (m_spMoveBox) 
//	            {
//	                [self removeChild:m_spMoveBox cleanup:YES];
//	                [m_spMoveBox release];
//	                [self createBox:(float)convertedLocation.x Y:(float)convertedLocation.y];
		return ccTouchesEnded(event);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
	{
		CGPoint convertedLocation = CCDirector.sharedDirector()
			.convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		pressPoint = convertedLocation;
		if (AppSettings.isTutoMsgShown || isLastTutorial > 3) {
			return true;
		}
		
		if (isEnd) {
			if (isLastTutorial == 1 /*|| isLastTutorial == 2*/) {
				if (CGRect.containsPoint(m_spHero.getBoundingBox(), CGPoint
						.make((float) convertedLocation.x,
								(float) convertedLocation.y))){
						isStart = false;
						touchState = 1;
						isLastTutorial++;
					}
			} else if (isLastTutorial == 2) {
				AppSettings.isTutoMsgShown = false;
				isLastTutorial++;
				quitTutorial();
			}
			return true;
		}
		touchState = 0;
		CGRect rectHero = CGRect.make(
			m_spHero.getPosition().x - m_spHero.getContentSize().width/ 2,
			m_spHero.getPosition().y - m_spHero.getContentSize().height/ 2, 
			m_spHero.getContentSize().width,
			m_spHero.getContentSize().height);

		if (CGRect.containsPoint(rectHero, CGPoint.make(
					(float) convertedLocation.x, (float) convertedLocation.y))) {
			touchState = 1;
		} else if (AppSettings.boxState > 0) {
			CGRect rectBox = CGRect.make(
				m_spBox.getPosition().x- m_spBox.getContentSize().width / 2,
						m_spBox.getPosition().y- m_spBox.getContentSize().height / 2,
						m_spBox.getContentSize().width,
						m_spBox.getContentSize().height);
			if (CGRect.containsPoint(rectBox, CGPoint.make(
						(float) convertedLocation.x,
						(float) convertedLocation.y))) {
				isBoxPick  = true;
				touchState = 2;

				m_spMoveBox = CCSprite.sprite(G._getImg("btn_buy_box"));
				G.setScale(m_spMoveBox);
				m_spMoveBox.setPosition((float) convertedLocation.x,
					(float) convertedLocation.y);
				addChild(m_spMoveBox);
			}
		}
		for (int i = 0; i < arrayBox.size(); i++) {
			boxObject box = (boxObject) arrayBox.get(i);
			if (CGRect.containsPoint(box.getBoundingBox(), CGPoint.make(
					(float) convertedLocation.x,
					(float) convertedLocation.y))) {
				removeChild(box, true);
				arrayBox.remove(box);
				box.removeSelf();
				touchState = 3;
				oldX = (float) convertedLocation.x;
				oldY = (float) convertedLocation.y;
				m_spMoveBox = CCSprite.sprite(G._getImg("btn_buy_box"));
				G.setScale(m_spMoveBox);
				m_spMoveBox.setPosition((float) convertedLocation.x,
							(float) convertedLocation.y);
				addChild(m_spMoveBox);
				break;
			}
		}

		if (isStart && touchState == 0) {
			if (bullet == 0) {
				AppSettings.playEffect(AppSettings.kCockgun2);
				return true;
			}
			int idSound = 0;
			if (gunState == 1) {
				idSound = AppSettings.kPistol;
			} else if (gunState == 2) {
				idSound = AppSettings.kShotgun;
				AppSettings.playEffect(idSound);
				createBullet((int) convertedLocation.x,
						(int) convertedLocation.y);
				float upperY = (int) convertedLocation.y - ccMacros.CC_DEGREES_TO_RADIANS(4)*convertedLocation.x;
				float lowerY = (int) convertedLocation.y + ccMacros.CC_DEGREES_TO_RADIANS(4)*convertedLocation.x;
				createBullet((int) convertedLocation.x,
						(int) upperY);
				//convertedLocation.y = -ccMacros.CC_DEGREES_TO_RADIANS(5);
				createBullet((int) convertedLocation.x,
						(int) lowerY);
				if (bullet > 0) {
					bullet--;
					m_btnReload.setVisible(true);
				}
				isFire = true;
				return true;
			} else if (gunState == 3) {
				isRifle = true;
				unschedule("tick1");
				schedule("tick1");
				return true;
			} else if (gunState == 4) {
				idSound = AppSettings.kSniper;
			}
			AppSettings.playEffect(idSound);
			createBullet((int) convertedLocation.x,
				(int) convertedLocation.y);
			if (bullet > 0) {
				bullet--;
				m_btnReload.setVisible(true);
			}
			isFire = true;
		}
	}
	return true;
}

int arrangeZombiePos(zombieObject obj1, zombieObject obj2) {
		String compare_str1 = String.format("%d", obj1.y);
		String compare_str2 = String.format("%d", obj2.y);
		return compare_str1.compareToIgnoreCase(compare_str2);
	}

}