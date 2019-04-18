package com.haxademic.core.draw.filters.pgraphics.shared;

import com.haxademic.core.app.P;
import com.haxademic.core.data.constants.PRenderers;
import com.haxademic.core.debug.DebugUtil;
import com.haxademic.core.draw.image.ImageUtil;

import processing.core.PGraphics;
import processing.core.PImage;

public class BaseVideoFilter {

	protected int width;
	protected int height;
	protected PGraphics sourceBuffer;
	protected PGraphics destBuffer;
	protected boolean hasExtraSourceCopy = false;	// did we create an extra source buffer for pre-processing step? or are we using the original source buffer directly?
	
	public BaseVideoFilter(int width, int height) {
		this(width, height, null);
		hasExtraSourceCopy = true;
	}
	
	public BaseVideoFilter(int width, int height, PGraphics sourceBuffer) {
		this.width = width;
		this.height = height;
		this.sourceBuffer = (sourceBuffer != null) ? sourceBuffer : P.p.createGraphics(width, height, PRenderers.P3D);
		destBuffer = P.p.createGraphics(width, height, PRenderers.P3D);
	}
	
	public PGraphics image() {
		return destBuffer;
	}
	
	public void newFrame(PImage frame) {
		if(hasExtraSourceCopy) return;
		ImageUtil.cropFillCopyImage(frame, sourceBuffer, true);
	}
	
	public void update() {
		DebugUtil.printErr("Must override BaseVideoFilter.update(");
	}
}
