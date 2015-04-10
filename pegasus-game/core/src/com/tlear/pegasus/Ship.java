package com.tlear.pegasus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Ship {
	private Rectangle hitBox;
	private float x;
	private float y;
	
	// Ship textures
	private Texture shipImage;
	private TextureRegion shipTexture;
	
	// Other textures
	private BitmapFont font;
	
	// Ship movement model
	private float shipAngle;
	private float shipSpeed;
	
	// Ship texture size
	private int shipTexWidth;
	private int shipTexHeight;
	
	// Ship model size
	private int shipWidth;
	private int shipHeight;
	
	// Stage size
	private int windowWidth;
	private int windowHeight;
	
	// Hitbox offsets
	private float offX;
	private float offY;
	
	// Debug
	private boolean debugMode;
	private String debugString;
	
	public Ship(int windowWidth, int windowHeight) {
		// Load texture
		shipImage = new Texture(Gdx.files.internal("ship.png"));
		shipTexture = new TextureRegion(shipImage);
		
		// Load other textures
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		
		
		// Initialise speed and rotation
		shipSpeed = 0f;
		shipAngle = 0f;
		
		// Initialise position
		x = 50;
		y = 50;
		
		// Initialise ship texture size
		shipTexWidth = 95;
		shipTexHeight = 108;
		
		// Initalise the ship model size
		shipWidth = 60;
		shipHeight = 100;
		
		// Initialise the hitbox to always be contained inside the ship's texture
		// regardless of the rotation
		hitBox = new Rectangle();
		hitBox.width = hitBox.height = (float) Math.sqrt((Math.pow((double) shipWidth, 2.0) / 2.0));
		offX = (shipTexWidth / 2) - (hitBox.width / 2);
		offY = (shipTexHeight / 2) - (hitBox.height / 2);
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		// Set debug mode
		debugMode = true;
		debugString = "";
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		// PRE: batch must have begun
		batch.begin();
		batch.draw(shipTexture, x, y, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, shipAngle-90);
		if (debugMode) {
			font.drawMultiLine(batch, debugString, 10, windowHeight-10);
		}
		batch.end();
		
		//Draw debug info
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(0, 1, 0, 1);
		shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		shapeRenderer.end();

		double dx = shipSpeed * Math.cos(degreesToRadians(shipAngle)) * Gdx.graphics.getDeltaTime();
		double dy = shipSpeed * Math.sin(degreesToRadians(shipAngle)) * Gdx.graphics.getDeltaTime();
		
		x += dx;
		y += dy;
		
		checkOutOfBounds();
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
		
		if (debugMode) {
			debugString = "Speed: " + shipSpeed + "\nAngle: " + shipAngle + "\nx: " + (int) x + "\ny: " + (int) y;
		}
		
	}
	
	public void addAngle(int a) {
		shipAngle += a;
	}
	
	public void addSpeed(int s) {
		shipSpeed += s;
	}
	
	public void dispose() {
		font.dispose();
	}
	
	
	private double degreesToRadians(float deg) {
		return deg * Math.PI / 180;
	}
	
	
	private void checkOutOfBounds() {
		if (x > windowWidth - shipWidth/2) {
			x = -shipWidth/2;
		} else if (x < -shipWidth/2) {
			x = windowWidth - shipWidth/2;
		}
		if (y > windowHeight - shipHeight/2) {
			y = -shipHeight/2;
		} else if (y < -shipHeight/2) {
			y = windowHeight - shipHeight/2;
		}
	}

	
}
