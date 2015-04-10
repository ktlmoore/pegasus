package com.tlear.pegasus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pegasus extends ApplicationAdapter {
	
	public Pegasus(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
	}
	
	private int windowWidth;
	private int windowHeight;
	
	BitmapFont font;
	
	// Camera init
	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	// Texture init

	
	// Model init
	private Ship ship;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, windowWidth, windowHeight);
		
		
		// Initialise ship
		ship = new Ship(windowWidth, windowHeight);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera.
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		// Render the ship
		ship.draw(batch, shapeRenderer);
		
		// Checking for keyboard input
		if (Gdx.input.isKeyPressed(Keys.A)) ship.addAngle(1);
		if (Gdx.input.isKeyPressed(Keys.D)) ship.addAngle(-1);
		if (Gdx.input.isKeyPressed(Keys.S)) ship.addSpeed(-5);
		if (Gdx.input.isKeyPressed(Keys.W)) ship.addSpeed(5);
		

		
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
}
