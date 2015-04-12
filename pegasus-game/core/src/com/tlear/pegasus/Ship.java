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
import com.tlear.pegasus.shipParts.PartType;
import com.tlear.pegasus.shipParts.ShipLaser;
import com.tlear.pegasus.shipParts.ShipPart;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Ship {
	private Hitbox hitBox;
	// Ship model position
	private float x;
	private float y;
	
	// Ship display position
	private Vector2 disp;
	
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
	
	// Stage size
	private int windowWidth;
	private int windowHeight;
	
	// Hitbox offsets
	private float offX;
	private float offY;
	
	// Parts
	private Map<PartType, Set<ShipPart>> parts;
	
	// Laser target is where mouse was last
	private Vector2 laserTarget;
	
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
		disp = new Vector2(x, y);
		
		// Initialise the hitbox to always be contained inside the ship's texture
		// regardless of the rotation
		
		float hitBoxWidth = (float) Math.sqrt((Math.pow((double) shipWidth, 2.0) / 2.0));
		hitBox = new Hitbox(x, y, hitBoxWidth, hitBoxWidth);
		offX = (shipTexWidth / 2) - (hitBox.width / 2);
		offY = (shipTexHeight / 2) - (hitBox.height / 2);
		
		hitBox.disp.x = hitBox.x + offX;
		hitBox.disp.y = hitBox.y + offY;
		
		// Initialise parts
		parts = new HashMap<PartType, Set<ShipPart>>();
		HashSet<ShipPart> lasers = new HashSet<ShipPart>();
		lasers.add(new ShipLaser(new Vector2(shipTexWidth / 2, shipTexHeight / 2)));	// Have a standard laser cannon
		
		parts.put(PartType.LASER, lasers);
		
		laserTarget = new Vector2();
		
		// Initialise window
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		// Initialise constraints
		maxSpeed = 200;
		maxRotationalVelocity = 2;
		
		// Set debug mode
		debugMode = true;
		debugString = "";
		
		
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		batch.begin();
		// Draw ship
		batch.draw(shipTextures.get(shipDirection), disp.x, disp.y, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, shipAngle);
		batch.end();
		
		
		shapeRenderer.begin(ShapeType.Line);
		//Draw lasers
		HashSet<ShipPart> shipLasers = new HashSet<ShipPart>(parts.get(PartType.LASER));
		for (ShipPart p : shipLasers) {
			// Convert each part into laser - safe
			ShipLaser l = (ShipLaser) p;
			if (l.isFiring()) {
				shapeRenderer.setColor(1, 0, 0, 1);
				
				// Calculate where to fire laser from
				Vector2 tmp = new Vector2(disp);
				tmp.add(l.getFiringOrigin());
				shapeRenderer.line(tmp, laserTarget);
			}
		}
		shapeRenderer.end();
		
		// Draw all laser parts after lasers
		batch.begin();
		for (ShipPart p : shipLasers) {
			// Convert each part into a laser again - safe
			ShipLaser l = (ShipLaser) p;
			batch.draw(l.getTextureRegion(), disp.x + l.getDisp().x, disp.y + l.getDisp().y, l.getTexWidth() / 2, l.getTexHeight() / 2, l.getTexWidth(), l.getTexHeight(), 1.0f, 1.0f, l.getDispAngle());
		}
		
		
		batch.end();
		
		// Draw debug info last always
		if (debugMode) {
			//Draw debug info
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(0, 1, 0, 1);
			shapeRenderer.rect(hitBox.disp.x, hitBox.disp.y, hitBox.width, hitBox.height);
			
			// Draw every part's hitbox in blue
			shapeRenderer.setColor(0, 0.5f, 1, 1);
			for (Entry<PartType, Set<ShipPart>> entry : parts.entrySet()) {
				for (ShipPart p : entry.getValue()) {
					shapeRenderer.rect(disp.x + p.getHitbox().disp.x, disp.y + p.getHitbox().disp.y, p.getHitbox().width, p.getHitbox().height);
				}
			}
			shapeRenderer.end();
			
			batch.begin();
			font.drawMultiLine(batch, debugString, 10, windowHeight-10);
			batch.end();
		}
		
		
		/* Check for weapon resets */
		for (ShipPart p : parts.get(PartType.LASER)) {
			((ShipLaser) p).notFiring();
		}
		
		// Move the ship

		double dx = shipSpeed * Math.cos(degreesToRadians(shipAngle+90)) * Gdx.graphics.getDeltaTime();
		double dy = shipSpeed * Math.sin(degreesToRadians(shipAngle+90)) * Gdx.graphics.getDeltaTime();
		
		x += dx;
		y += dy;
		
		shipAngle += rotationalVelocity;
		// Rotate all the parts
		for (Entry<PartType, Set<ShipPart>> entry : parts.entrySet()) {
			for (ShipPart p : entry.getValue()) {
				p.setDispAngle(p.getDispAngle() + rotationalVelocity);
			}
		}
		
		checkOutOfBounds();
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
	
		
		if (debugMode) {
			debugString = "Speed: " + shipSpeed;
			debugString+= "\nAngle: " + (int) shipAngle; 
			debugString+= "\nx: " + (int) x; 
			debugString+= "\ny: " + (int) y;
			debugString+= "\nRotVel: " + (double) ((int) (rotationalVelocity*100)) / 100 + "º"; 
			debugString+= "\nLaserTarget: " + laserTarget.toString();
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
	
	public void fireLasers(Vector3 pos) {
		// Fires the ship's laser at the position
		
		laserTarget = new Vector2(pos.x, pos.y);
		
		// First, get the lasers
		HashSet<ShipPart> shipLasers = new HashSet<ShipPart>(parts.get(PartType.LASER));
		for (ShipPart p : shipLasers) {
			
			// Convert each ship part to laser - safe because we only add lasers to the LASER in map
			ShipLaser l = (ShipLaser) p;
			
			// Fire each laser at the target 
			l.fireAt(new Vector2(pos.x - disp.x, pos.y - disp.y));
		}
		
		
	}
	
	public void reset() {
		// Set no direction
		shipDirection = ShipDirection.NONE;
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

	
	/* MATHS */
	private double degreesToRadians(float deg) {
		return deg * Math.PI / 180;
	}
	
	
}
