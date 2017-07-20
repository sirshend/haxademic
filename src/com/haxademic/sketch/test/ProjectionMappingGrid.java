package com.haxademic.sketch.test;

import com.haxademic.core.app.AppSettings;
import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.draw.mesh.PGraphicsKeystone;
import com.haxademic.core.draw.util.OpenGLUtil;
import com.haxademic.core.file.FileUtil;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PShader;

public class ProjectionMappingGrid
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	PGraphicsKeystone[] keystoneQuads;
	
	protected boolean testPattern = false;
	protected PShader shaderPattern;
	protected PShader shaderPattern2;
	protected PImage overlayImage;
	protected int rows = 4;
	protected int cols = 5;

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.WIDTH, 1000 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 700 );
		p.appConfig.setProperty( AppSettings.FILLS_SCREEN, false );
		p.appConfig.setProperty( AppSettings.FULLSCREEN, false );
	}

	public void setup() {
		super.setup();	
		shaderPattern = p.loadShader(FileUtil.getFile("shaders/textures/cacheflowe-scrolling-dashed-lines.glsl"));
		shaderPattern2 = p.loadShader(FileUtil.getFile("shaders/textures/cacheflowe-op-wavy-rotate.glsl"));
		overlayImage = p.loadImage(FileUtil.getFile("images/test-sneaker-silhouette.png"));
		buildCanvas();
	}

	protected void buildCanvas() {
		keystoneQuads = new PGraphicsKeystone[rows * cols];
		for (int i = 0; i < keystoneQuads.length; i++) {
			PGraphics pg = p.createGraphics( p.width / 2, p.height / 2, P.P3D );
			pg.smooth(OpenGLUtil.SMOOTH_HIGH);
			keystoneQuads[i] = new PGraphicsKeystone(p, pg, 12, FileUtil.getFile("text/keystoning/grid-demo"+i+".txt"));
		}
	}
	
	protected void resetQuads() {
		// get coordinates based on random indexes across a grid
		for (int i = 0; i < keystoneQuads.length; i++) {
			float col = i % cols;
			float row = P.floor((float) i / cols);
			float x = P.map(col, 0, cols - 1, 0.2f * p.width, 0.8f * p.width);
			float y = P.map(row, 0, rows - 1, 0.2f * p.height, 0.8f * p.height);
			keystoneQuads[i].setPosition(x, y, 100, 50);
		}
	}

	public void drawApp() {
		p.background(0);
		
		// update textures
		shaderPattern.set("time", p.frameCount * 0.01f);
		shaderPattern2.set("time", p.frameCount * 0.01f);

		// update buffers
		for (int i = 0; i < keystoneQuads.length; i++) {
			PGraphics pg = keystoneQuads[i].pg();
			pg.beginDraw();
			if(i % 2 == 1) pg.filter(shaderPattern);
			else pg.filter(shaderPattern2);
			pg.image(overlayImage, 0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	
		// draw test patterns
		if(testPattern == true) {
			for (int i = 0; i < keystoneQuads.length; i++) {
				keystoneQuads[i].drawTestPattern();
			}
		}
		
		// draw to screen 
		for (int i = 0; i < keystoneQuads.length; i++) {
			keystoneQuads[i].update(p.g, true);
		}
	}

	public void keyPressed() {
		super.keyPressed();
		if(p.key == 'd') testPattern = !testPattern;
		if(p.key == 'r') resetQuads();
//		if(p.keyCode == 8) pgPinnable.resetCorners(p.g);
	}

}
