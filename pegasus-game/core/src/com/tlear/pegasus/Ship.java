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
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.physics.PhysicsObject;
import com.tlear.pegasus.shipParts.PartType;
import com.tlear.pegasus.shipParts.ShipPart;
import com.tlear.pegasus.shipParts.engines.BasicEngine;
import com.tlear.pegasus.shipParts.engines.BasicLeftSideEngine;
import com.tlear.pegasus.shipParts.engines.BasicRightSideEngine;
import com.tlear.pegasus.shipParts.engines.ShipEngine;
import com.tlear.pegasus.shipParts.weapons.BasicCannon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Ship extends PhysicsObject {
	private Rectangle hitBox;
	
	// Ship display position relative to centre
	private Vector2 disp;
	
	// Ship textures
	private Texture hullImg;
	private TextureRegion hullTex;
	
	// Other textures
	private BitmapFont font;
	
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
	
	// Debug
	private boolean debugMode;
	private String debugString;
	
	public Ship(int windowWidth, int windowHeight) {
		super();
		/* Load textures */
		
		hullImg = new Texture(Gdx.files.internal("shipBase/pegasus1.png"));
		hullTex = new TextureRegion(hullImg);
		
		// Load other textures
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		
		// Initialise ship texture size
		shipTexWidth = 79;
		shipTexHeight = 66;
		
		// Initalise the ship model size
		shipWidth = 79;
		shipHeight = 66;
		
		// Initialise position
		setPos(new Vector2(0, 0));
		
		// Initialise display position - centre of screen for Pegasus
		centre = new Vector2(windowWidth / 2, windowHeight / 2);
		disp = new Vector2(centre.x-shipTexWidth / 2, centre.y-shipTexHeight / 2);
		
		
		// Initialise the hitbox to always be contained inside the ship's texture
		// regardless of the rotation
		
		float hitBoxWidth = (float) Math.sqrt((Math.pow((double) shipWidth, 2.0) / 2.0));
		hitBox = new Rectangle(getPos().x, getPos().y, hitBoxWidth, hitBoxWidth);
		offX = (shipTexWidth / 2) - (hitBox.width / 2);
		offY = (shipTexHeight / 2) - (hitBox.height / 2);
		
		// Initialise parts
		initParts();
		
		// Initialise window
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		// Set debug mode
		debugMode = true;
		debugString = "";
		
		
	}
	
	private void initParts() {		// Initialise parts
		parts = new HashMap<PartType, Set<ShipPart>>();
		// Cannons
		HashSet<ShipPart> cannons = new HashSet<ShipPart>();
		cannons.add(new BasicCannon(new Vector2(0, shipTexHeight/2 - 3), this));
		// Lasers - removed 20.4.15
		//HashSet<ShipPart> lasers = new HashSet<ShipPart>();
		//lasers.add(new ShipLaser(new Vector2(0, 0), this));	// Have a standard laser cannon
		// Engines
		HashSet<ShipPart> engines = new HashSet<ShipPart>();
		engines.add(new BasicEngine(new Vector2(20 - shipTexWidth/2, shipTexHeight/2 - 50), this));
		engines.add(new BasicEngine(new Vector2(shipTexWidth/2 - 20, shipTexHeight/2 - 50), this));
		// Side Engines
		HashSet<ShipPart> leftEngines = new HashSet<ShipPart>();
		leftEngines.add(new BasicLeftSideEngine(new Vector2(22 - shipTexWidth/2, shipTexHeight/2 - 15), this));
		HashSet<ShipPart> rightEngines = new HashSet<ShipPart>();
		rightEngines.add(new BasicRightSideEngine(new Vector2(shipTexWidth/2 - 22, shipTexHeight/2 - 15), this));
		
		parts.put(PartType.CANNON, cannons);
		//parts.put(PartType.LASER, lasers);
		parts.put(PartType.ENGINE, engines);
		parts.put(PartType.LEFT_ENGINE, leftEngines);
		parts.put(PartType.RIGHT_ENGINE, rightEngines);
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
		HashSet<ShipPart> engines = new HashSet<ShipPart>();
		engines.addAll(parts.get(PartType.ENGINE));
		engines.addAll(parts.get(PartType.LEFT_ENGINE));
		engines.addAll(parts.get(PartType.RIGHT_ENGINE));
		Vector2 d = new Vector2(0, 0);
		for (ShipPart p : engines) {
			ShipEngine e = (ShipEngine) p;
			addForce(e.getForce());
		}
		
		// Update movement
		super.update();
		
		hitBox.x = getPos().x + offX;
		hitBox.y = getPos().y + offY;
	
		
		if (debugMode) {
			debugString = "Speed: " + d.len();
			debugString+= "\nAngle: " + (int) getAngle(); 
			debugString+= "\nx: " + (int) getPos().x; 
			debugString+= "\ny: " + (int) getPos().y; 
		}
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		batch.begin();
		// Draw ship
		batch.draw(hullTex, disp.x, disp.y, shipTexWidth / 2, shipTexHeight / 2, shipTexWidth, shipTexHeight, 1.0f, 1.0f, getAngle());
		
		// Draw all ship parts except lasers.  Lasers come last.
		for (Entry<PartType, Set<ShipPart>> e : parts.entrySet()) {
			for (ShipPart p : e.getValue()) {
				p.draw(batch, shapeRenderer);
			}
		}
		batch.end();
		
		// Draw debug info last always
		if (!debugMode) {
			//Draw debug info
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.identity();
			shapeRenderer.setColor(0, 1, 0, 1);
			shapeRenderer.translate(centre.x, centre.y, 0);
			shapeRenderer.rotate(0f, 0f, 1.0f, getAngle());
			shapeRenderer.rect(-hitBox.width/2, -hitBox.height/2, hitBox.width, hitBox.height);
			
			/*
			// Draw every part's hitbox in blue
			shapeRenderer.setColor(0, 0.5f, 1, 1);
			for (Entry<PartType, Set<ShipPart>> entry : parts.entrySet()) {
				for (ShipPart p : entry.getValue()) {
					shapeRenderer.rect(p.getHitbox().disp.x - shipTexWidth/2, p.getHitbox().disp.y - shipTexHeight/2, p.getHitbox().width, p.getHitbox().height);
				}
			}
			shapeRenderer.end();
			*/
			
			batch.begin();
			font.drawMultiLine(batch, debugString, 10, windowHeight-10);
			batch.end();
		}
		
		
		
	}
	
	public void addAngle(float a) {
		addTorque(a);
	}
	
	public void thrustLeft() {
		// Thrust the ship left by firing right engines
		for (ShipPart p : parts.get(PartType.RIGHT_ENGINE)) {
			((ShipEngine) p).increaseThrust();
		}
	}
	public void thrustRight() {
		// Thrust the ship right by firing left engines
		for (ShipPart p : parts.get(PartType.LEFT_ENGINE)) {
			((ShipEngine) p).increaseThrust();
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
	
	//Deprecated
	/*public void fireLasers(Vector3 pos) {
		// Fires the ship's laser at the mouse
		
		Vector2 laserTarget = new Vector2(pos.x, pos.y);
		
		// First, get the laser
		for (ShipPart p : parts.get(PartType.LASER)) {
			
			// Convert each ship part to laser - safe because we only add lasers to the LASER in map
			ShipLaser l = (ShipLaser) p;
			
			// Fire each laser at the target 
			l.fireAt(laserTarget);
		}
		
		
	}*/
	
	public void reset() {

	}
	
	public void stopMoving() {
		zeroForce();
		zeroTorque();
	}
	
	public void dispose() {
		font.dispose();
	}
	
	public Vector2 getDisp() {
		return new Vector2(disp);
	}
	
	public Vector2 getCentre() {
		return new Vector2(centre);
	}
	
	
}
