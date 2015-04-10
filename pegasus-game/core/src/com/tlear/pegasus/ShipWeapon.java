package com.tlear.pegasus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ShipWeapon {
	
	public void setX();
	public void setY();
	public void setAngle();
	
	public void addX();
	public void addY();
	public void addAngle();
	
	public int getX();
	public int getY();
	public int getAngle();
	public int getWidth();
	public int getHeight();
	
	public TextureRegion getTextureRegion();
	
}
