package com.haxademic.core.draw.filters.shaders;

import com.haxademic.core.draw.filters.shaders.shared.BaseFilter;
import com.haxademic.core.file.DemoAssets;

import processing.core.PApplet;
import processing.core.PImage;

public class DisplacementMapFilter
extends BaseFilter {

	public static DisplacementMapFilter instance;
	
	public DisplacementMapFilter(PApplet p) {
		super(p, "haxademic/shaders/filters/displacement-map.glsl");
		setMap(DemoAssets.smallTexture());
		setMode(1);
	}
	
	public static DisplacementMapFilter instance(PApplet p) {
		if(instance != null) return instance;
		instance = new DisplacementMapFilter(p);
		return instance;
	}
	
	public void setMap(PImage texture) {
		shader.set("map", texture);
	}
	
	public void setMode(int mode) {
		shader.set("mode", mode);
	}
	
}
