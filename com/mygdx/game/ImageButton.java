package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageButton {

	private Sprite skin;
	private int x,y,width,height;

	public ImageButton(Texture texture, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		skin = new Sprite(texture);
		skin.setPosition(x, y);
		skin.setSize(width, height);
	}

	public void update(SpriteBatch batch) {
		skin.draw(batch); // draw the button
	}

	public boolean isClicked(int x, int y) {
		if (x > this.x && x < this.x + this.width) {
			if (y > this.y && y < this.y + this.height) {
				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
					return true;
				}		
			}
		}
		return false;
	}
	
	public boolean isHovered(int x, int y) {
		if (x > this.x && x < this.x + this.width) {
			if (y > this.y && y < this.y + this.height) {
				return true;	
			}
		}
		return false;
	}

}
