package com.tlear.pegasus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pegasus extends ApplicationAdapter {
	
	BitmapFont font;
	
	// Camera init
	private OrthographicCamera camera;
	
	SpriteBatch batch;
	
	// Texture init
	private Texture shipImage;
	
	// Model init
	private Rectangle ship;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Initialise textures
		shipImage = new Texture(Gdx.files.internal("ship.png"));
		
		// Initialise model
		ship = new Rectangle();
		ship.width = 95;
		ship.height = 108;
		
		ship.x = 50;
		ship.y = 50;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera.
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Render the ship
		batch.draw(shipImage, ship.x, ship.y);
		batch.end();
		
		// Checking for keyboard input
		if (Gdx.input.isKeyPressed(Keys.A)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.D)) ship.x += 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.S)) ship.y -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.W)) ship.y += 200 * Gdx.graphics.getDeltaTime();
		
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
