package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
	private static int x;
	private static int y;
	public static int width;
	static int height;
	private Texture img;
	private Sprite paddleSprite;
	private SpriteBatch batch;
	
	public Paddle(int x ,int y){
		Paddle.x = x;
		Paddle.y = y;
		Paddle.width = 150;
		Paddle.height = 40;
		img = new Texture("paddle.png");
		paddleSprite = new Sprite(img);
		batch = new SpriteBatch();
		
	}
	
	public void render(int x , int y){
		batch.begin(); //start drawing		
		paddleSprite.setPosition(x,y);
		paddleSprite.draw(batch);		
		batch.end(); //end drawing
	}
	
	public static int getY(){
		return y;
	}
	
	public static int getX(){
		return x;
	}
	
	public boolean hitPaddle() {
		if(paddleSprite.getBoundingRectangle().overlaps(Ball.ballSprite.getBoundingRectangle())){
			return true;
		}
		return false;
	}

	public void setX(int x) {
		Paddle.x = x;	
	}

	public int getWidth() {
		return Paddle.width;
	}

}
