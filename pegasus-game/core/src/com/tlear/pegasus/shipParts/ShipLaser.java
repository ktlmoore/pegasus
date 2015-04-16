package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

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
	// draw inherits
	
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
		this.target = new Vector2(target);
		firing = true;
		
		// Fire at the target
		Vector2 laserVector = new Vector2(target);
		laserVector.sub(new Vector2(disp.x + width/2, disp.y + height/2));
		float a = (float) (radToDeg * ((Math.atan(laserVector.y / laserVector.x)) - 90));
		if (target.x < disp.x + width/2) a += 180;
		setAngle(a);
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
