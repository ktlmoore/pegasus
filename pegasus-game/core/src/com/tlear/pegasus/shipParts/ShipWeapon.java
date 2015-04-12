package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;

public abstract class ShipWeapon implements ShipPart {
	// Model variables
	protected float x;
	protected float y;
	protected float angle;
	protected Hitbox hitbox;
	
	public ShipWeapon(Vector2 pos) {
		// pos is the centre of the weapon, relative to the hull we're putting it on.
		x = pos.x;
		y = pos.y;
		angle = 0;
		hitbox = new Hitbox(x, y, 0f, 0f);
	}
	
	/* Textures */
	// We do not deal with textures in the abstract class
	
	/* Model */
	// Set the location of the part in terms of offset of the hull they are attached to
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	// Get the location of the part in terms of offset of the hull they are attached to
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	// We do not need any "addX or addY" as these should never be changed, only reset
	
	// Set the angle of the part in relation to the part
	// i.e. 0 degrees is up for that part (add to angle of hull when drawing)
	public void setAngle(float a) {
		this.angle = a;
	}
	// Get the angle of the part in relation to that part
	public float getAngle() {
		return angle;
	}
	
	// Get the hitbox of the part - this is the square that denotes where the part is
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	/* Weapon only */
	// We want to know where to fire our "firing" texture from
	public Vector2 getFiringOrigin() {
		return new Vector2(x, y);
	}
	
}
