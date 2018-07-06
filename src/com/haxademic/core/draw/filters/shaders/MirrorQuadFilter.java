package com.haxademic.core.draw.filters.shaders;

import com.haxademic.core.draw.filters.shaders.shared.BaseFilter;

import processing.core.PApplet;
import processing.core.PGraphics;

public class MirrorQuadFilter
extends BaseFilter {

	public static MirrorQuadFilter instance;
	
	public MirrorQuadFilter(PApplet p) {
		super(p, "haxademic/shaders/filters/mirror-quad.glsl");
		setZoom(1f);
	}
	
	public static MirrorQuadFilter instance(PApplet p) {
		if(instance != null) return instance;
		instance = new MirrorQuadFilter(p);
		return instance;
	}
	
	public void applyTo(PGraphics pg) {
		shader.set("textureDupe", pg);
		super.applyTo(pg);
	}
	
	public void applyTo(PApplet p) {
		shader.set("textureDupe", p.g);
		super.applyTo(p);
	}
	
	public void setZoom(float zoom) {
		shader.set("zoom", zoom);
	}

}
