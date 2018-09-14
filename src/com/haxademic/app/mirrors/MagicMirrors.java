package com.haxademic.app.mirrors;

import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.constants.PRenderers;
import com.haxademic.core.draw.filters.pgraphics.GPUParticlesSheetDisplacer;
import com.haxademic.core.draw.filters.pgraphics.PixelTriFilter;
import com.haxademic.core.draw.filters.pgraphics.shared.BaseVideoFilter;
import com.haxademic.core.draw.image.ImageUtil;
import com.haxademic.core.hardware.webcam.IWebCamCallback;

import processing.core.PGraphics;
import processing.core.PImage;

public class MagicMirrors 
extends PAppletHax
implements IWebCamCallback {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected float w = 1280;
	protected float h = 720;
	protected BaseVideoFilter vfx;
	
	protected int webcamW = 640;
	protected int webcamH = 480;
	protected PGraphics webcamBuffer;

	protected void overridePropsFile() {
		p.appConfig.setProperty(AppSettings.WIDTH, (int) w);
		p.appConfig.setProperty(AppSettings.HEIGHT, (int) h);
		p.appConfig.setProperty(AppSettings.WEBCAM_INDEX, 5);
	}

	protected void setupFirstFrame() {
		p.webCamWrapper.setDelegate(this);
		vfx = new GPUParticlesSheetDisplacer(p.width, p.height, 0.5f);
		vfx = new PixelTriFilter(p.width, p.height, 20);
		webcamBuffer = p.createGraphics(webcamW, webcamH, PRenderers.P2D);
	}

	public void drawApp() {
		background(0);
		vfx.update();
		p.image(vfx.image(), 0, 0);
	}

	@Override
	public void newFrame(PImage frame) {
		// copy webcam and create motion detection at size of cropped webcam (and downscaling)
		ImageUtil.cropFillCopyImage(frame, webcamBuffer, true);
		ImageUtil.flipH(webcamBuffer);
		
		// send new webcam mirror frame to vfx
		vfx.newFrame(webcamBuffer);
	}

		
}


































