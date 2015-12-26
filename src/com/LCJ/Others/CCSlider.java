package com.LCJ.Others;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import com.LCJ.ZombiDefense.G;

public class CCSlider extends CCLayer
{
	public CCSprite 	_barImage;
	CCSliderTouchLogic 	_touchLogic;
	static float 		_max = 194; // bar.png Image Width

	public void  setTouchEnabled(boolean enable) {
	    _touchLogic.setIsTouchEnabled(enable);
	}
	
	public static CCSlider slider(String barImg, String normalThumb,String selectThumb, CCNode t, String s) {
		CCSlider self = new CCSlider();
		self._barImage = CCSprite.sprite(barImg);
		G.setScale(self._barImage);
		
		float w = self._barImage.getTexture().getWidth() * G._scaleX;
		float h = self._barImage.getTexture().getHeight() * G._scaleY;
		_max = w;
		
		CCNodeClipping node = new CCNodeClipping();
		G.setScale(node);
		node.setPosition(CGPoint.make(0, 0));
		node.setClippingRegion(CGRect.make(
			self.getPosition().x - w/2,
			self.getPosition().y - h/2, w, h));
		
		node.addChild(self._barImage, 1, 0x2);
		self.addChild(node, 2, 0x2);

		self._touchLogic = CCSliderTouchLogic.sliderTouchLogic(normalThumb, selectThumb, t, s);
		self.addChild(self._touchLogic, 3);
		
		return self;
	}

	public void  clippingBar(CGSize size) {
		CCNodeClipping node = (CCNodeClipping) getChildByTag(0x2);
		node.setClippingRegion(CGRect.make(
			getPosition().x - _barImage.getContentSize().width/2 * G._scaleX,
			getPosition().y - _barImage.getContentSize().height/2 * G._scaleY,
			size.width * G._scaleX, size.height * G._scaleY));
	}

	public CCSliderThumb getThumb() {
		return _touchLogic.getThumb();
	}

	public void setValue(float val) {
		_touchLogic._thumb.setValue(val);
	}

	public boolean getLiveDragging() {
		return _touchLogic._liveDragging;
	}

	public void setLiveDragging(boolean live) {
		_touchLogic._liveDragging = live;
	}

	//-------------------------------------------------
	public static class CCSliderTouchLogic extends CCMenu
	{
		CCSliderThumb 	_thumb;
		boolean 		_liveDragging;
	    CCNode  		_target;

		public static CCSliderTouchLogic sliderTouchLogic(String normalThumb,String selectThumb,CCNode target, String selector)
		{
			CCSliderTouchLogic self = new CCSliderTouchLogic();
			self.setPosition(CGPoint.ccp(0,0));
			self._target = target;
			self._liveDragging = false;
			self._thumb = CCSliderThumb.sliderThumb(normalThumb, selectThumb, target, selector);
			G.setScale(self._thumb, false);
			self.addChild(self._thumb);
			
			return self;
		}
	
		public CCSliderThumb getThumb() {
			return _thumb;
		}
	
		@Override public boolean ccTouchesMoved(MotionEvent event) {
			float x = convertTouchToNodeSpace(event).x;
			if (x<-_max/2)		_thumb.setPosition(CGPoint.ccp(-_max/2, 0));
			else if(x >_max/2)	_thumb.setPosition(CGPoint.ccp(_max/2, 0));
			else				_thumb.setPosition(CGPoint.ccp(x, 0));
			
			if (_liveDragging)  _thumb.activate();
			return true;
		}
	}

	//-------------------------------------------
	public static class CCSliderThumb extends CCMenuItemImage
	{
		protected CCSliderThumb(CCSprite normal, CCSprite selected,
				CCSprite disabled, CCNode t, String sel) {
			super(normal, selected, disabled, t, sel);
		}

		public  static CCSliderThumb sliderThumb(String normalImg, String selectImg, CCNode target, String selector) {
			CCSprite normal = CCSprite.sprite(normalImg);
			CCSprite select = CCSprite.sprite(selectImg);
			return new CCSliderThumb(normal, select, null, target, selector );
		}

		public float getValue() {
			return (position_.x+(_max/2))/_max;
		}

		public void setValue(float val)	{
			setPosition(CGPoint.ccp(val*_max-(_max/2), position_.y));
		}
	}
}

