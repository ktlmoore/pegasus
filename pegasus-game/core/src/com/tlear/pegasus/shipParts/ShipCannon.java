package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

public abstract class ShipCannon extends ShipWeapon implements Part {
	/*** Extra Variables ***/
	/* Texture info */
	
	
	/* Model info */
	protected int cooldownLimit;	// How long before we can fire again
	protected int cooldownTimer;	// How long since we last fired
	
	public ShipCannon(Vector2 pos, Ship parent) {
		super(pos, parent);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
	}
	
	public ShipCannon(Vector2 pos, Ship parent, float w, float h) {
		super(pos, parent, w, h);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
	}
	
	public ShipCannon(Vector2 pos, Ship parent, float w, float h, String texFileName) {
		super(pos, parent, w, h, texFileName);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
	}
	
	/* DRAW AND UPDATE */
	// Draw inherits
	
	@Override
	public void update() {
		cooldownTimer++;
	}
	
	/* Texture */
	
	/* WEAPON SPECIFIC */
	@Override
	public Vector2 getFiringOrigin() {
		// Return the centre because why not
		Vector2 tmp = new Vector2(disp);
		tmp.add(new Vector2(width/2, height/2));
		return new Vector2(tmp);
	}
	
	@Override
	public void fire() {
		
	}
	
	/* CANNON SPECIFIC */
	

}
