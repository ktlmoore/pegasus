package com.tlear.pegasus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ShipWeapon {
	
	public void setX(float x);
	public void setY(float y);
	public void setAngle(float a);
	
	public void addX(float x);
	public void addY(float y);
	public void addAngle(float a);
	
	public float getX();
	public float getY();
	public float getAngle();
	public int getWidth();
	public int getHeight();
	public float getTexWidth();
	public float getTexHeight();
	
	public TextureRegion getTextureRegion();
	
}
