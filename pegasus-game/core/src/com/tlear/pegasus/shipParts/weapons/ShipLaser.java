package com.tlear.pegasus.shipParts.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;

@Deprecated
public class ShipLaser extends ShipWeapon implements Part {
	/** EXTRA VARIABLES ***/
	
	// Texture

	// Model
	
	private boolean firing;
	private Vector2 target;
	
	/*** CONSTRUCTORS ***/
	
	public ShipLaser(Vector2 offset, Ship parent) {
		super(offset, parent, 21, 21, "shipLaserTurret.png"); 
	
		firing = false;
		
		target = new Vector2();
	}
	
	
	/* DRAW AND UPDATE */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.end();
		
		// We need to draw the laser!
		shapeRenderer.begin(ShapeType.Line);
		//Draw lasers
		
		if (isFiring()) {
			shapeRenderer.setColor(1, 0, 0, 1);
			shapeRenderer.identity();
		
			shapeRenderer.line(getDispFiringOrigin(), target);
		}
		shapeRenderer.end();
		
		batch.begin();
		// Then draw the turret
		super.draw(batch, shapeRenderer);
	}
	
	public void update() {
		notFiring();
	}
	
	/* Texture */
	

	/* Model */
	
	/* Weapon only */
	// Give centre of laser as origin of laser beam
	public Vector2 getDispFiringOrigin() {
		return new Vector2(disp.x + width/2, disp.y + height/2);
	}
	
	/* Laser only */
	// A method to say imma firin mah lazor
	public void fireAt(Vector2 target) {
		// target is the mouse position in the window (i.e. display vector)
		this.target = new Vector2(target);
		firing = true;
		
		// Fire at the target
		// Set the laserVector to be the vector from the turret to the mouse
		Vector2 laserVector = new Vector2(target);
		laserVector.sub(getDispFiringOrigin());
		float a = laserVector.angle();//(float) (radToDeg * ((Math.atan(laserVector.y / laserVector.x)) - 90));
		//if (target.x < disp.x + width/2) a += 180;
		setAngle(a - 90);
	}
	// A method to say if imma firin mah lazor
	public boolean isFiring() {
		return firing;
	}
	// A method to say I stop firing my laser
	private void notFiring() {
		firing = false;
	}
	// A method to say where imma firin mah lazor at
	public Vector2 getTarget() {
		return new Vector2(target);
	}

}
