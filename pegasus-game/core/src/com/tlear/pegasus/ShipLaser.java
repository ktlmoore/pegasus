package com.tlear.pegasus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ShipLaser implements ShipWeapon {
	// Texture
	private Texture laserImage;
	private TextureRegion laserTexture;
	
	private int laserTexWidth;
	private int laserTexHeight;

	// Model
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private float angle;

	
	public ShipLaser(Vector2 location) {
		
		// init texture info 
		
		laserImage = new Texture("shipLaserTurret.png");
		laserTexture = new TextureRegion(laserImage);
		
		laserTexWidth = 21;
		laserTexHeight = 23;
		
		// init model info
		
		x = (int) location.x;
		y = (int) location.y;
		
		angle = 0;
		
		width = 21;
		height = 21;
			
	}
	
	// Setters, adders, getters
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAngle(int a) {
		this.angle = a;
	}

	public void addX(int x) {
		this.x += x;
	}

	public void addY(int y) {
		this.y += y;
	}

	public void addAngle(int a) {
		this.angle += a;
	}

	public int getX() {
		return x;
	}

	public int getY() {
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
	
	public int getTexWidth() {
		return laserTexWidth;
	}
	
	public int getTexHeight() {
		return laserTexHeight;
	}

}