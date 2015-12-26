/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package com.LCJ.ZombiDefense;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;


import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {
	public static boolean	isFirstStory = false;
	public static boolean	isStory = false;
	public static boolean   isSurvival = false;
	public static boolean   isFirstLevel = false;
	public static boolean	sound = true;
	public static int		level = 0;;
	public static int		stage = 0;
	public static int		gunState = 0;
	public static int     	wallState1 = 0;
	public static int     	wallState2 = 0;
	public static int     	wallState3 = 0;
	public static int     	wallState4 = 0;
	public static int		boxState = 0;
	public static int		buyType = 0;
	public static int		money = 0;
	public static float	    animationInterval = 60;
	public static int		totalTime = 0;

	public static int       savedTime = 0;
	public static int		savedLevel = 0;
	public static int		savedStage = 0;
	public static int		savedMoney = 0;
	public static int		savedGunState = 0;
	public static int     	savedWallState1 = 0;
	public static int     	savedWallState2 = 0;
	public static int     	savedWallState3 = 0;
	public static int     	savedWallState4 = 0;
	public static int		savedBoxState = 0;
	public static boolean   savedSound = false;
	public static int		trainPrePlay = 0;
	public static boolean   isTutorialEnd = false; 
	public static boolean   isTutoMsgShown = false;
	public static boolean   isFirstBuyGun;
	public static boolean   isSwapGun;
	public static boolean   isFirstPlay;

	public static void init()
	{
		isFirstStory = false;
		sound		= true;
		level		= 0;
	    stage       = 0;
		money		= 0;
		animationInterval	= 60;
	    isStory    = false;
	    isSurvival  = false;
	    gunState    = 0;
	    wallState1   = 30;
        wallState2   = 0;
        wallState3   = 0;
        wallState4   = 0;
	    boxState    = 0;
	    trainPrePlay = 0;
	    totalTime	= 0;
	    isTutorialEnd = false;
	    isFirstBuyGun = false;
	    isSwapGun = false;
	}
	
	public static void InitGameInfo()
	{
		SharedPreferences setting = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE);
		int Level1  = setting.getInt("savedLevel", 0);
		
		if( Level1 == 0) {
	        savedBoxState = 0;
	        savedMoney = 0;
	        savedLevel = 0;
	        savedStage = 0;
	        savedGunState = 0;
	        savedWallState1 = 0;
	        savedWallState2 = 0;
	        savedWallState3 = 0;
	        savedWallState4 = 0;
	        savedTime = 0;
	        savedSound = true;
	        isFirstBuyGun = false;
	        isFirstPlay = true;
	        
		}
		else {
			isFirstPlay = false;
			isFirstBuyGun =  setting.getBoolean("isFirstBuyGun", false);
			savedBoxState  = setting.getInt("savedBoxState", 0);
	        savedMoney  = setting.getInt("savedMoney", 0);
	        savedLevel  = setting.getInt("savedLevel", 0);
	        savedStage  = setting.getInt("savedStage", 0);
	        savedGunState  = setting.getInt("savedGunState", 0);
	        savedWallState1   = setting.getInt("savedWallState1", 0);
	        savedWallState2   = setting.getInt("savedWallState2", 0);
	        savedWallState3   = setting.getInt("savedWallState3", 0);
	        savedWallState4   = setting.getInt("savedWallState4", 0);
	        savedTime   = setting.getInt("savedTime", 0);
		}
	}

	public static void SaveSoundInfo(){
		
	}
	
	public static void SaveGameInfo()
	{
		SharedPreferences.Editor editor = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE).edit();
		editor.putInt("savedBoxState", savedBoxState);
		editor.putInt("savedMoney", savedMoney);
		editor.putInt("savedLevel", savedLevel);
		editor.putInt("savedStage", savedStage);
		editor.putInt("savedGunState", savedGunState);
		editor.putInt("savedWallState1", savedWallState1);
		editor.putInt("savedWallState2", savedWallState2);
		editor.putInt("savedWallState3", savedWallState3);
		editor.putInt("savedWallState4", savedWallState4);
		editor.putBoolean("savedSound", savedSound);
		editor.putBoolean("savedSound", savedSound);
		editor.putInt("savedTime", savedTime);
		editor.commit();
	}
	
	public static void SaveLevelInfo()
	{
		if(isSurvival)
			return;
		
		savedLevel = level;
	    savedStage = stage;
	    savedMoney = money;
	    savedGunState = gunState;
	    savedWallState1 = wallState1;
	    savedWallState2 = wallState2;
	    savedWallState3 = wallState3;
	    savedWallState4 = wallState4;
	    savedBoxState = boxState;
	    savedSound = sound;
	    savedTime = totalTime;
	    SaveGameInfo();
	}
	public static void LevelUp()
	{
		level = 1;  //please modify here for level up
	    stage ++;
	    if (stage > 4) {
	        stage = 1;
	    }
	    isFirstStory = true;
	    
//	    money = 100;
//	    gunState = 1;
//	    wallState1 = 30;
//	    wallState2 = 0;
//	    wallState3 = 0;
//	    wallState4 = 0;
//	    //wallHealth = 1;
//	    boxState = 0;
	    isFirstLevel = true;
	    SaveLevelInfo();
	}

	public static void SaveFirstBuyInfo()
	{
		SharedPreferences.Editor editor = 
			CCDirector.sharedDirector().getActivity().
			getSharedPreferences("ZombiDefense", Context.MODE_PRIVATE).edit();
		editor.putBoolean("isFirstBuyGun", isFirstBuyGun);
		editor.commit();
	}
	
	public static void InitLevelInfo()
	{
		level = 1;
	    stage = 1;
	    money = 1000/*tutorial ? 370 : 100*/;
	    totalTime = 0;
	    gunState = 1;
	    wallState1 = 30;
	    wallState2 = 0;
	    wallState3 = 0;
	    wallState4 = 0;
	    boxState = 0;	
	    isFirstBuyGun = false;
	}
	
	public static void LoadLevelInfo()
	{
		level = savedLevel;
	    stage = savedStage;
	    money = savedMoney;
	    gunState = savedGunState;
	    wallState1 = savedWallState1;
	    wallState2 = savedWallState2;
	    wallState3 = savedWallState3;
	    wallState4 = savedWallState4;
	    boxState = savedBoxState;
	    totalTime = savedTime;
	}
	
	public static int getDamage(int type, boolean isLong)
	{
		int ret = 0;
		if (type == 1) {
			if (stage == 1) {
				ret = 10-level/2;
			} else if (stage == 2) {
				ret = 10-level/2;
			} else if (stage == 3) {
				ret = 8-level/2;
			} else if (stage == 4) {
				ret = 7-level/2;
			}
		} else if (type == 2) {
			if (stage == 1) {
				ret = isLong ? 3:6;
			} else if (stage == 2) {
				ret = isLong ? 2:5;
			} else if (stage == 3) {
				ret = isLong ? 2:4;
			} else if (stage == 4) {
				ret = isLong ? 1:3;
			}
		} else if (type == 3) {
			if (stage == 1) {
				ret = isLong ? 5:10;
			} else if (stage == 2) {
				ret = isLong ? 5:7;
			} else if (stage == 3) {
				ret = isLong ? 2:4;
			} else if (stage == 4) {
				ret = isLong ? 2:3;
			}
		} else if (type == 4) {
			if (stage == 1) {
				ret = isLong ? 30 : 30;
			} else if (stage == 2) {
				ret = isLong ? 30 : 30;
			} else if (stage == 3) {
				ret = isLong ? 15 : 15;
			} else if (stage == 4) {
				ret = isLong ? 15 : 15;
			}
		}
		return  ret;
	}

	public static boolean checkGun(int type)
	{
		boolean ret = false;
	    if (type == 1 && AppSettings.gunState == 1) {
	        ret = true;
	    } else if (type == 2 && (AppSettings.gunState / 10)%10 == 1) {
	        ret = true;
	    } else if (type == 3 && (AppSettings.gunState / 100)%10 == 1) {
	        ret = true;
	    } else if (type == 4 && (AppSettings.gunState / 1000)%10 == 1) {
	        ret = true;
	    }
		
		return ret;
	}
	
	//for Sound and Effect
	public static void playSound() {
		if(!sound) return;
		stopSound();
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), 
				R.raw.music, true);
	}

	public static void playEffect(int effID) {
		if(!sound) return;
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), effID);
	}
	
	public static void stopSound() {
		SoundEngine.sharedEngine().realesAllSounds();
	}
	
	public static void pauseSound() {
		SoundEngine.sharedEngine().pauseSound();
	}

	public static void resumeSound() {
		SoundEngine.sharedEngine().resumeSound();
	}

	//for EffectSound & Sound
	public static final int kMusic = R.raw.music;
	public static final int kCockgun1 =  R.raw.cockgun1;
	public static final int kCockgun2 = R.raw.cockgun2;
	public static final int kPistol = R.raw.pistol;
	public static final int kRifle = R.raw.rifle;
	public static final int kGameOver = R.raw.gameover;
	public static final int kShotgun 	= R.raw.shotgun;
	public static final int kSniper = R.raw.sniper;
	public static final int kZombie0 = R.raw.zombie1;
	public static final int kZombie1 = R.raw.zombie2;
	public static final int kZombie2 = R.raw.zombie3;
	public static final int kZombie3 = R.raw.zombie4;
	
	//for Game Type
	public static final int GAME_QUICK 	= 0;
	public static final int STAGE_1 	= 1;
	public static final int STAGE_2 	= 2;
	public static final int STAGE_3 	= 3;
	public static final int STAGE_4 	= 4;
	public static final int STAGE_5 	= 5;
	public static final int STAGE_6 	= 6;
	
	public static final int[] DistanceForStage = {
		300, 600, 900, 1200, 1500, 2000};
	public static final int[] GoldForCompleteStage = {
		20, 40, 60, 80, 100, 150};
}