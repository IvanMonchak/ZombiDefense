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
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;
import com.LCJ.ZombiDefense.G.SessionListener;
import com.LCJ.Layers.GamePlayLayer;
import com.LCJ.Layers.LevelLayer;
import com.LCJ.Layers.LoadingLayer;
import com.LCJ.Layers.PreplayLayer;
import com.LCJ.Layers.StartLayer;
import com.LCJ.Others.ScoreManager;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.SessionEvents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class ZombiDefense extends Activity {
//	static {
//		System.loadLibrary("androidgl20");
//		// System.loadLibrary("gdx");
//	}
	private static int DENSITY_XHIGH = 320;
	public static Context context;
	protected CCGLSurfaceView mGLSurfaceView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mGLSurfaceView = new CCGLSurfaceView(this);
		CCTexture2D.setDefaultAlphaPixelFormat(Config.ARGB_8888);

		CCDirector.sharedDirector().setScreenSize(G.DEFAULT_W, G.DEFAULT_H);
		CCDirector.sharedDirector().setDeviceOrientation(
				CCDirector.kCCDeviceOrientationLandscapeLeft);
		InitParam();
		setContentView(mGLSurfaceView, createLayoutParams());
		context = this;
	}

	private void InitParam() {
		Display display = getWindowManager().getDefaultDisplay();
		G.WIN_W = display.getWidth();
		G.WIN_H = display.getHeight();
		G.g_Context = this;
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		if((displayMetrics.densityDpi==DisplayMetrics.DENSITY_HIGH)||(displayMetrics.densityDpi==DENSITY_XHIGH))
			G.hpdi =true;
		
		if(G.hpdi ==  true){
			G.DEFAULT_W = 960;
			G.DEFAULT_H  = 640;
		}else{
			G.DEFAULT_W = 480;
			G.DEFAULT_H = 320;
		}
			
		G._scaleX = G.WIN_W / G.DEFAULT_W;
		G._scaleY = G.WIN_H / G.DEFAULT_H;
		
		//for FACEBOOK!
		G.g_Facebook 	= new Facebook(G.kFBAppId);
		G.mAsyncRunner 	= new AsyncFacebookRunner(G.g_Facebook);
		
		SessionListener listener = new SessionListener();
        SessionEvents.addAuthListener(listener);
        SessionEvents.addLogoutListener(listener);
		
		AppSettings.init();
	}

	protected LayoutParams createLayoutParams() {
		final DisplayMetrics pDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(pDisplayMetrics);

		final float mRatio = (float) G.DEFAULT_W / G.DEFAULT_H;
		final float realRatio = (float) pDisplayMetrics.widthPixels
				/ pDisplayMetrics.heightPixels;

		final int width;
		final int height;
		if (realRatio < mRatio) {
			width = pDisplayMetrics.widthPixels;
			height = Math.round(width / mRatio);
		} else {
			height = pDisplayMetrics.heightPixels;
			width = Math.round(height * mRatio);
		}

		final LayoutParams layoutParams = new LayoutParams(width, height);

		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}

	@Override
	protected void onPause() {
		super.onPause();
		CCDirector.sharedDirector().pause();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		CCDirector.sharedDirector().resume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		CCDirector.sharedDirector().attachInView(mGLSurfaceView);
		CCDirector.sharedDirector().setDisplayFPS(false);
		CCDirector.sharedDirector().setAnimationInterval(1f / 60f);
		CCDirector.sharedDirector().runWithScene(LoadingLayer.scene());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppSettings.stopSound();
		CCDirector.sharedDirector().end();
		Process.killProcess(Process.myPid());
		System.gc();
	}

	public static final int MORE_GAMES = 0; // StartLayer
	public static final int LEVEL_TOAST = 1; // ..Layer
	public static final int SHOW_FACEBOOK_DLG = 2;

	public Handler Help = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MORE_GAMES:
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:\"Hawk Industries, LLC.\""));
				startActivity(intent);
				return;
			case LEVEL_TOAST:
				break;
			case SHOW_FACEBOOK_DLG:
				G.submitScoreToFacebook(ScoreManager.sharedScoreManager().getCurrentScore());
				break;
			}
		}
	};
}