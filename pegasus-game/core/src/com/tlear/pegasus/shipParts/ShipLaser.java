package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;

public class ShipLaser extends ShipWeapon {
	// Texture
	private Texture laserImage;
	private TextureRegion laserTexture;
	
	private float laserTexWidth;
	private float laserTexHeight;
	
	private Vector2 disp;
	private float dispAngle;

	// Model
	
	public ShipLaser(Vector2 location) {
		super(location);
		
		// init texture info 
		
		laserImage = new Texture("shipLaserTurret.png");
		laserTexture = new TextureRegion(laserImage);
		
		laserTexWidth = 21f;
		laserTexHeight = 23f;
		
		// init model info
		
		hitbox = new Hitbox(x, y, 21f, 21f);
		
		disp = new Vector2(hitbox.disp);
		dispAngle = 0;
	}
	
	@Override
	public void setAngle(float a) {
		super.setAngle(a);
		// We also need to set the angle of the display if we're firing
		dispAngle = a;
	}
	
	@Override
	public void setDispAngle(float a) {
		dispAngle = a;
	}
	
	/* Texture */
	
	public TextureRegion getTextureRegion() {
		return laserTexture;
	}
	
	public float getTexWidth() {
		return laserTexWidth;
	}
	
	public float getTexHeight() {
		return laserTexHeight;
	}
	
	public Vector2 getDisp() {
		return new Vector2(disp);
	}
	
	public float getDispAngle() {
		return dispAngle;
	}
	
	/* Weapon only */
	// Give centre of laser as origin of laser beam
	public Vector2 getDispFiringOrigin() {
		return new Vector2(x, y);
	}

}
