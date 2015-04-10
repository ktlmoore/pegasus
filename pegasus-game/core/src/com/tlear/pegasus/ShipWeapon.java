package com.tlear.pegasus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ShipWeapon {
	
	public void setX(double x);
	public void setY(double y);
	public void setAngle(float a);
	
	public void addX(double x);
	public void addY(double y);
	public void addAngle(float a);
	
	public double getX();
	public double getY();
	public float getAngle();
	public int getWidth();
	public int getHeight();
	public int getTexWidth();
	public int getTexHeight();
	
	public TextureRegion getTextureRegion();
	
}
