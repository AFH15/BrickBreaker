package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Game extends ApplicationAdapter implements InputProcessor{
	Paddle pad;
	Ball bal;
	SpriteBatch batch;
	Texture mainMenuTitle, mainMenuHighScoreButton, mainMenuQuitButton, gameOverScreen;
	Sprite guy;
	int x,y,xSpeed,score = 0, lives = 3, bricksLeft = 0;
	Brick[][] brick = new Brick[6][11];
	BitmapFont font;
	boolean gameOver = false, gameStart = true;
	Music music;
	Sound hitSound;
	ShapeRenderer shapeRenderer;
	ImageButton playButton, playButtonHovered, quitButton, quitButtonHovered;

	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/main.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bounce.wav"));
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		bal = new Ball(Utils.mouseX() - 5 , Paddle.getY() + Paddle.height);	
		pad = new Paddle(0,80);	
		
		makeBricks();
		loadMainMenu();
		
	
	}
	
	public void loadMainMenu(){
		mainMenuTitle = new Texture(Gdx.files.internal("mainmenu/mainMenuTitle.png"));
				
		playButton = new ImageButton(new Texture(Gdx.files.internal("mainmenu/buttons/playButton.png")), 170, 500, 230, 80);
		playButtonHovered = new ImageButton(new Texture(Gdx.files.internal("mainmenu/buttons/playButtonHovered.png")), 170, 500, 230, 80);
		
		mainMenuHighScoreButton = new Texture(Gdx.files.internal("mainmenu/buttons/highScoreButton.png"));
		
		quitButton = new ImageButton(new Texture(Gdx.files.internal("mainmenu/buttons/quitButton.png")), 170, 300, 230, 80);
		quitButtonHovered = new ImageButton(new Texture(Gdx.files.internal("mainmenu/buttons/quitButtonHovered.png")), 170, 300, 230, 80);
		
		gameOverScreen = new Texture(Gdx.files.internal("mainmenu/gameOver.png"));	
		
	}
	
	@Override
	public void render(){
		if(gameStart == true){
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
			System.out.println(mouseX +" "+ mouseY);
			
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			batch.begin();		
			batch.draw(mainMenuTitle, 0, 0);
			playButton.update(batch);
			quitButton.update(batch);
			batch.end();
			
			if(playButton.isHovered(mouseX, mouseY)){
				if(playButton.isClicked(mouseX, mouseY)){
					hitSound.play(1.0f);
					gameStart = false;
					return;
				}
				batch.begin();	
				playButtonHovered.update(batch);
				batch.end();
			}
			
			if(quitButton.isHovered(mouseX, mouseY)){
				if(quitButton.isClicked(mouseX, mouseY)){
					System.exit(0);
					return;
				}
				batch.begin();	
				quitButtonHovered.update(batch);
				batch.end();
			}
			
			
		}		
		if (gameOver == false && gameStart == false) {
			if(bricksLeft == 0){
				gameOver = true;
				return;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
				hitSound.play(1.0f);
				gameStart = true;
			}
			
			Gdx.graphics.setTitle("BrickBreaker! Score: "+score);
			//music.play();
			//music.setLooping(true);
			
			xSpeed = 5;
			int x1 = bal.getX();
			int y1 = bal.getY() + bal.getHeight();

			int mouseX = Utils.mouseX();

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			pad.render(mouseX - 75, Paddle.getY());
			renderBricks();
			checkPaddle();
			if(bal.start == false){
				checkWall(x1, y1);
			}
			checkBricks(x1, y1,bal.getHeight(),bal.getWidth());
			bal.move();
			batch.begin();
			font.getData().setScale(1F);
			font.draw(batch, "Score: " + score, 5, 15);
			font.draw(batch, "Lives: " + lives, 5, 35);
			batch.end();
		
		}
		if(gameOver == true){
			Gdx.graphics.setTitle("GAMEOVER! Score: "+score);
			//music.stop();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			batch.draw(gameOverScreen, 0, 0);
			batch.end();
			
			if(Gdx.input.isKeyPressed(Keys.R)){
				reset();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
				gameStart = true;
			}
		}
		
	}

	public void makeBricks(){
		int x = 15;
		int y = Gdx.graphics.getHeight() - 40;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 11; j++) {
				brick[i][j] = new Brick(x, y);
				bricksLeft++;
				x+= 52;
			}
			x = 15;
			y-= 40;
		}
	}
	
	public void renderBricks(){
		shapeRenderer.begin(ShapeType.Filled);
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 11; j++) {
				brick[i][j].render(shapeRenderer, brick[i][j].getX(),brick[i][j].getY());	
			}
		}
		shapeRenderer.end();
		
	}
	
	public void checkBricks(int x1, int y1, int h,int w) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 11; j++) {				
				if (brick[i][j].hitBottom(x1, y1,h,w)) {
					hitSound.play(1.0f);
					bal.setYDir(-5);
					score += 20;
					brick[i][j].setDestroyed(true);
					bricksLeft--;
				}
				if (brick[i][j].hitLeft(x1, y1,h,w)) {
					hitSound.play(1.0f);
					xSpeed = -Math.abs(xSpeed);
					bal.setXDir(xSpeed);
					score += 20;
					brick[i][j].setDestroyed(true);
					bricksLeft--;
				}
				if (brick[i][j].hitRight(x1, y1,h,w)) {
					hitSound.play(1.0f);
					xSpeed = -Math.abs(xSpeed);
					bal.setXDir(xSpeed);
					score += 20;
					brick[i][j].setDestroyed(true);
					bricksLeft--;
				}
				if (brick[i][j].hitTop(x1, y1,h,w)) {
					hitSound.play(1.0f);
					bal.setYDir(5);
					score += 20;
					brick[i][j].setDestroyed(true);
					bricksLeft--;
				}
			}
		}
	}
	public void checkPaddle() {
		if (pad.hitPaddle() && bal.getXDir() <= 0) {
			hitSound.play(1.0f);
			bal.setYDir(5);
			xSpeed = -5;
			bal.setXDir(xSpeed);
		}
		if (pad.hitPaddle() && bal.getXDir() > 0) {
			hitSound.play(1.0f);
			bal.setYDir(5);
			xSpeed = 5;
			bal.setXDir(xSpeed);
		}
	}

	public void checkWall(int x1, int y1) {
		if (x1 >= Gdx.graphics.getWidth() - bal.getWidth()) {
			hitSound.setPitch(hitSound.play(1.0f), 2);
			xSpeed = -Math.abs(xSpeed);
			bal.setXDir(xSpeed);
		}
		if (x1 <= 0) {
			hitSound.setPitch(hitSound.play(1.0f), 2);
			xSpeed = Math.abs(xSpeed);
			bal.setXDir(xSpeed);
		}
		if (y1 <= 0) {
			bal.start = true;
			lives--;
			if(lives == 0){
				gameOver = true;
			}
		}
		if (y1 >= Gdx.graphics.getHeight()) {
			hitSound.setPitch(hitSound.play(1.0f), 2);
			bal.setYDir(-5);
		}
	}
	
	public void reset(){		
		gameOver = false;
		lives = 3;
		score = 0;
		create();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
