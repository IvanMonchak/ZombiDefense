package com.LCJ.Objects;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.utils.javolution.MathLib;
import org.cocos2d.config.ccMacros;

import com.LCJ.ZombiDefense.G;

public class bulletObject extends CCSprite
{
	float                  	 x;
	float             	   	 y;
	int             	   	 type;
	public float             stepx;
	public float             stepy;
	public float             sposx;
	public float             sposy;
    float           	     rad;
	
    public bulletObject(String strImg) {
		// TODO Auto-generated constructor stub
    	super(strImg);
	}

	public static bulletObject createBullet(float posX, float posY, int t)
    {
    	float deltaX = 0; 
    	float deltaY = 0;
        
//    	bulletObject self = new bulletObject();
    	String strBullet = String.format("bullet_%d", t);
        bulletObject self = new bulletObject(G._getImg(strBullet));
        //bulletObject self1 = new bulletObject(G._getImg("bullet"));
        //bulletObject self2 = new bulletObject(G._getImg("bullet"));
        G.setScale(self);
        self.x = posX;
    	self.y = posY;
    	self.type = t;
        
        if (self.type == 1) {
        	deltaX = 32; 
            deltaY = 40;
        } else if (self.type == 2) {
            deltaX = 30; 
            deltaY = 38;
        } else if (self.type == 3) {
            deltaX = 32; 
            deltaY = 37;
        } else if (self.type == 4) {
            deltaX = 36; 
            deltaY = 39;
        }
        if(G.hpdi == false){
        	self.sposx = G._getX(65 + deltaX); 
        	self.sposy = G._getY(130 + deltaY);
        }else{
        	self.sposx = G._getX(117 + deltaX*2); 
        	self.sposy = G._getY(258 + deltaY*2);
        }
        self.stepx = G._getX(40); 
        self.stepy = 0;
        self.stepy = self.stepx * (self.y-self.sposy) / (self.x-self.sposx);
        self.setRotation(-1 * ccMacros.CC_RADIANS_TO_DEGREES((float) MathLib.atan(self.stepy / self.stepx)));
        return self;
    }

    public void moveBullet()
    {	
        sposx += stepx;
        sposy += stepy;
        this.setPosition(sposx, sposy);
    }
}