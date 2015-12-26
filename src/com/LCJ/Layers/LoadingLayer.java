/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package com.LCJ.Layers;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

import com.LCJ.ZombiDefense.G;

public class LoadingLayer extends CCLayer 
{
	private long tck = 0;
	
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(LoadingLayer.node());
		return scene;
	}

	public static LoadingLayer node()
	{
		LoadingLayer self = new LoadingLayer();
		CCSprite bgSp = CCSprite.sprite(G._getImg("logo_splash"));
		G.setScale(bgSp);
		bgSp.setPosition(G._getX(G.DEFAULT_W / 2), G._getY(G.DEFAULT_H / 2));
		self.addChild(bgSp);
		return self;
	}
			
	@Override public void draw(GL10 gl) {
		if (tck++>100) 
			CCDirector.sharedDirector().replaceScene(StartLayer.scene());	
	}
} 