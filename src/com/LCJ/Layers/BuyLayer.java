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
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCLabel.TextAlignment;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.Others.BsButton;

public class BuyLayer extends CCLayer {
	public static CCScene scene() {
		CCScene s = CCScene.node();
		s.addChild(new BuyLayer());
		return s;
	}
	
	String  		strBack = "";
	CCSprite 		mBuyState;
	CCLabel 		labelBox;
	CCLabel 		labelBox_shadow;
	BsButton 		btnBuy;
	
	CCLabel         labelDamage;
    CCLabel         labelFireRate;
    CCLabel         labelCost;
    CCLabel         labelDefence;
    CCLabel         labelCost1;
	
	protected BuyLayer() {
		super();
		isTouchEnabled_ = true;
		
		if (AppSettings.buyType == 0) {
	        strBack = "buy_pistol_bg";
	    } else if (AppSettings.buyType == 1) {
	        strBack = "buy_shotgun_bg";
	    } else if (AppSettings.buyType == 2) {
	        strBack = "buy_rifle_bg";
	    } else if (AppSettings.buyType == 3) {
	        strBack = "buy_sniper_bg";
	    } else if (AppSettings.buyType == 4) {
	        strBack = "buy_box_bg";
	    } else if (AppSettings.buyType == 10) {
	        strBack = "shelt1buy";
	    } else if (AppSettings.buyType == 11) {
	        strBack = "shelt2buy";
	    } else if (AppSettings.buyType == 12) {
	        strBack = "shelt3buy";
	    } else if (AppSettings.buyType == 13) {
	        strBack = "shelt4buy";
	    }
		
		CCSprite bgSp = CCSprite.sprite(G._getImg(strBack));
		G.setScale(bgSp);
		bgSp.setPosition(CGPoint.ccp(G._getX(G.DEFAULT_W / 2),
				G._getY(G.DEFAULT_H / 2)));
		addChild(bgSp);
		
		mBuyState = CCSprite.sprite(G._getImg("buy_already"));
		G.setScale(mBuyState);
		if(G.hpdi == true)
			mBuyState.setPosition(CGPoint.ccp(G._getX(260), G._getY(50)));
		else
			mBuyState.setPosition(CGPoint.ccp(G._getX(130), G._getY(25)));
		addChild(mBuyState);
		
		btnBuy = BsButton.button(G._getImg("btn_buy_nor"),
				G._getImg("btn_buy_nor"), this, "actionBuy");
		if(G.hpdi == true)
			btnBuy.setPosition(CGPoint.ccp(G._getX(780), G._getY(470)));
		else
			btnBuy.setPosition(CGPoint.ccp(G._getX(390), G._getY(235)));
		addChild(btnBuy);
		
		BsButton btnBack = BsButton.button(G._getImg("btn_back3_nor"),
				G._getImg("btn_back3_dow"), this, "actionBack");
		if(G.hpdi == true)
			btnBack.setPosition(CGPoint.ccp(G._getX(860), G._getY(50)));
		else
			btnBack.setPosition(CGPoint.ccp(G._getX(430), G._getY(25)));
		addChild(btnBack);

		if (AppSettings.buyType == 0) {
			mBuyState.setVisible(true);
			//btnBuy.setVisible(false);
	    } else if (AppSettings.buyType == 1) {
	        if ((AppSettings.gunState / 10)%10 == 1) {
	        	mBuyState.setVisible(true);
	        } else {
	        	mBuyState.setVisible(false);
	        }
	    } else if (AppSettings.buyType == 2) {
	        if ((AppSettings.gunState / 100)%10 == 1) {
	        	mBuyState.setVisible(true);
	        } else {
	        	mBuyState.setVisible(false);
	        }
	    } else if (AppSettings.buyType == 3) {
	        if ((AppSettings.gunState / 1000)%10 == 1) {
	        	mBuyState.setVisible(true);
	        } else {
	        	mBuyState.setVisible(false);
	        }
	    } else if (AppSettings.buyType == 4) {
	    	mBuyState.setVisible(false);
	    }
		
		if (AppSettings.buyType == 10) {
			if(AppSettings.wallState1 == 30){
				mBuyState.setVisible(true);	
			} else{
				mBuyState.setVisible(false);
			}
	        
	    } else if (AppSettings.buyType == 11) {
	        if (AppSettings.wallState2 == 40) {
	            mBuyState.setVisible(true);
	        } else {
	            mBuyState.setVisible(false);
	        }
	    } else if (AppSettings.buyType == 12) {
	        if (AppSettings.wallState3 == 60) {
	            mBuyState.setVisible(true);
	        } else {
	            mBuyState.setVisible(false);
	        }
	    } else if (AppSettings.buyType == 13) {
	        if (AppSettings.wallState4 == 100) {
	            mBuyState.setVisible(true);
	        } else {
	            mBuyState.setVisible(false);
	        }
	    }
		
		if (G.hpdi == true){
			labelBox_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 92);
			G.setScale(labelBox_shadow);
			labelBox_shadow.setPosition(CGPoint.ccp(G._getX(230), G._getY(370)));
		}else{
			labelBox_shadow = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 46);
			G.setScale(labelBox_shadow);
			labelBox_shadow.setPosition(CGPoint.ccp(G._getX(115), G._getY(185)));
		}
		
		labelBox_shadow.setColor(ccColor3B.ccRED);
		addChild(labelBox_shadow);
		labelBox_shadow.setVisible(false);
		
		if (G.hpdi == true){
			labelBox  = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
			CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
			TextAlignment.CENTER, G._getFont("Rocky"), 90);
			G.setScale(labelBox);
			labelBox.setPosition(CGPoint.ccp(G._getX(230), G._getY(370)));
		}else{
			labelBox = CCLabel.makeLabel(String.format("%d", AppSettings.boxState), 
				CGSize.make(G.DEFAULT_W, G.DEFAULT_W),
				TextAlignment.CENTER, G._getFont("Rocky"), 45);
			G.setScale(labelBox);
			labelBox.setPosition(CGPoint.ccp(G._getX(115), G._getY(185)));
		}
		
		labelBox.setColor(ccColor3B.ccBLACK);
		addChild(labelBox);
		labelBox.setVisible(false);

		if (AppSettings.buyType == 4){ 
			addChild(labelBox_shadow);
			addChild(labelBox);
			labelBox_shadow.setVisible(true);
			labelBox.setVisible(true);
		}
		if (mBuyState.getVisible() == true) {
			btnBuy.setVisible(false);
	    } else {
	    	btnBuy.setVisible(true);
	    }
		
		createLabels();
	}

	void createLabels()
	{
		String	strScore = "";
		if (AppSettings.buyType == 0 || AppSettings.buyType == 1 || AppSettings.buyType == 2 || AppSettings.buyType == 3)
		{
			int nDamage = AppSettings.getDamage(AppSettings.buyType+1, false);
			strScore = String.format("%d/30", nDamage);
			
			if (G.hpdi == true){
				labelDamage  = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 50);
				G.setScale(labelDamage);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelDamage.setPosition(CGPoint.ccp(G._getX(556), G._getY(256)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelDamage.setPosition(CGPoint.ccp(G._getX(556), G._getY(240)));
			}else{
				labelDamage = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 25);
				G.setScale(labelDamage);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelDamage.setPosition(CGPoint.ccp(G._getX(278), G._getY(128)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelDamage.setPosition(CGPoint.ccp(G._getX(278), G._getY(120)));
			}
			
			labelDamage.setColor(new ccColor3B(255, 202, 0));
			addChild(labelDamage);
			
			if (G.hpdi == true){
				labelFireRate  = CCLabel.makeLabel(AppSettings.buyType == 2 ? "AUTOMATIC":"SEMI AUTOMATIC", 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 40);
				G.setScale(labelFireRate);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelFireRate.setPosition(CGPoint.ccp(G._getX(680), G._getY(192)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelFireRate.setPosition(CGPoint.ccp(G._getX(680), G._getY(160)));
			}else{
				labelFireRate = CCLabel.makeLabel(AppSettings.buyType == 2 ? "AUTOMATIC":"SEMI AUTOMATIC", 
						CGSize.make(G._getX(520), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 20);
				G.setScale(labelFireRate);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelFireRate.setPosition(CGPoint.ccp(G._getX(355), G._getY(96)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelFireRate.setPosition(CGPoint.ccp(G._getX(355), G._getY(91)));
			}
			
			labelFireRate.setColor(new ccColor3B(255, 202, 0));
			addChild(labelFireRate);

			if (AppSettings.buyType == 0) {
				strScore = String.format("%d", G.MONEY_GUN1);
			} else if(AppSettings.buyType == 1) {
				strScore = String.format("%d", G.MONEY_GUN2);
			} else if(AppSettings.buyType == 2) {
				strScore = String.format("%d", G.MONEY_GUN3);
			} else if(AppSettings.buyType == 3) {
				strScore = String.format("%d", G.MONEY_GUN4);
			}

			if (G.hpdi == true){
				labelCost  = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 50);
				G.setScale(labelCost);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelCost.setPosition(CGPoint.ccp(G._getX(550), G._getY(132)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelCost.setPosition(CGPoint.ccp(G._getX(550), G._getY(120)));
			}else{
				labelCost = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 25);
				G.setScale(labelCost);
				if (AppSettings.buyType == 0 || AppSettings.buyType == 1)
					labelCost.setPosition(CGPoint.ccp(G._getX(275), G._getY(66)));
				else if (AppSettings.buyType == 2 || AppSettings.buyType == 3)
					labelCost.setPosition(CGPoint.ccp(G._getX(275), G._getY(60)));
			}
			
			labelCost.setColor(new ccColor3B(255, 202, 0));
			addChild(labelCost);
		} else {
			if (AppSettings.buyType == 4) {
				strScore = String.format("%d", 2);
			} else if(AppSettings.buyType == 10) {
				strScore = String.format("%d", G.WALL_HEALTH1/10);
			} else if(AppSettings.buyType == 11) {
				strScore = String.format("%d", G.WALL_HEALTH2/10);
			} else if(AppSettings.buyType == 12) {
				strScore = String.format("%d", G.WALL_HEALTH3/10);
			} else if(AppSettings.buyType == 13) {
				strScore = String.format("%d", G.WALL_HEALTH4/10);
			}
			
			if (G.hpdi == true){
				labelDefence  = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 50);
				G.setScale(labelDefence);
				labelDefence.setPosition(CGPoint.ccp(G._getX(700), G._getY(258)));
			}else{
				labelDefence = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 25);
				G.setScale(labelDefence);
//				labelDefence.setPosition(G._getX(450), G._getY(116));
				labelDefence.setPosition(CGPoint.ccp(G._getX(350), G._getY(129)));
			}
			labelDefence.setColor(new ccColor3B(255, 202, 0));
			addChild(labelDefence);

			if (AppSettings.buyType == 4) {
				strScore = String.format("$%d", G.MONEY_BOX);
			} else if(AppSettings.buyType == 10) {
				strScore = String.format("$%d", G.MONEY_SHELTER1);
			} else if(AppSettings.buyType == 11) {
				strScore = String.format("$%d", G.MONEY_SHELTER2);
			} else if(AppSettings.buyType == 12) {
				strScore = String.format("$%d", G.MONEY_SHELTER3);
			} else if(AppSettings.buyType == 13) {
				strScore = String.format("$%d", G.MONEY_SHELTER4);
			}
			
			if (G.hpdi == true){
				labelDefence  = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 50);
				G.setScale(labelDefence);
				labelDefence.setPosition(CGPoint.ccp(G._getX(700), G._getY(178)));
			}else{
				labelDefence = CCLabel.makeLabel(strScore, 
						CGSize.make(G._getX(420), G._getY(160)),
						TextAlignment.CENTER, G._getFont("FORTE"), 25);
				G.setScale(labelDefence);
//				labelDefence.setPosition(G._getX(450), G._getY(116));
				labelDefence.setPosition(CGPoint.ccp(G._getX(350), G._getY(89)));
			}
//			labelDefence.setAnchorPoint(0.5);
			labelDefence.setColor(new ccColor3B(255, 202, 0));
			addChild(labelDefence);
		}
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		mBuyState = null;
		labelBox = null;
		labelBox_shadow = null;
		btnBuy = null;
		
		System.gc();
		super.onExit();
	}

	public void actionBuy(Object sender) {
		if (AppSettings.buyType == 4) {
	            	AppSettings.boxState ++;
	            	AppSettings.money -= G.MONEY_BOX;
	                if (AppSettings.money < G.MONEY_BOX) {
	                	btnBuy.setVisible(false);
	                }
	                labelBox.setString(String.format("%d",AppSettings.boxState));
	                labelBox_shadow.setString(String.format("%d",AppSettings.boxState));
	    } else {
	        if (AppSettings.buyType == 0) {
	            
	        } else if (AppSettings.buyType == 1) {
	        	AppSettings.gunState += 10;
	        	AppSettings.money -= G.MONEY_GUN2;
	        	btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	            if(!AppSettings.isFirstBuyGun) firstSwap();
	        } else if (AppSettings.buyType == 2) {
	        	AppSettings.gunState += 100;
	        	AppSettings.money -= G.MONEY_GUN3;
	        	btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	            if(!AppSettings.isFirstBuyGun) firstSwap();
	        } else if (AppSettings.buyType == 3) {
	        	AppSettings.gunState += 1000;
	        	AppSettings.money -= G.MONEY_GUN4;
	        	btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	            if(!AppSettings.isFirstBuyGun) firstSwap();
	        }
	        if (AppSettings.buyType == 10) {
	        	AppSettings.wallState1 = 30;
	        	AppSettings.money -= G.MONEY_SHELTER1;
	            btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	        } else if (AppSettings.buyType == 11) {
	        	AppSettings.wallState2 = 40;
	        	AppSettings.money -= G.MONEY_SHELTER2;
	            btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	        } else if (AppSettings.buyType == 12) {
	        	AppSettings.wallState3 = 60;
	        	AppSettings.money -= G.MONEY_SHELTER3;
	            btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	        } else if (AppSettings.buyType == 13) {
	        	AppSettings.wallState4 = 100;
	        	AppSettings.money -= G.MONEY_SHELTER4;
	            btnBuy.setVisible(false);
	            mBuyState.setVisible(true);
	        }
	        if (!AppSettings.isSurvival)
	        	AppSettings.SaveLevelInfo();
	    }
	}
	public void firstSwap()
	{
		if (!AppSettings.isStory) {
			return;
		}
		
		AppSettings.isFirstBuyGun = true;
		AppSettings.SaveFirstBuyInfo();
		AppSettings.isSwapGun = true;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, GamePlayLayer.scene(),
						ccColor3B.ccBLACK));
	}
	public void actionBack(Object sender) {
		AppSettings.isTutoMsgShown = false;
		CCDirector.sharedDirector().replaceScene(
				CCFadeTransition.transition(0.6f, PropertyLayer.scene(),
						ccColor3B.ccBLACK));
	}
}