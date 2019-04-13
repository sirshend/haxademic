package com.haxademic.core.draw.textures.pgraphics;

import com.haxademic.core.app.P;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.draw.shapes.Shapes;
import com.haxademic.core.draw.textures.pgraphics.shared.BaseTexture;
import com.haxademic.core.math.MathUtil;
import com.haxademic.core.math.easing.EasingFloat;

public class TextureConcentricDashedCubes
extends BaseTexture {

	protected EasingFloat speed = new EasingFloat(1f, 0.1f);
	protected float frames = 0;
	protected EasingFloat wobbleFreq = new EasingFloat(0.01f, 0.1f);
	protected EasingFloat wobbleAmp = new EasingFloat(0.1f, 0.1f);
	protected EasingFloat spacing = new EasingFloat(50, 0.1f);
	
	public TextureConcentricDashedCubes( int width, int height ) {
		super();
		buildGraphics( width, height );
	}
	
	public void preDraw() {
	}
	
	public void updateDraw() {
		// draw transition result to texture
		_texture.background(0);
		_texture.stroke(255);
		_texture.strokeWeight(3);
		
		// context & camera
		DrawUtil.setCenterScreen(_texture);
		DrawUtil.setDrawCenter(_texture);
		_texture.ortho();
		
		// hexagon tilt
		_texture.rotateZ(P.PI * 0.25f); // p.mousePercentY()

		speed.update(true);
		frames += speed.value();
		wobbleAmp.update(true);
		wobbleFreq.update(true);
		spacing.update(true);
//		frames = 0;
		float numCubes = 60;
		float loopProgress = (frames % 100f) / 100f;
		loopProgress = P.abs(loopProgress);
		for (int i = 0; i < numCubes; i++) {
			float cubeSize = i * spacing.value();
			cubeSize += loopProgress * spacing.value();
//			drawDashedCube(cubeSize, 20f + P.sin(p.loop.progressRads()) * 5f);
			_texture.pushMatrix();
			_texture.rotateZ(wobbleAmp.value() * P.sin(cubeSize * wobbleFreq.value()));
			Shapes.drawDashedCube(_texture, cubeSize, 2f + (cubeSize * 0.08f), false);
			_texture.popMatrix();
		}
	}
	
	public void updateTiming() {
		if(MathUtil.randBoolean(P.p)) {
			speed.setTarget(MathUtil.randRangeDecimal(-4f, 4f));
		}
	}
	
	public void updateTimingSection() {
		wobbleFreq.setTarget(MathUtil.randRangeDecimal(0.001f, 0.01f));
		wobbleAmp.setTarget(MathUtil.randRangeDecimal(-0.2f, 0.2f));
		spacing.setTarget(MathUtil.randRangeDecimal(30, 70));
	}
	
}
