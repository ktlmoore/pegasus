package com.tlear.pegasus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ShipWeapon {
	
	public void setX(int x);
	public void setY(int y);
	public void setAngle(int a);
	
	public void addX(int x);
	public void addY(int y);
	public void addAngle(int a);
	
	public int getX();
	public int getY();
	public float getAngle();
	public int getWidth();
	public int getHeight();
	public int getTexWidth();
	public int getTexHeight();
	
	public TextureRegion getTextureRegion();
	
}
