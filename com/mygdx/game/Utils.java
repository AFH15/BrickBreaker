package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Utils {
	
	public static int mouseX(){
		int mouseX = Gdx.input.getX();
		return mouseX;
	}
	public static int mouseY(){
		int mouseY = Gdx.input.getY();
		return mouseY;
		
	}
}
