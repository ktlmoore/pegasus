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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;

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
	
	SpriteBatch batch;
	
	// Texture init
	private Texture shipImage;
	private TextureRegion shipTexture;
	
	// Model init
	private Rectangle ship;
	
	// Movement model
	private float shipAngle;
	private double shipSpeed;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, windowWidth, windowHeight);
		
		// Initialise textures
		shipImage = new Texture(Gdx.files.internal("ship.png"));
		shipTexture = new TextureRegion(shipImage);
		
		// Initialise model
		ship = new Rectangle();
		ship.width = 95;
		ship.height = 108;
		
		ship.x = 50;
		ship.y = 50;
		
		shipAngle = 0;
		shipSpeed = 0;
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
		batch.draw(shipTexture, ship.x, ship.y, ship.width / 2, ship.height / 2, ship.width, ship.height, 1.0f, 1.0f, shipAngle-90);
		batch.end();
		
		// Checking for keyboard input
		if (Gdx.input.isKeyPressed(Keys.A)) shipAngle += 1;
		if (Gdx.input.isKeyPressed(Keys.D)) shipAngle -= 1;
		if (Gdx.input.isKeyPressed(Keys.S)) shipSpeed -= 5;
		if (Gdx.input.isKeyPressed(Keys.W)) shipSpeed += 5;
		
		System.out.println(shipSpeed);
		//System.out.println("SHIPX: " + ship.x + ", SHIPY: " + ship.y);
		
		double dx = shipSpeed * Math.cos(degreesToRadians(shipAngle)) * Gdx.graphics.getDeltaTime();
		double dy = shipSpeed * Math.sin(degreesToRadians(shipAngle)) * Gdx.graphics.getDeltaTime();
		
		checkOutOfBounds();
		
		ship.x += dx;
		ship.y += dy;
		
		
	}
	
	private void checkOutOfBounds() {
		if (ship.x > windowWidth) {
			ship.x = 0;
		} else if (ship.x < 0) {
			ship.x = windowWidth;
		}
		if (ship.y > windowHeight) {
			ship.y = 0;
		} else if (ship.y < 0) {
			ship.y = windowHeight;
		}
	}
	
	private double degreesToRadians(float deg) {
		return deg * Math.PI / 180;
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
