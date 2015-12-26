/*
 * Copyright (C) 2012 The Android Game Source Project
 *
 * http://www.superman.org/licenses/LICENSE-2.0
 * 
 * Created by LCJ  on 22/01/2012
 * 
 */

package  com.LCJ.ZombiDefense;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.cocos2d.nodes.CCNode;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

//import com.YRH.DrakeTower.G;
//import com.YRH.DrakeTower.G.LoginDialogListener;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseDialogListener;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.FbDialog;
import com.facebook.android.SessionEvents;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;

public class G {
	public static float _scaleX 		= 1f;
	public static float _scaleY 		= 1f;
	public static float WIN_W;
	public static float WIN_H;
	public static boolean hpdi          = false;
	
	public static Facebook 		g_Facebook;
    public static AsyncFacebookRunner mAsyncRunner;
	public static Activity		g_Context; 		
	private static String		_facebookName;
	private static boolean		isLogging;
	private static boolean 		_posting;
	private static int			m_nTopScore;
	public static float _getX(float x) {
		return _scaleX*x;
	}

	public static float _getY(float y) {
		return _scaleY*y;
	}
	
	public static String _getImg(String imgName) {
		if(G.hpdi == true)
			return String.format("%s%s%s", "hdpi/", imgName, ".png" );
		else
			return String.format("%s%s%s", "mdpi/", imgName, ".png" );
	}
	
	public static String _getFont(String fntName) {
		return String.format("%s%s%s",_fntDirPath, fntName,".ttf");
	}

	public static void setScale(CCNode node) {
		node.setScaleX(_scaleX);
		node.setScaleY(_scaleY);
	}
	
	public static void setScale(CCNode node, float scaleFactor) {
		node.setScaleX(_scaleX*scaleFactor);
		node.setScaleY(_scaleY*scaleFactor);
	}
	
	public static void setScale(CCNode node, float scaleFactor, boolean bSmall) {
		float scale = bSmall ?
				(_scaleX<_scaleY ? _scaleX : _scaleY) :
				(_scaleX>_scaleY ? _scaleX : _scaleY);
		node.setScale(scale*scaleFactor);
	}

	public static void setScale(CCNode node, boolean bSmall) {
		float scale = bSmall ?
			(_scaleX<_scaleY ? _scaleX : _scaleY) :
			(_scaleX>_scaleY ? _scaleX : _scaleY);
		node.setScale(scale);
	}
	
	//for FACEBOOK!
	
    public static class LoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
            Toast.makeText(g_Context, "Facebook Login Success", Toast.LENGTH_SHORT).show();
            G.getFacebookName();
        }

        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
            Toast.makeText(g_Context, "Facebook Login Fail", Toast.LENGTH_SHORT).show();
        }
        
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
            Toast.makeText(g_Context, "Facebook Login Fail", Toast.LENGTH_SHORT).show();
        }

        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
            Toast.makeText(g_Context, "Facebook Login Cancel", Toast.LENGTH_SHORT).show();
        }
    }
 
	public static void submitScoreToFacebook(int topScoreValue)
	{
		_posting = true;
		m_nTopScore = topScoreValue;
//		// If we're not logged in, log in first...
		if (g_Facebook.isSessionValid()) {
			SessionEvents.onLogoutBegin();
            AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(G.g_Facebook);
            asyncRunner.logout(g_Context, new BaseRequestListener(){
				@Override public void onComplete(String response, Object state) {
					new Handler().post(new Runnable(){
						@Override public void run() {
							SessionEvents.onLogoutFinish();
							isLogging = false;
							_facebookName = null;

						}
					});
				}
            });
		} else {
			String[] kApiPer = new String[]{"publish_stream", "read_stream", "offline_access"};
        	G.g_Facebook.authorize(g_Context, kApiPer, new LoginDialogListener());
		}
	}


	public static void getFacebookName()
	{
		String fql = String.format("select uid,name from user where uid == %lld", g_Facebook.getAccessExpires());//uid);
		Bundle param = new Bundle();
		param.putString("query", fql);
		try {
			g_Facebook.request(param);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static class SampleDialogListener extends BaseDialogListener {
        public void onComplete(Bundle values) {
			final String name = values.getString("name");
			_facebookName = name;		
			isLogging = true;
			if (_posting) {
				postToWall(m_nTopScore);
				_posting = false;
			}
        }
    }
    
    public static class SessionListener implements AuthListener, LogoutListener {
        public void onAuthSucceed() {
            //setImageResource(R.drawable.logout_button);
            //SessionStore.save(mFb, getContext());
        }

        public void onAuthFail(String error) {
        }
        
        public void onLogoutBegin() {           
        }
        
        public void onLogoutFinish() {
            //SessionStore.clear(getContext());
            //setImageResource(R.drawable.login_button);
        }
    }

	private static void postToWall(int nScore) {
		FbDialog dialog = new FbDialog(g_Context, 
//			"http://www.geekquarter.com/games/tarzanandthetreasure/redirect/", 
			"http://www.facebook.com/pages/Hawk-I-Games/",
				new DialogListener() {
				@Override public void onComplete(Bundle values) {
					
					Bundle dd = new Bundle();
					
					if (!AppSettings.isStory) {
						String strDesc = String.format("wave %d", AppSettings.level);
						Bundle ddd = new Bundle();
						ddd.putCharSequence("name", "Zombie Defense for Android Phone");
						ddd.putCharSequence("caption", "My best wave");
						ddd.putCharSequence("description", strDesc);
						
						dd.putBundle("attachment", ddd);
						dd.putCharSequence("message", "I'm playing in survival mode");
						dd.putCharSequence("api_key", kFBAppId);
		
				try {
					g_Facebook.request("stream.publish", dd, "POST");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
	} else {
		String strScore = String.format("Score : %d", AppSettings.money);
		int nTotalSecond = AppSettings.totalTime / 60;
		int nHour = nTotalSecond / 3600;
		int nMinute = (nTotalSecond % 3600) / 60;
		int nSecond = nTotalSecond % 60;
		String strTime = String.format("Time : %02d:%02d:%02d", nHour, nMinute, nSecond);
		String strDesc = String.format("%s, %s", strScore, strTime);
		
		Bundle ddd = new Bundle();
		ddd.putCharSequence("name", "Zombie Defense for Android Phone");
		ddd.putCharSequence("caption", "My best score and time");
		ddd.putCharSequence("description", strDesc);
		
		dd.putBundle("attachment", ddd);
		dd.putCharSequence("message", "I'm playing in story mode");
		dd.putCharSequence("api_key", kFBAppId);

		try {
			g_Facebook.request("stream.publish", dd, "POST");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
					
								
				}
				@Override public void onFacebookError(FacebookError e) {}
				@Override public void onError(DialogError e) {}
				@Override public void onCancel() {}
		});
		dialog.show();
		
		
	}

	//for GameCenter
	public static void submitScore(int nScore) {
//		if(nScore > 0)
//		{
//			[self.gameCenterManager reportScore: nScore forCategory: self.currentLeaderBoard];
//		}
	}
	public static void showLeaderboard() {
		// TODO Auto-generated method stub
	}


	
	//private static final String _imgDirPath = "image/"; 
	private static final String _fntDirPath = "font/";
	
	public static float DEFAULT_W 		= 480f; 
	public static float DEFAULT_H 		= 320f;
	public static float PTM_RATIO 		= 32f; // pixels to metre ratio
	
	public static float POINT_Y			= 15f;
	public static String kFBAppId 		= "113634462039118";
	
	public static int MONEY_BOX 		= 50;
	public static int MONEY_GUN1		= 150;
	public static int MONEY_GUN2		= 350;
	public static int MONEY_GUN3		= 500;
	public static int MONEY_GUN4		= 650;
	public static int MONEY_SHELTER1	= 350;
	public static int MONEY_SHELTER2	= 500;
	public static int MONEY_SHELTER3	= 650;
	public static int MONEY_SHELTER4	= 800;
	public static int MONEY_BONUS		= 50;
	
	public static int WALL_HEALTH1	= 30;
	public static int WALL_HEALTH2	= 40;
	public static int WALL_HEALTH3	= 60;
	public static int WALL_HEALTH4	= 100;
}