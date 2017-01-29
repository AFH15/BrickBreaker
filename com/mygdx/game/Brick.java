package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Brick {
	private int x, y;
	private static int width;
	private static int height;
	private boolean destroyed;
	private Color clur = Color.YELLOW;

	public Brick(int x, int y) {
	
		width = 50;
		height = 20;
		this.x = x;
		this.y = y;		
		this.destroyed = false;
	}

	public void render(ShapeRenderer shapeRenderer,int x, int y) {
		if (!destroyed) {			
            shapeRenderer.setColor(clur);
            shapeRenderer.rect(x, y, width, height);           
		}
	}

	public boolean hitBottom(int ballX, int ballY, int ballHeight, int ballWidth) {
		if ((ballX >= x) && (ballX <= x + width) && (ballY == y) && (destroyed == false)) {
			return true;
		}
		return false;
	}

	public boolean hitTop(int ballX, int ballY, int ballHeight, int ballWidth)  {
		if ((ballX >= x) && (ballX <= x + width) && ((ballY-ballHeight) == (y + height)) && (destroyed == false)) {
			return true;
		}
		return false;
	}

	public boolean hitLeft(int ballX, int ballY, int ballHeight, int ballWidth)  {
		if ((ballY >= y) && (ballY <= y + height) && ((ballX+ballWidth) == x) && (destroyed == false)) {
			return true;
		}
		return false;
	}

	public boolean hitRight(int ballX, int ballY, int ballHeight, int ballWidth)  {
		if ((ballY >= y) && (ballY <= y + height) && (ballX == (x + width)) && (destroyed == false)) {
			return true;
		}
		return false;
	}

	
	public void setDestroyed(boolean dest) {
		this.destroyed = dest;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getX(){
		return this.x;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
