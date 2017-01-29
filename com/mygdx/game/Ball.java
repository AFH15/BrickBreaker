package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
	private int x,y,width,height;
	private int xDir,yDir = 5;
	boolean start = true;
	private static Texture img;
	static Sprite ballSprite;
	private SpriteBatch batch;
	
	public Ball(int x ,int y){
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		img = new Texture("ball.png");
		ballSprite = new Sprite(img);
		batch = new SpriteBatch();
	}
	
	public void render(int x , int y){
		batch.begin(); //start drawing
		ballSprite.setPosition(x, y);
		ballSprite.draw(batch);
		batch.end(); //end drawing
	}
	
	public int getY(){
		return y;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void move(){
		
		if (start == true) {
			render(Utils.mouseX() - 5 , Paddle.getY() + Paddle.height);
			x = Utils.mouseX() - 5;
			y = Paddle.getY() + Paddle.height;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				start = false;
			}
		}if(start == false){
			x += xDir;
			y += yDir;
			render(x,y);
		}
		
	}

	
	public void setXDir(int xDir) {
		this.xDir = xDir;
	}

	public void setYDir(int yDir) {
		this.yDir = yDir;
	}

	public int getXDir() {
		return this.xDir;
	}

	public int getYDir() {
		return this.yDir;
	}

	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}

}
