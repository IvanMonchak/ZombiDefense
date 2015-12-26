package com.LCJ.Others;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGRect;

public class CCNodeClipping extends CCNode {
	CGRect clipRegionInNodeCoord;

	public void setClippingRegion(CGRect region) {
		clipRegionInNodeCoord = region;
	}

	@Override public void visit(GL10 gl) {
		gl.glPushMatrix();
			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor(
				(int) (clipRegionInNodeCoord.origin.x /*+ getPosition().x*/), 
				(int) (clipRegionInNodeCoord.origin.y /*+ getPosition().y*/),
				(int) clipRegionInNodeCoord.size.width, (int) clipRegionInNodeCoord.size.height );
				super.visit(gl);
			gl.glDisable(GL10.GL_SCISSOR_TEST);
		gl.glPopMatrix();
	}
}