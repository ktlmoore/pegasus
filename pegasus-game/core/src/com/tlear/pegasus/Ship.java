package com.tlear.pegasus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class Ship {
	private Hitbox hitBox;
	// Ship model position
	private float x;
	private float y;
	
	// Ship display position
	private float dispX;
	private float dispY;
	
	// Ship textures
	private HashMap<ShipDirection, Texture> shipImages;
	private HashMap<ShipDirection, TextureRegion> shipTextures;
	
	// Other textures
	private BitmapFont font;
	
	// Ship movement model
	private float shipAngle;
	private float shipSpeed;
	
	private ShipDirection shipDirection;
	public float rotationalVelocity;
	
	// Constraints
	private int maxSpeed;
	private int maxRotationalVelocity;
	
	// Ship texture size
	private int shipTexWidth;
	private int shipTexHeight;

	
	// Ship model size
	private int shipWidth;
	private int shipHeight;
	
	private Vector2 shipCentre;
	
	// Stage size
	private int windowWidth;
	private int windowHeight;
	
	// Hitbox offsets
	private float offX;
	private float offY;
	
	// Laser variables
	private boolean firingLaser;
	private Vector2 laserTarget;
	
	// Parts
	private ShipLaser laserTurret;
	
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
		rotationalVelocity = 0f;
		
		shipDirection = ShipDirection.NONE;
		
		// Initialise ship texture size
		shipTexWidth = 95;
		shipTexHeight = 108;
		
		// Initalise the ship model size
		shipWidth = 60;
		shipHeight = 100;
		
		// Initialise position
		x = windowWidth / 2 - shipTexWidth / 2;
		y = windowHeight / 2 - shipTexHeight / 2;
		
		// Initialise display position - centre of screen for Pegasus
		dispX = x;
		dispY = y;
		
		shipCentre = new Vector2(x + shipTexWidth / 2, y + shipTexHeight / 2);
		
		// Initialise the hitbox to always be contained inside the ship's texture
		// regardless of the rotation
		hitBox = new Hitbox();
		hitBox.width = hitBox.height = (float) Math.sqrt((Math.pow((double) shipWidth, 2.0) / 2.0));
		offX = (shipTexWidth / 2) - (hitBox.width / 2);
		offY = (shipTexHeight / 2) - (hitBox.height / 2);
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
		hitBox.dispX = hitBox.x;
		hitBox.dispY = hitBox.y;
		
		// Initialise parts
		laserTurret = new ShipLaser(new Vector2(shipTexWidth / 2, shipTexHeight / 2));
		laserTurret.addX(-laserTurret.getTexWidth() / 2);
		laserTurret.addY(-laserTurret.getTexHeight() / 2);
		
		// Initialise window
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		// Initialise constraints
		maxSpeed = 200;
		maxRotationalVelocity = 2;
		
		// Initialise laser
		firingLaser = false;
		laserTarget = new Vector2();
		
		// Set debug mode
		debugMode = true;
		debugString = "";
		
		
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		batch.begin();
		// Draw ship
		batch.draw(shipTextures.get(shipDirection), dispX, dispY, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, shipAngle);
		batch.end();
		
		
		shapeRenderer.begin(ShapeType.Line);
		//Draw laser
		if (firingLaser) {
			shapeRenderer.setColor(1, 0, 0, 1);
			shapeRenderer.line(new Vector2(x + shipTexWidth / 2, y + shipTexHeight / 2), laserTarget);
		}
		shapeRenderer.end();
		
		// Draw laser turret after laser
		batch.begin();
		batch.draw(laserTurret.getTextureRegion(), dispX + laserTurret.getX(), dispY + laserTurret.getY(), laserTurret.getTexWidth() / 2, laserTurret.getTexHeight() / 2, laserTurret.getTexWidth(), laserTurret.getTexHeight(), 1.0f, 1.0f, laserTurret.getAngle());
		batch.end();
		
		// Draw debug info last always
		if (debugMode) {
			//Draw debug info
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(0, 1, 0, 1);
			shapeRenderer.rect(hitBox.dispX, hitBox.dispY, hitBox.width, hitBox.height);
			shapeRenderer.end();
			
			batch.begin();
			font.drawMultiLine(batch, debugString, 10, windowHeight-10);
			batch.end();
		}
		
		
		// Move the ship

		double dx = shipSpeed * Math.cos(degreesToRadians(shipAngle+90)) * Gdx.graphics.getDeltaTime();
		double dy = shipSpeed * Math.sin(degreesToRadians(shipAngle+90)) * Gdx.graphics.getDeltaTime();
		
		x += dx;
		y += dy;
		
		shipAngle += rotationalVelocity;
		if (!firingLaser) laserTurret.addAngle(rotationalVelocity);
		
		checkOutOfBounds();
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
		
		shipCentre = new Vector2(x + shipTexWidth / 2, y + shipTexHeight / 2);
		
		if (debugMode) {
			debugString = "Speed: " + shipSpeed;
			debugString+= "\nAngle: " + (int) shipAngle; 
			debugString+= "\nx: " + (int) x; 
			debugString+= "\ny: " + (int) y;
			debugString+= "\nRotVel: " + (double) ((int) (rotationalVelocity*100)) / 100 + "º"; 
			debugString+= "\nFiring: " + firingLaser;
			debugString+= "\nTarget: " + laserTarget.toString();
			debugString+= "\nTurret Angle: " + laserTurret.getAngle();
		}
		
	}
	
	public void addAngle(float a) {
		if (Math.abs(rotationalVelocity + a) <= maxRotationalVelocity) {
			rotationalVelocity += a;
			shipDirection = a > 0 ? ShipDirection.LEFT : ShipDirection.RIGHT;
		}
	}
	
	public void addSpeed(int s) {
		if (shipSpeed + s <= maxSpeed && shipSpeed + s >= -maxSpeed / 2) {
			shipSpeed += s;
			shipDirection = s > 0 ? ShipDirection.FORWARD : ShipDirection.BACKWARD;
		} else {
			shipDirection = shipDirection != ShipDirection.NONE ? shipDirection : ShipDirection.NONE;
		}
	}
	
	public void fire(Vector3 pos) {
		// Fires the ship's laser at the position
		firingLaser = true;
		laserTarget = new Vector2(pos.x, pos.y);
		Vector2 laserVector = new Vector2(laserTarget);
		laserVector.sub(shipCentre);
		float a = radiansToDegrees(Math.atan(laserVector.y / laserVector.x)) - 90;
		if (laserTarget.x < shipCentre.x) a += 180;
		laserTurret.setAngle(a);
	}
	
	public void reset() {
		// Set no direction and not firing
		shipDirection = ShipDirection.NONE;
		firingLaser = false;
	}
	
	public void stopMoving() {
		shipSpeed = 0;
		rotationalVelocity = 0;
		shipDirection = ShipDirection.NONE;
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
	
	private float radiansToDegrees(double rad) {
		return (float) (rad * 180 / Math.PI);
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
