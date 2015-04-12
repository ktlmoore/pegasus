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
	
	private boolean firing;
	private Vector2 target;
	
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
		
		firing = false;
		
		target = new Vector2();
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
	
	/* Laser only */
	// A method to say imma firin mah lazor
	public void fireAt(Vector2 target) {
		this.target = new Vector2(target);
		firing = true;
		
		// Fire at the target
		Vector2 laserVector = new Vector2(target);
		laserVector.sub(new Vector2(disp.x + laserTexWidth/2, disp.y + laserTexHeight/2));
		float a = radiansToDegrees(Math.atan(laserVector.y / laserVector.x)) - 90;
		if (target.x < disp.x + laserTexWidth/2) a += 180;
		setAngle(a);
	}
	// A method to say if imma firin mah lazor
	public boolean isFiring() {
		return firing;
	}
	// A method to say I stop firing my laser
	public void notFiring() {
		firing = false;
	}
	// A method to say where imma firin mah lazor at
	public Vector2 getTarget() {
		return new Vector2(target);
	}
	
	private float radiansToDegrees(double rad) {
		return (float) (rad * 180 / Math.PI);
	}

}
