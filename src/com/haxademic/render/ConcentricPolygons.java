package com.haxademic.render;

import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.app.config.AppSettings;
import com.haxademic.core.app.config.Config;
import com.haxademic.core.draw.color.ImageGradient;
import com.haxademic.core.draw.context.PG;
import com.haxademic.core.draw.shapes.Shapes;
import com.haxademic.core.math.MathUtil;

public class ConcentricPolygons 
extends PAppletHax {
	public static void main(String args[]) { arguments = args; PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected ImageGradient imageGradient;
	
	protected void config() {
		int FRAMES = 140;
//		Config.setProperty( AppSettings.SUNFLOW, true );
//		Config.setProperty( AppSettings.SUNFLOW_ACTIVE, false );
		Config.setProperty( AppSettings.LOOP_FRAMES, FRAMES );
		Config.setProperty( AppSettings.WIDTH, 1000 );
		Config.setProperty( AppSettings.HEIGHT, 800 );
		Config.setProperty( AppSettings.FILLS_SCREEN, false );
		Config.setProperty( AppSettings.RENDERING_MOVIE, false );
		Config.setProperty( AppSettings.RENDERING_MOVIE_START_FRAME, 1 );
		Config.setProperty( AppSettings.RENDERING_MOVIE_STOP_FRAME, 1 + FRAMES );
	}
	
	public void firstFrame() {
		imageGradient = new ImageGradient(ImageGradient.BLACK_HOLE());
	}

	public void drawApp() {
		p.background(0);
		p.noStroke();
		//		PG.setBetterLights(p);
		p.lights();
		PG.setCenterScreen(p);
//		p.translate(0, p.height * -0.05f);
//		rotateX(0.9f + 0.15f * P.sin(loop.progressRads())); 
		
		
		// set up concentric polygon config
		float radius = p.height * 1.8f; 
		float spacing = p.height * 0.03f; 
		float lineWeight = radius * 0.015f;
		float vertices = P.round(5.5f + 2.5f * P.sin(loop.progressRads()));
		vertices = 6f;
		
		// rotate polygon to sit on a flat bottom
		// offset y
		float radiusClosest = MathUtil.polygonClosestPoint(vertices, radius);
		float radiusDiff = radius - radiusClosest;
//		p.translate(0, radiusDiff);
		// rotate
		float segmentRads = P.TWO_PI / vertices;
		p.rotate(P.HALF_PI);
		p.rotate(segmentRads / 2f);
		
		// draw concentric shapes
		float index = 0;
		while(radius > 2) {
			p.pushMatrix();
			p.translate(0, 0, p.width * 0.27f * P.sin(index * 0.3f + loop.progressRads()));
//			p.rotateZ(0.2f * P.sin(index * 0.3f + loop.progressRads()));
			p.fill(imageGradient.getColorAtProgress(0.5f + 0.4f * P.sin(index * 0.3f + loop.progressRads())));
//			Shapes.drawDisc(p, radius, radius - lineWeight, (int) vertices);
			float h = 160f + 155f * P.sin(P.PI + index * 0.3f + loop.progressRads());
			Shapes.drawDisc3D(p, radius, radius - lineWeight, h, (int) vertices, 0, 0 );
			radius -= lineWeight + spacing;
			lineWeight *= 0.98f;
			index++;
			p.popMatrix();
		}
	}
}
