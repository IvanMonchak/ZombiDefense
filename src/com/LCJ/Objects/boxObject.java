package com.LCJ.Objects;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGRect;

import com.LCJ.ZombiDefense.G;

public class boxObject extends CCSprite
{
	public float 			x; 
	public float 			y;
	public int 				state;
	public int 				w;
	public int 				hu;
	public int 				hd;
	public int 				health;
	public CGRect           rcHit;
    CCTexture2D[]   m_txtBox = new CCTexture2D[4];//4

    public boxObject(String _getImg) {
		// TODO Auto-generated constructor stub
    	super(_getImg);
	}

	public static boxObject createBox(float posX, float posY)
    {
    	boxObject self = new boxObject(G._getImg("box1"));
    	self.x = posX; 
    	self.y = posY; 
    	self.state = 1;
    	if(G.hpdi == false)
    		self.rcHit = CGRect.make(self.x-31*G._scaleX, self.y-13*G._scaleY, 51*G._scaleX, 44*G._scaleY);
    	else
    		self.rcHit = CGRect.make(self.x-62*G._scaleX, self.y-26*G._scaleY, 102*G._scaleX, 88*G._scaleY);
        
        for (int i=0; i<4; i++) {
            String strBox = String.format("box%d", i+1);
            self.m_txtBox[i] = CCTextureCache.sharedTextureCache().addImage(G._getImg(strBox));
        }
        
        self.health = 10;
        return self;
    }

    public void changeBoxState()
    {	
        health --;
        if (health == 0) {
            health = 10;

            state++;
            if (state == 5) {
                return;
            }
            this.setTexture(m_txtBox[state-1]);
            CGRect rect = CGRect.zero();
            rect.size = this.getTexture().getContentSize();
            this.setTextureRect(rect);
        }
    }
}
