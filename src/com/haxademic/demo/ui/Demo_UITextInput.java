package com.haxademic.demo.ui;

import java.util.ArrayList;

import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.data.constants.PTextAlign;
import com.haxademic.core.draw.color.ColorsHax;
import com.haxademic.core.media.DemoAssets;
import com.haxademic.core.ui.UITextInput;

public class Demo_UITextInput 
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected boolean debugMode = true;
	protected ArrayList<UITextInput> textInputs = new ArrayList<UITextInput>();
	
	public void setupFirstFrame () {
		int inputX = 100;
		int inputY = 10;
		int inputW = 300;
		for (int i = 0; i < 6; i++) {
			int inputH = 20 + 20 * i;
			textInputs.add(new UITextInput("demo"+i, 20, DemoAssets.fontOpenSansPath, ColorsHax.WHITE, 10, PTextAlign.LEFT, inputX, inputY, inputW, inputH));
			inputY += inputH + 20;
		}
	}
	
	public void keyPressed() {
		super.keyPressed();
		if(p.key == 'd') debugMode = !debugMode;
	}
	
	public void drawApp() {
		p.background(0);
		for (int i = 0; i < textInputs.size(); i++) {
			textInputs.get(i).update(p.g);
		}
	}
	
}