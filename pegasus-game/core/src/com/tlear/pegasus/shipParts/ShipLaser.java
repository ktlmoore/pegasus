package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;
import com.tlear.pegasus.Ship;

public class ShipLaser extends ShipWeapon implements ShipPart {
	// Texture
	private Texture img;
	private TextureRegion tex;
	
	private float texWidth;
	private float texHeight;
	
	private Vector2 disp;
	private float dispAngle;

	// Model
	
	private boolean firing;
	private Vector2 target;
	
	public ShipLaser(Vector2 location, Ship parent) {
		super(location, parent);
		
		// init texture info 
		
		img = new Texture("shipParts/shipLaserTurret.png");
		tex = new TextureRegion(img);
		
		texWidth = 21f;
		texHeight = 23f;
		
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
	
	/* DRAW AND UPDATE */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		// Only draw if we have a texture
		if (tex != null) {
			// Add parent disp to disp
			Vector2 dispVec = new Vector2(disp);
			dispVec.add(parent.getDisp());
			
			// Get centre of rotation
			Vector2 origin = new Vector2(parent.getCentre());
			
			// Draw
			batch.draw(getTextureRegion(), dispVec.x, dispVec.y, origin.x, origin.y, texWidth, texHeight, 1.0f, 1.0f, dispAngle);
		}
		
		// TO DO DRAW LASER BEAM
	}
	
	public void update() {
		notFiring();
	}
	
	/* Texture */
	
	public TextureRegion getTextureRegion() {
		return tex;
	}
	
	public float getTexWidth() {
		return texWidth;
	}
	
	public float getTexHeight() {
		return texHeight;
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
		laserVector.sub(new Vector2(disp.x + texWidth/2, disp.y + texHeight/2));
		float a = radiansToDegrees(Math.atan(laserVector.y / laserVector.x)) - 90;
		if (target.x < disp.x + texWidth/2) a += 180;
		setAngle(a);
	}
	// A method to say if imma firin mah lazor
	public boolean isFiring() {
		return firing;
	}
	// A method to say I stop firing my laser
	private void notFiring() {
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
