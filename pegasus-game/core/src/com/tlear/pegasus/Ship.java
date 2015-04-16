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
import com.tlear.pegasus.shipParts.BasicCannon;
import com.tlear.pegasus.shipParts.BasicEngine;
import com.tlear.pegasus.shipParts.PartType;
import com.tlear.pegasus.shipParts.ShipCannon;
import com.tlear.pegasus.shipParts.ShipEngine;
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
	
	// Ship display position relative to centre
	private Vector2 disp;
	
	// Ship textures
	private Texture hullImg;
	private TextureRegion hullTex;
	//private HashMap<ShipDirection, Texture> shipImages;
	//private HashMap<ShipDirection, TextureRegion> shipTextures;
	
	// Other textures
	private BitmapFont font;
	
	// Ship movement model
	private float shipAngle;
	
	public float rotationalVelocity;
	
	// Constraints
	private int maxRotationalVelocity;
	
	// Ship texture size
	private int shipTexWidth;
	private int shipTexHeight;
	
	// Centre coordinates
	private Vector2 centre;

	
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
		
		hullImg = new Texture(Gdx.files.internal("shipBase/ship.png"));
		hullTex = new TextureRegion(hullImg);
		
		// Load other textures
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		
		
		// Initialise rotation
		shipAngle = 0f;
		rotationalVelocity = 0f;
		
		
		//shipDirection = ShipDirection.NONE;
		
		// Initialise ship texture size
		shipTexWidth = 95;
		shipTexHeight = 108;
		
		// Initalise the ship model size
		shipWidth = 60;
		shipHeight = 100;
		
		// Initialise position
		x = windowWidth / 2 - shipTexWidth / 2;
		y = windowHeight / 2 - shipTexHeight / 2;
		
		centre = new Vector2(windowWidth / 2, windowHeight / 2);
		
		// Initialise display position - centre of screen for Pegasus
		disp = new Vector2(centre.x-shipTexWidth / 2, centre.y-shipTexHeight / 2);
		
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
		// Cannons
		HashSet<ShipPart> cannons = new HashSet<ShipPart>();
		cannons.add(new BasicCannon(new Vector2(shipTexWidth / 2, shipTexHeight - 6), this));
		// Lasers
		HashSet<ShipPart> lasers = new HashSet<ShipPart>();
		lasers.add(new ShipLaser(new Vector2(shipTexWidth / 2, shipTexHeight / 2), this));	// Have a standard laser cannon
		// Engines
		HashSet<ShipPart> engines = new HashSet<ShipPart>();
		engines.add(new BasicEngine(new Vector2(20 - shipTexWidth/2, shipTexHeight/2 - 78), this));
		engines.add(new BasicEngine(new Vector2(shipTexWidth/2 - 20, shipTexHeight/2 - 78), this));
		
		parts.put(PartType.CANNON, cannons);
		parts.put(PartType.LASER, lasers);
		parts.put(PartType.ENGINE, engines);
		
		laserTarget = new Vector2();
		
		// Initialise window
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		// Initialise constraints
		maxRotationalVelocity = 2;
		
		// Set debug mode
		debugMode = true;
		debugString = "";
		
		
	}
	
	// UPDATE
	
	public void update() {
		// Update every single part
		for (Entry<PartType, Set<ShipPart>> e : parts.entrySet()) {
			for (ShipPart p : e.getValue()) {
				p.update();
			}
		}
		
		/* Move the ship */
		// Sum the velocities of the engines!
		Vector2 d = new Vector2(0, 0);
		for (ShipPart p : parts.get(PartType.ENGINE)) {
			d.add(((ShipEngine) p).getVelocity());
		}
		// Scale by delta
		d.scl(Gdx.graphics.getDeltaTime());
			
		// Update ship location
		x += d.x;
		y += d.y;
		
		shipAngle += rotationalVelocity;
		// Rotate all the parts
		for (Entry<PartType, Set<ShipPart>> entry : parts.entrySet()) {
			for (ShipPart p : entry.getValue()) {
				p.setDispAngle(p.getDispAngle() + rotationalVelocity);
			}
		}
		
		hitBox.x = x + offX;
		hitBox.y = y + offY;
	
		
		if (debugMode) {
			debugString = "Speed: " + d.len();
			debugString+= "\nAngle: " + (int) shipAngle; 
			debugString+= "\nx: " + (int) x; 
			debugString+= "\ny: " + (int) y;
			debugString+= "\nRotVel: " + (double) ((int) (rotationalVelocity*100)) / 100 + "º"; 
			debugString+= "\nLaserTarget: " + laserTarget.toString();
		}
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		batch.begin();
		// Draw ship
		batch.draw(hullTex, disp.x, disp.y, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, shipAngle);
		// Draw all ship parts except lasers.  Lasers come last.
		for (Entry<PartType, Set<ShipPart>> e : parts.entrySet()) {
			if (e.getKey() != PartType.LASER) {
				for (ShipPart p : e.getValue()) {
					p.draw(batch, shapeRenderer);
				}
			}
		}
		batch.end();
		
		
		shapeRenderer.begin(ShapeType.Line);
		//Draw lasers
		HashSet<ShipPart> shipLasers = new HashSet<ShipPart>(parts.get(PartType.LASER));
		for (ShipPart p : shipLasers) {
			// Convert each part into laser - safe
			ShipLaser l = (ShipLaser) p;
			if (l.isFiring()) {
				shapeRenderer.setColor(1, 0, 0, 1);
				shapeRenderer.identity();
				
				// Calculate where to fire laser from
				Vector2 tmp = new Vector2(disp);
				tmp.add(l.getFiringOrigin());
				tmp.add(centre);
				shapeRenderer.line(tmp, laserTarget);
			}
		}
		shapeRenderer.end();
		
		// Draw all laser parts after lasers
		batch.begin();
		for (ShipPart p : shipLasers) {
			// Convert each part into a laser again - safe
			p.draw(batch, shapeRenderer);
		}
		
		
		batch.end();
		
		// Draw debug info last always
		if (!debugMode) {
			//Draw debug info
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.identity();
			shapeRenderer.setColor(0, 1, 0, 1);
			shapeRenderer.translate(centre.x, centre.y, 0);
			shapeRenderer.rotate(0f, 0f, 1.0f, shipAngle);
			shapeRenderer.rect(-hitBox.width/2, -hitBox.height/2, hitBox.width, hitBox.height);
			
			// Draw every part's hitbox in blue
			shapeRenderer.setColor(0, 0.5f, 1, 1);
			for (Entry<PartType, Set<ShipPart>> entry : parts.entrySet()) {
				for (ShipPart p : entry.getValue()) {
					shapeRenderer.rect(p.getHitbox().disp.x - shipTexWidth/2, p.getHitbox().disp.y - shipTexHeight/2, p.getHitbox().width, p.getHitbox().height);
				}
			}
			shapeRenderer.end();
			
			batch.begin();
			font.drawMultiLine(batch, debugString, 10, windowHeight-10);
			batch.end();
		}
		
		
		
	}
	
	public void addAngle(float a) {
		if (Math.abs(rotationalVelocity + a) <= maxRotationalVelocity) {
			rotationalVelocity += a;
			//shipDirection = a > 0 ? ShipDirection.LEFT : ShipDirection.RIGHT;
		}
	}
	
	public void addSpeed() {
		for (ShipPart p : parts.get(PartType.ENGINE)) {
			((ShipEngine) p).increaseThrust();
		}
		//shipDirection = ShipDirection.FORWARD;
	}

	public void reduceSpeed() {
		for (ShipPart p : parts.get(PartType.ENGINE)) {
			((ShipEngine) p).decreaseThrust();
		}
		//shipDirection = ShipDirection.BACKWARD;
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
			l.fireAt(new Vector2(pos.x - centre.x, pos.y - centre.y));
		}
		
		
	}
	
	public void reset() {
		// Set no direction
		//shipDirection = ShipDirection.NONE;
	}
	
	public void stopMoving() {
		/*
		for (ShipPart p : parts.get(PartType.ENGINE)) {
			((ShipEngine) p).zero();
		}
		*/
		rotationalVelocity = 0;
		//shipDirection = ShipDirection.NONE;
	}
	
	public void dispose() {
		font.dispose();
	}
	
	public Vector2 getPos() {
		return new Vector2(x, y);
	}
	
	public Vector2 getDisp() {
		return new Vector2(disp);
	}
	
	public Vector2 getCentre() {
		return new Vector2(centre);
	}
	
	
}
