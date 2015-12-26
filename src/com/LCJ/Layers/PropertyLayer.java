package com.LCJ.Layers;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCLabel.TextAlignment;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.Others.BsButton;

public class PropertyLayer extends CCLayer {
	public static CCScene scene() {
		CCScene s = CCScene.node();
		s.addChild(new PropertyLayer());
		return s;
	}
	
	int 				realMoney = 0;
	
	CCSprite			m_spWallState1;
	CCSprite			m_spWallState2;
	CCSprite			m_spWallState3;
	CCSprite			m_spWallState4;
	CCSprite			m_spCanBuy;
	
	CCLabel          	labelHelp;
	CCLabel          	labelMoney_shadow;
	CCLabel          	labelI;
	CCLabel          	labelI_sh;
	CCLabel          	labelS;
	CCLabel          	labelS_shadow;
	CCLabel          	labelMoney;
	CCLabel          	labelTime;
	CCLayer       		layer_force;
	BsButton 			btnPistol ;
	BsButton 			btnShotGun;
	BsButton 			btnBox;
	
	CCSprite			m_spAleadyBuy;
	int					alertState;
	
	protected PropertyLayer() {
		super();
		isTouchEnabled_ = true;
		CCSprite bgSp = CCSprite.sprite(G._getImg("buy_menu_bg"));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);
		
		m_spCanBuy = CCSprite.sprite(G._getImg("cannot"));
		G.setScale(m_spCanBuy);
		m_spCanBuy.setPosition(G._getX(G.DEFAULT_W/2),G._getY(G.DEFAULT_H/2));
		addChild(m_spCanBuy);
		m_spCanBuy.setVisible(false);
		
		m_spAleadyBuy = CCSprite.sprite(G._getImg("already_buy"));
		G.setScale(m_spAleadyBuy);
		m_spAleadyBuy.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(m_spAleadyBuy);
		m_spAleadyBuy.setVisible(false);
		//the gun list
		btnPistol = BsButton.button(G._getImg("btn_pistol_nor"),
				G._getImg("btn_pistol_dow"), this, "actionBuyGun1");
		if(G.hpdi == true)
			btnPistol.setPosition(CGPoint.ccp(G._getX(200), G._getY(450)));
		else
			btnPistol.setPosition(CGPoint.ccp(G._getX(100), G._getY(225)));
		addChild(btnPistol);

		btnShotGun = BsButton.button(G._getImg("btn_shotgun_nor"),
				G._getImg("btn_shotgun_dow"), this, "actionBuyGun2");
		if(G.hpdi == true)
			btnShotGun.setPosition(CGPoint.ccp(G._getX(200), G._getY(350)));
		else
			btnShotGun.setPosition(CGPoint.ccp(G._getX(100), G._getY(175)));
		addChild(btnShotGun);

		BsButton btnRefle = BsButton.button(G._getImg("btn_refle_nor"),
				G._getImg("btn_refle_dow"), this, "actionBuyGun3");
		if(G.hpdi == true)
			btnRefle.setPosition(CGPoint.ccp(G._getX(200),	G._getY(250)));
		else
			btnRefle.setPosition(CGPoint.ccp(G._getX(100),	G._getY(125)));
		addChild(btnRefle);
		
		BsButton btnSniper = BsButton.button(G._getImg("btn_sniper_nor"),
				G._getImg("btn_sniper_dow"), this, "actionBuyGun4");
		if(G.hpdi == true)
			btnSniper.setPosition(CGPoint.ccp(G._getX(200),	G._getY(150)));
		else
			btnSniper.setPosition(CGPoint.ccp(G._getX(100),	G._getY(75)));
		addChild(btnSniper);
		
		//Box
		btnBox = BsButton.button(G._getImg("btn_buy_box"),
				G._getImg("btn_buy_box"), this, "actionBuyBox");
		G.setScale(btnBox);
		if(G.hpdi == true)
			btnBox.setPosition(CGPoint.ccp(G._getX(800), G._getY(600)));
		else
			btnBox.setPosition(G._getX(400), G._getY(300));
		addChild(btnBox);
		
		if(G.hpdi == true){
			CCLabel labelBox_shadow = CCLabel.makeLabel(
					String.format("%d", AppSettings.boxState),
					CGSize.make(160*G._scaleX, 100*G._scaleY),
					TextAlignment.CENTER, G._getFont("Rocky"), 65*G._scaleX);
				G.setScale(labelBox_shadow);
				labelBox_shadow.setPosition(G._getX(558), G._getY(480));
				labelBox_shadow.setColor(ccColor3B.ccRED);
				labelBox_shadow.setOpacityModifyRGB(false);
			addChild(labelBox_shadow);
			labelBox_shadow.setScale(1.55f);
		}else{
			CCLabel labelBox_shadow = CCLabel.makeLabel(
					String.format("%d", AppSettings.boxState),
					CGSize.make(160*G._scaleX, 100*G._scaleY),
					TextAlignment.CENTER, G._getFont("Rocky"), 36*G._scaleX);
				G.setScale(labelBox_shadow);
				labelBox_shadow.setPosition(G._getX(279), G._getY(240));
				labelBox_shadow.setColor(ccColor3B.ccRED);
				labelBox_shadow.setOpacityModifyRGB(false);
				labelBox_shadow.setScale(1.5f);
			addChild(labelBox_shadow);
		}
		
		if(G.hpdi == true){
			CCLabel labelBox = CCLabel.makeLabel(
					String.format("%d", AppSettings.boxState),
					CGSize.make(160*G._scaleX, 100*G._scaleY),
					TextAlignment.CENTER, G._getFont("Rocky"), 65*G._scaleX);
				G.setScale(labelBox);
			labelBox.setPosition(G._getX(558), G._getY(480));
			labelBox.setColor(ccColor3B.ccBLACK);
			labelBox.setOpacityModifyRGB(false);
			addChild(labelBox);
			labelBox.setScale(1.5f);
		}else{
			CCLabel labelBox = CCLabel.makeLabel(
					String.format("%d", AppSettings.boxState),
					CGSize.make(160*G._scaleX, 100*G._scaleY),
					TextAlignment.CENTER, G._getFont("Rocky"), 35*G._scaleX);
				G.setScale(labelBox);
			labelBox.setPosition(G._getX(279), G._getY(240));
			labelBox.setColor(ccColor3B.ccBLACK);
			labelBox.setOpacityModifyRGB(false);
			labelBox.setScale(1.5f);
			addChild(labelBox);
		}
		
		btnBox.setScale(1.5f);
		
		//back 
		BsButton btnBack = BsButton.button(G._getImg("btn_back3_nor"),
				G._getImg("btn_back3_dow"), this, "actionBack");
		if(G.hpdi == true)
			btnBack.setPosition(CGPoint.ccp(G._getX(100), G._getY(50)));
		else
			btnBack.setPosition(CGPoint.ccp(G._getX(50), G._getY(25)));
		addChild(btnBack);
		
		//money
		if (G.hpdi == true){
			labelMoney = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 72);
			G.setScale(labelMoney);
			labelMoney.setPosition(CGPoint.ccp(G._getX(850), G._getY(450)));
		}else{
			labelMoney = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 36);
			G.setScale(labelMoney);
			labelMoney.setPosition(CGPoint.ccp(G._getX(425), G._getY(225)));
		}
		
		labelMoney.setColor(ccColor3B.ccRED);
		addChild(labelMoney);
		//labelMoney.setVisible(false);
		
		if (G.hpdi == true){
			labelMoney_shadow  = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 68);
			G.setScale(labelMoney_shadow);
			labelMoney_shadow.setPosition(CGPoint.ccp(G._getX(850), G._getY(450)));
		}else{
			labelMoney_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.money), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 34);
			G.setScale(labelMoney_shadow);
			labelMoney_shadow.setPosition(CGPoint.ccp(G._getX(425), G._getY(225)));
		}
		
		labelMoney_shadow.setColor(new ccColor3B(8, 139, 8));
		addChild(labelMoney_shadow);
			
		//$
		if (G.hpdi == true){
			labelS = CCLabel.makeLabel("1", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky1"), 64);
			G.setScale(labelS);
			labelS.setPosition(CGPoint.ccp(G._getX(742), G._getY(448)));
		}else{
			labelS = CCLabel.makeLabel("1", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky1"), 32);
			G.setScale(labelS);
			labelS.setPosition(CGPoint.ccp(G._getX(371), G._getY(223)));
		}
		
		labelS.setColor(new ccColor3B(8, 139, 8));
		addChild(labelS);
		
		if (G.hpdi == true){
			labelI = CCLabel.makeLabel("S", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 64);
			G.setScale(labelI);
			labelI.setPosition(CGPoint.ccp(G._getX(742), G._getY(448)));
		}else{
			labelI = CCLabel.makeLabel("S", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 32);
			G.setScale(labelI);
			labelI.setPosition(CGPoint.ccp(G._getX(371), G._getY(223)));
		}
		
		labelI.setColor(ccColor3B.ccRED);
		addChild(labelI);
		
		if (G.hpdi == true){
			labelI_sh  = CCLabel.makeLabel("S", 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 60);
			G.setScale(labelI_sh);
			labelI_sh.setPosition(CGPoint.ccp(G._getX(742), G._getY(448)));
		}else{
			labelI_sh = CCLabel.makeLabel("S", 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 30);
			G.setScale(labelI_sh);
			labelI_sh.setPosition(CGPoint.ccp(G._getX(371), G._getY(223)));
		}
		
		labelI_sh.setColor(new ccColor3B(8, 139, 8));
		addChild(labelI_sh);
		
			
		//the shelter list
		BsButton btnShelter1 = BsButton.button(G._getImg("shelt1menu"),
				G._getImg("shelt1menu"), this, "actionBuyShelter1");
		if(G.hpdi == true)
			btnShelter1.setPosition(CGPoint.ccp(G._getX(500), G._getY(170)));
		else
			btnShelter1.setPosition(CGPoint.ccp(G._getX(250), G._getY(95)));
		addChild(btnShelter1);
		
		BsButton btnShelter2 = BsButton.button(G._getImg("shelt2menu"),
				G._getImg("shelt2menu"), this, "actionBuyShelter2");
		if(G.hpdi == true)
			btnShelter2.setPosition(CGPoint.ccp(G._getX(620), G._getY(170)));
		else
			btnShelter2.setPosition(CGPoint.ccp(G._getX(310), G._getY(95)));
		addChild(btnShelter2);
		
		BsButton btnShelter3 = BsButton.button(G._getImg("shelt3menu"),
				G._getImg("shelt3menu"), this, "actionBuyShelter3");
		if(G.hpdi == true)
			btnShelter3.setPosition(CGPoint.ccp(G._getX(740), G._getY(170)));
		else
			btnShelter3.setPosition(CGPoint.ccp(G._getX(370), G._getY(95)));
		addChild(btnShelter3);
		
		BsButton btnShelter4 = BsButton.button(G._getImg("shelt4menu"),
				G._getImg("shelt4menu"), this, "actionBuyShelter4");
		if(G.hpdi == true)
			btnShelter4.setPosition(CGPoint.ccp(G._getX(860), G._getY(170)));
		else
			btnShelter4.setPosition(CGPoint.ccp(G._getX(430), G._getY(95)));
		addChild(btnShelter4);
		
		m_spWallState1 = CCSprite.sprite(G._getImg("bar1"));
		G.setScale(m_spWallState1);
		if(G.hpdi == true)
			m_spWallState1.setPosition(CGPoint.ccp(G._getX(450),
					G._getY(60)));
		else	
			m_spWallState1.setPosition(CGPoint.ccp(G._getX(225),
					G._getY(40)));
		m_spWallState1.setAnchorPoint(0, 0);
		addChild(m_spWallState1);
		
		m_spWallState2 = CCSprite.sprite(G._getImg("bar2"));
		G.setScale(m_spWallState2);
		if(G.hpdi == true)
			m_spWallState2.setPosition(CGPoint.ccp(G._getX(570),
					G._getY(60)));
		else
			m_spWallState2.setPosition(CGPoint.ccp(G._getX(285),
				G._getY(40)));
		m_spWallState2.setAnchorPoint(0, 0);
		addChild(m_spWallState2);
		
		m_spWallState3 = CCSprite.sprite(G._getImg("bar3"));
		G.setScale(m_spWallState3);
		if(G.hpdi == true)
			m_spWallState3.setPosition(CGPoint.ccp(G._getX(690),
					G._getY(60)));
		else
			m_spWallState3.setPosition(CGPoint.ccp(G._getX(345),
					G._getY(40)));
		m_spWallState3.setAnchorPoint(0, 0);
		addChild(m_spWallState3);
		
		m_spWallState4 = CCSprite.sprite(G._getImg("bar4"));
		G.setScale(m_spWallState4);
		if(G.hpdi == true)
			m_spWallState4.setPosition(CGPoint.ccp(G._getX(810),
					G._getY(60)));
		else
			m_spWallState4.setPosition(CGPoint.ccp(G._getX(405),
					G._getY(40)));
		m_spWallState4.setAnchorPoint(0, 0);
		addChild(m_spWallState4);
		
		if (AppSettings.isStory) {
			int nTotalSecond = AppSettings.totalTime / 60;
			int nHour = nTotalSecond / 3600;
			int nMinute = (nTotalSecond % 3600) / 60;
			int nSecond = nTotalSecond % 60;
			
			if (G.hpdi == true){
				labelTime  = CCLabel.makeLabel(String.format("%02d:%02d:%02d", nHour, nMinute, nSecond), 
				CGSize.make(G._getX(400), G._getY(100)),
				TextAlignment.CENTER, G._getFont("Rocky"), 60);
				G.setScale(labelTime);
				labelTime.setPosition(CGPoint.ccp(G._getX(750), G._getY(30)));
			}else{
				labelTime = CCLabel.makeLabel(String.format("%02d:%02d:%02d", nHour, nMinute, nSecond), 
					CGSize.make(G._getX(400), G._getY(100)),
					TextAlignment.CENTER, G._getFont("Rocky"), 30);
				G.setScale(labelTime);
				labelTime.setPosition(CGPoint.ccp(G._getX(375), G._getY(15)));
			}
			
			labelTime.setColor(new ccColor3B(8, 139, 8));
			addChild(labelTime);
		}
		
		setWallHealth();
		
		layer_force = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 255), G._scaleX*G.DEFAULT_W, G._scaleY*G.DEFAULT_H);
		layer_force.setPosition(G._getX(G.DEFAULT_W/2),G._getY(G.DEFAULT_H/2));

		layer_force.setRelativeAnchorPoint(true);
		((CCColorLayer) layer_force).setOpacity(150);
		addChild( layer_force);
		layer_force.setVisible(false);
		
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		m_spWallState1 = null;
		m_spWallState2 = null;
		m_spWallState3 = null;
		m_spWallState4 = null;
		m_spCanBuy = null;
		
		labelHelp = null;
		labelMoney_shadow = null;
		labelI = null;
		labelI_sh = null;
		labelS = null;
		labelS_shadow = null;
		labelMoney = null;
		layer_force = null;
		btnPistol = null;
		btnShotGun = null;
		btnBox = null;
		
		System.gc();
		super.onExit();
	}



	public void cannot_buy()
	{
		m_spCanBuy.setVisible(false);
	}
	
	public void cannotBuy()
	{
		m_spCanBuy.setVisible(true);
		CCDelayTime delay = CCDelayTime.action(2);
		CCCallFunc nextaction = CCCallFunc.action(this, "cannot_buy");
		CCSequence animateSeq = CCSequence.actions(delay, nextaction);
		runAction(animateSeq);	
	}
	
	public void setWallHealth()
	{
	    int maxHealthWidth = 100;
	    
	    if(G.hpdi ==true)
	    	maxHealthWidth = 100;
	    else 
	    	maxHealthWidth = 50;
	    int maxHealth = 30;
	    CGRect rect = m_spWallState1.getTextureRect();
	    rect.size.width = (float)maxHealthWidth * (float)AppSettings.wallState1 / (float)maxHealth;
	    m_spWallState1.setTextureRect(rect);
	    
	    maxHealth = 40;
	    rect = m_spWallState2.getTextureRect();
	    rect.size.width = (float)maxHealthWidth * (float)AppSettings.wallState2 / (float)maxHealth;
	    m_spWallState2.setTextureRect(rect);
	    
	    maxHealth = 60;
	    rect = m_spWallState3.getTextureRect();
	    rect.size.width = (float)maxHealthWidth * (float)AppSettings.wallState3 / (float)maxHealth;
	    m_spWallState3.setTextureRect(rect);
	    
	    maxHealth = 100;
	    rect = m_spWallState4.getTextureRect();
	    rect.size.width = (float)maxHealthWidth * (float)AppSettings.wallState4 / (float)maxHealth;
	    m_spWallState4.setTextureRect(rect);
	}

	public void actionBuyGun1(Object sender) {
		AppSettings.buyType = 0;
		alertState = 2;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyGun2(Object sender) {
		if (AppSettings.money < G.MONEY_GUN2) {
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 1;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyGun3(Object sender) {
		if (AppSettings.money < G.MONEY_GUN3) {
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 2;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyGun4(Object sender) {
		if (AppSettings.money < G.MONEY_GUN4) {
			cannotBuy();
			return;
	    }
	    AppSettings.buyType = 3;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyBox(Object sender) {
		if (AppSettings.money < G.MONEY_BOX) {
			cannotBuy();
			return;
	    }
	    AppSettings.buyType = 4;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyShelter1(Object sender) {
		if (AppSettings.money < G.MONEY_SHELTER1) {
			alertState = 1;
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 10;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyShelter2(Object sender) {
		if (AppSettings.money < G.MONEY_SHELTER2) {
			alertState = 1;
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 11;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyShelter3(Object sender) {
		if (AppSettings.money < G.MONEY_SHELTER3) {
			alertState = 1;
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 12;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	
	public void actionBuyShelter4(Object sender) {
		if (AppSettings.money < G.MONEY_SHELTER4) {
			alertState = 1;
			cannotBuy();
			return;
	    }
		AppSettings.buyType = 13;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, BuyLayer.scene(),
						ccColor3B.ccBLACK));
	}
	public void actionBack(Object sender) {
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(1.0f, PreplayLayer.scene(),
						ccColor3B.ccBLACK));
	}
}