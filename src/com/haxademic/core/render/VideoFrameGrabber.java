package com.haxademic.core.render;


import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Movie;

import com.haxademic.core.app.P;
import com.haxademic.core.math.MathUtil;

public class VideoFrameGrabber {

	protected PApplet p;
	protected Movie _movie;
	protected PGraphics _curFrame;
	protected int newFrame = 0;

	protected String _videoFile;
	protected PGraphics _curFrameBuffer = null;
	protected PImage _curFrameImage= null;
	protected int _fps = 30;
	protected int _startFrame = 0;
	
	public VideoFrameGrabber( PApplet p, String videoFile, int frameRate, int startFrame ) {
		this.p = p;
		_videoFile = videoFile;
		_fps = frameRate;
		_startFrame = startFrame;
		
		// Load and set the video to play. Setting the video 
		// in play mode is needed so at least one frame is read
		// and we can get duration, size and other information from
		// the video stream. 
		initMovie();

		_curFrameBuffer = p.createGraphics( _movie.width, _movie.height, P.P3D );
		_curFrameImage = p.createImage( _movie.width, _movie.height, P.ARGB );
	}
	
	protected void initMovie() {
		_movie = new Movie( p, _videoFile );
		_movie.play();
		_movie.jump(0);
		_movie.pause();
	}

	
	public int width() {
		return _movie.width;
	}
	
	public int height() {
		return _movie.height;
	}
	
	public Movie movie() {
		return _movie;
	}
	
	public PImage frameImageCopy() {
		return _curFrameImage;
	}
	
	public PGraphics frameGraphicsCopy() {
		return _curFrameBuffer;
	}
	
	public boolean isVideoDataGood() {
		if( _movie.pixels.length > 100 ) return true;
		else return false;
	}
	
	public void randomMovieTime() {
		_movie.jump( MathUtil.randRangeDecimal( 0, _movie.duration() - 1 ) );
	}

	public int getFrame() {    
		return P.ceil( _movie.time() * 30f ) - 1;
	}

	public void setFrameIndex(int n) {
		if(_movie.time() <= 0 ) initMovie(); // fix movie unloading problem???
		
		_movie.play();

		// The duration of a single frame:
		float frameDuration = 1.0f / _fps;

		// We move to the middle of the frame by adding 0.5:
		float where = (n + 0.5f) * frameDuration; 

		// Taking into account border effects:
		float diff = _movie.duration() - where;
		if (diff < 0) {
			where += diff - 0.25f * frameDuration;
		}

		_movie.jump(where);
		_movie.pause();  
		
		P.println("Movie frame: "+getFrame() + " / " + (getLength() - 1));
		
		// copy pixels to PGraphics and PImage - is this necessary?
		_curFrameBuffer.beginDraw();
		_curFrameBuffer.image( _movie, 0, 0, _movie.width, _movie.height);
		_curFrameBuffer.endDraw();
		_curFrameImage.copy( _curFrameBuffer, 0, 0, _curFrameBuffer.width, _curFrameBuffer.height, 0, 0, _curFrameImage.width, _curFrameImage.height );
	}  

	int getLength() {
		return (int) (_movie.duration() * _fps);
	}
}
