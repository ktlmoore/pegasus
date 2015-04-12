package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tlear.pegasus.Hitbox;

public interface ShipPart {
	
	/* Textures */
	// Every ship part must have an associated texture region to be drawn
	// The texture is only ever managed by the part.
	public TextureRegion getTextureRegion();
	// We may want to get the size of that texture region
	public float getTexWidth();
	public float getTexHeight();
	
	/* Model */
	// Set the location of the part in terms of offset of the hull they are attached to
	public void setX(float x);
	public void setY(float y);
	// Get the location of the part in terms of offset of the hull they are attached to
	public float getY();
	public float getX();
	// We do not need any "addX or addY" as these should never be changed, only reset
	
	// Set the angle of the part in relation to the part
	// i.e. 0 degrees is up for that part (add to angle of hull when drawing)
	public void setAngle(float a);
	// Get the angle of the part in relation to that part
	public float getAngle();
	
	// Get the hitbox of the part - this is the square that denotes where the part is
	public Hitbox getHitbox();
}
