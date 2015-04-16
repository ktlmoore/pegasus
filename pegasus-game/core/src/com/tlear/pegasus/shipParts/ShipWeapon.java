package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

public abstract class ShipWeapon extends ShipPart implements Part {
	/*** Extra variables ***/
	
	/* Texture */
	
	/* Model */
	
	/*** Constructors ***/

	public ShipWeapon(Vector2 offset, Ship parent) {
		super(offset, parent);
	}
	
	public ShipWeapon(Vector2 offset, Ship parent, float w, float h) {
		super(offset, parent, w, h);
	}
	
	public ShipWeapon(Vector2 offset, Ship parent, float w, float h, String texFileName) {
		super(offset, parent, w, h, texFileName);
	}
	
	/*** Methods ***/
	
	/* Textures */
	
	/* Model */
	
	/* Weapon only */
	// We want to know where to fire our "firing" texture from
	public Vector2 getFiringOrigin() {
		return new Vector2(pos);
	}
	
	public void fire() {
		
	}
	
}
