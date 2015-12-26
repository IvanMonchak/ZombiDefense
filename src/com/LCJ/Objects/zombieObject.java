package com.LCJ.Objects;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.utils.javolution.MathLib;

import com.LCJ.ZombiDefense.AppSettings;
import com.LCJ.ZombiDefense.G;

public class zombieObject extends CCSprite
{
	public float 		x;
	public float 		y;
	public int			type;		// 0~3 : 0~2 zombie, 3 mouse
	public int 			status;		// 1 : move, 2 : heat and dead, 3 : unvisible, 0: init
    public int 			ani;
    public int 			health;
    public CGRect 		rcHit;
    public boolean    	isStop;
    
    CCTexture2D[]    m_txtRedAttack = new CCTexture2D[5];
    CCTexture2D[]    m_txtAttack = new CCTexture2D[5];
    CCTexture2D[]    m_txtMove = new CCTexture2D[9];
    CCTexture2D[]    m_txtRed = new CCTexture2D[9];
    CCTexture2D[]    m_txtDead = new CCTexture2D[8];
    
    public void initZombie()
    {
    	x = 0; y = 0;
    	type = 999;			
    	status = 0;		
        ani = 0;
        health = 0;
    }

    public zombieObject(String strImg) {
		// TODO Auto-generated constructor stub
    	super(strImg);
	}
    
    public static zombieObject createZombie(float posX, float posY)
    {
    	//zombieObject self = new zombieObject();
    	int type1 = (int) ((MathLib.random(0, 3)+(int)posX+(int)posY)%4);
    	String str = String.format("zombie%d_1", (type1+1));
    	
    	zombieObject self = new zombieObject(G._getImg(str));
    	G.setScale(self);
    	self.x = posX;
    	self.y = posY;
    	self.type = type1; //arc4random()
    	self.status = 1;						
    	self.ani = 0;
    	self.health = 30;
    	self.isStop = false;
    	if ((MathLib.random(0, 19)+(int)posX+(int)posY)%20 == 0) {	
    		self.type = 2;
    	}
    	
    	if (self.type == 0) {
    		for (int i=0; i<3; i++) {
    			String strAttack = String.format("attack%d_%d", self.type+1, i+1);
            	self.m_txtAttack[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strAttack));
    		}
    		for (int i=0; i<3; i++) {
    			String strAttack = String.format("red_attack%d_%d", self.type+1, i+1);
            	self.m_txtRedAttack[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strAttack));
    		}
    	} else {
    		for (int i=0; i<5; i++) {
    			String strAttack = String.format("attack%d_%d", self.type+1, i+1);
            	self.m_txtAttack[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strAttack));
    		}
    		for (int i=0; i<5; i++) {
    			String strAttack = String.format("red_attack%d_%d", self.type+1, i+1);
            	self.m_txtRedAttack[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strAttack));
    		}
    	}
    	
    	if (self.type == 0 || self.type == 1) {
    		for (int i=0; i<8; i++) {
    			String strMove = String.format("zombie%d_%d", self.type+1, i+1);
            	self.m_txtMove[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strMove));
    		}
    		for (int i=0; i<8; i++) {
    			String strMove = String.format("red_zombie%d_%d", self.type+1, i+1);
            	self.m_txtRed[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strMove));
    		}
    	} else {
    		for (int i=0; i<9; i++) {
    			String strMove = String.format("zombie%d_%d", self.type+1, i+1);
            	self.m_txtMove[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strMove));
    		}
    		for (int i=0; i<9; i++) {
    			String strMove = String.format("red_zombie%d_%d", self.type+1, i+1);
            	self.m_txtRed[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strMove));
    		}
    	}
    	
        if (self.type == 0) {
            for (int i=0; i<5; i++) {
            	String strDead = String.format("sh4_%d", i+1);
            	self.m_txtDead[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strDead));
            }
        } else {
            for (int i=0; i<8; i++) {
            	String strDead = String.format("sh%d_%d", self.type, i+1);
            	self.m_txtDead[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strDead));
            }
        }
        self.setCarTexture();
        self.calcHitRect();
    	return self;
    }

    public void calcHitRect()
    {
        if (type == 0) {
            rcHit = CGRect.make(x-this.getContentSize().width/2, y-this.getContentSize().height/2, 1, this.getContentSize().height);
        } else if (type == 1) {
        	if(G.hpdi ==true)
        		rcHit = CGRect.make(x-34*G._scaleX, y-100*G._scaleY, 88*G._scaleX, 24*G._scaleY);
        	else
        		rcHit = CGRect.make(x-17*G._scaleX, y-50*G._scaleY, 44*G._scaleX, 12*G._scaleY);    
        } else if (type == 2) {
        	if(G.hpdi == true)
        		rcHit = CGRect.make(x-22*G._scaleX, y-88*G._scaleY, 92*G._scaleX, 32*G._scaleY);
        	else
        		rcHit = CGRect.make(x-11*G._scaleX, y-44*G._scaleY, 46*G._scaleX, 16*G._scaleY);
        } else if (type == 3) {
        	if(G.hpdi == true)
        		rcHit = CGRect.make(x-16*G._scaleX, y-68*G._scaleY, 60*G._scaleX, 32*G._scaleY);
        	else
        		rcHit = CGRect.make(x-8*G._scaleX, y-34*G._scaleY, 30*G._scaleX, 16*G._scaleY);
        }
    }

    public void releaseZombie()
    {
    	initZombie();
    }

    public void moveZombie()
    {	
		if (status == 1) {// moving
			int step = 7;
			if(!AppSettings.isStory){
				step += AppSettings.level/10;
			}
			if (!isStop)
				x -= G._getX(step)/*step * G._scaleX*/;
			if (isStop) {
				if (type == 0) {
					if (ani >= 2) {
						ani = 0;
					} else {
						ani++;
					}
				} else {
					if (ani >= 4) {
						ani = 0;
					} else {
						ani++;
					}
				}
			} else {
				if (type == 0 || type == 1) {
					if (ani == 7) {
						ani = 0;
					} else {
						ani++;
					}
				} else {
					if (ani == 8) {
						ani = 0;
					} else {
						ani++;
					}
				}
			}
		} else if (status == 2) {// deading
            if (type == 0) {
                if (ani == 4) {
                    status = 3;
                    AppSettings.playEffect(AppSettings.kZombie0);
                    this.setOpacity(250);
                } else {
                    ani++;
                }
            } else {
                if (ani == 7) {
                    status = 3;
                    if (type == 1){
                    	AppSettings.playEffect(AppSettings.kZombie1);
                    }else if (type == 2){
                    	AppSettings.playEffect(AppSettings.kZombie2);
                    } if(type == 3){
                    	AppSettings.playEffect(AppSettings.kZombie3);
                    }
                    this.setOpacity(250);
                } else {
                    ani ++;
                }
            }
    	} else if (status == 3) {//alpha --
            if (this.getOpacity() == 0) {
                status = 4;
            } else {
            	this.setOpacity(this.getOpacity()-25);
            }
        }
        
        if (status != 4) 
        	setCarTexture();

        calcHitRect();
    }

    public void setCarTexture()
    {
        if (status == 1) {
        	if (isStop) {
        		this.setTexture(m_txtAttack[ani]);
    		} else {
    			this.setTexture(m_txtMove[ani]);
    		}
        } else if (status == 2) {
        	this.setTexture(m_txtDead[ani]);
        } else if (status == 3) {
        	this.setTexture(m_txtDead[ani]);
        }
        CGRect rect = CGRect.zero();
        rect.size = this.getTexture().getContentSize();
        this.setTextureRect(rect);
    }

    public boolean touchZombie(float posX, float posY, int gun)
    {
        float nZombieWidth = 0;
        float nZombieHeight = 0;
        if (type == 0) {
        	if(G.hpdi == true){
        		nZombieWidth = 156*G._scaleX ;
                nZombieHeight = 46*G._scaleY;
        	}else{
        		nZombieWidth = 78*G._scaleX ;
                nZombieHeight = 23*G._scaleY;	
        	}
        } else if (type == 1) {
        	if(G.hpdi == true){
        		nZombieWidth = 156*G._scaleX ;
                nZombieHeight = 202*G._scaleY;
        	}else{
        		nZombieWidth = 78*G._scaleX;
                nZombieHeight = 101*G._scaleY;
        	}
        } else if (type == 2) {
        	if(G.hpdi == true){
        		nZombieWidth = 156*G._scaleX ;
                nZombieHeight = 178*G._scaleY;
        	}else{
        		nZombieWidth = 78*G._scaleX;
                nZombieHeight = 89*G._scaleY;
        	}
        } else if (type == 3) {
        	if(G.hpdi == true){
        		nZombieWidth = 156*G._scaleX ;
                nZombieHeight = 202*G._scaleY;
        	}else{
        		nZombieWidth = 78*G._scaleX;
                nZombieHeight = 101*G._scaleY;
        	}
        }
        
        CGRect rect = CGRect.make(x-nZombieWidth/2, y-nZombieHeight/2, nZombieWidth, nZombieHeight);

        if(CGRect.containsPoint(rect, CGPoint.make(posX, posY))){
        	boolean isLong = true;
            if (posX < G._getX(G.DEFAULT_W * 5 / 8)) {
                isLong = false;
            }
            health -= AppSettings.getDamage(gun,isLong);
            if (status == 1){
            	this.setTexture(isStop ? m_txtRedAttack[ani]:m_txtRed[ani]);
                CGRect rect1 = CGRect.zero();
                rect1.size = this.getTexture().getContentSize();
                this.setTextureRect(rect1);
            }
    		return true;
        }
    	
    	return false;
    }
}