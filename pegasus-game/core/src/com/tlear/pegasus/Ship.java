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

import java.util.HashMap;

public class Ship {
	private Rectangle hitBox;
	private float x;
	private float y;
	
	// Ship textures
	private HashMap<ShipDirection, Texture> shipImages;
	private HashMap<ShipDirection, TextureRegion> shipTextures;
	
	// Other textures
	private BitmapFont font;
	
	// Ship movement model
	private float shipAngle;
	private float shipSpeed;
	
	private ShipDirection shipDirection;
	
	// Constraints
	private int maxSpeed;
	
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
		/* Load textures */
		
		// Set up maps of directions
		shipImages = new HashMap<ShipDirection, Texture>();
		shipImages.put(ShipDirection.NONE, new Texture(Gdx.files.internal("ship.png")));
		shipImages.put(ShipDirection.FORWARD, new Texture(Gdx.files.internal("shipForward.png")));
		shipImages.put(ShipDirection.BACKWARD, new Texture(Gdx.files.internal("shipBackward.png")));
		shipImages.put(ShipDirection.LEFT, new Texture(Gdx.files.internal("shipLeft.png")));
		shipImages.put(ShipDirection.RIGHT, new Texture(Gdx.files.internal("shipRight.png")));
		
		shipTextures = new HashMap<ShipDirection, TextureRegion>();
		shipTextures.put(ShipDirection.NONE, new TextureRegion(shipImages.get(ShipDirection.NONE)));
		shipTextures.put(ShipDirection.FORWARD, new TextureRegion(shipImages.get(ShipDirection.FORWARD)));
		shipTextures.put(ShipDirection.BACKWARD, new TextureRegion(shipImages.get(ShipDirection.BACKWARD)));
		shipTextures.put(ShipDirection.LEFT, new TextureRegion(shipImages.get(ShipDirection.LEFT)));
		shipTextures.put(ShipDirection.RIGHT, new TextureRegion(shipImages.get(ShipDirection.RIGHT)));
		
		// Load other textures
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		
		
		// Initialise speed and rotation
		shipSpeed = 0f;
		shipAngle = 0f;
		
		shipDirection = ShipDirection.NONE;
		
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
		
		// Initialise constraints
		maxSpeed = 200;
		
		// Set debug mode
		debugMode = true;
		debugString = "";
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		batch.begin();
		batch.draw(shipTextures.get(shipDirection), x, y, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, shipAngle-90);
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
		shipDirection = a > 0 ? ShipDirection.LEFT : ShipDirection.RIGHT;
	}
	
	public void addSpeed(int s) {
		if (shipSpeed + s <= maxSpeed && shipSpeed + s >= -maxSpeed / 2) {
			shipSpeed += s;
			shipDirection = s > 0 ? ShipDirection.FORWARD : ShipDirection.BACKWARD;
		} else {
			shipDirection = ShipDirection.NONE;
		}
	}
	
	public void dispose() {
		font.dispose();
	}
	
	public void setDirection(ShipDirection d) {
		shipDirection = d;
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
