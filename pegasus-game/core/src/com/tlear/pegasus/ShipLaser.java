package com.tlear.pegasus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ShipLaser implements ShipWeapon {
	// Texture
	private Texture laserImage;
	private TextureRegion laserTexture;
	
	private float laserTexWidth;
	private float laserTexHeight;

	// Model
	private float x;
	private float y;
	
	private int width;
	private int height;
	
	private float angle;

	
	public ShipLaser(Vector2 location) {
		
		// init texture info 
		
		laserImage = new Texture("shipLaserTurret.png");
		laserTexture = new TextureRegion(laserImage);
		
		laserTexWidth = 21f;
		laserTexHeight = 23f;
		
		// init model info
		
		x = location.x;
		y = location.y;
		
		angle = 0;
		
		width = 21;
		height = 21;
			
	}
	
	// Setters, adders, getters
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setAngle(float a) {
		this.angle = a;
	}

	public void addX(float x) {
		this.x += x;
	}

	public void addY(float y) {
		this.y += y;
	}

	public void addAngle(float a) {
		this.angle += a;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getAngle() {
		return angle;
	}

	public TextureRegion getTextureRegion() {
		return laserTexture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public float getTexWidth() {
		return laserTexWidth;
	}
	
	public float getTexHeight() {
		return laserTexHeight;
	}

}
