package com.tlear.pegasus.shipParts.weapons;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;
import com.tlear.pegasus.shipParts.PartType;
import com.tlear.pegasus.shipParts.ShipPart;

public abstract class ShipWeapon extends ShipPart implements Part {
	/*** Extra variables ***/
	
	/* Texture */
	
	/* Model */
	
	/*** Constructors ***/

	public ShipWeapon(Vector2 offset, Ship parent, PartType type) {
		super(offset, parent, type);
	}
	
	public ShipWeapon(Vector2 offset, Ship parent, float w, float h, PartType type) {
		super(offset, parent, w, h, type);
	}
	
	public ShipWeapon(Vector2 offset, Ship parent, float w, float h, String texFileName, PartType type) {
		super(offset, parent, w, h, texFileName, type);		
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
