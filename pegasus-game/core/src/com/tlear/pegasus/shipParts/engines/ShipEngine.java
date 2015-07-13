package com.tlear.pegasus.shipParts.engines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;
import com.tlear.pegasus.shipParts.ShipPart;
import com.tlear.pegasus.shipParts.PartType;

public abstract class ShipEngine extends ShipPart implements Part {
	// Extra variables
	
	/* Texture */
	protected Texture imgFwd;
	protected Texture imgBwd;

	protected TextureRegion texFwd;
	protected TextureRegion texBwd;
	protected TextureRegion texNeutral;

	/* Model */
	
	protected float maxSpeed;
	protected float thrust;
	
	protected Vector2 force;
	
	protected int thrustDirection;	// -1, 0, 1 for BACK, NONE, FWD
	
	public ShipEngine(Vector2 offset, Ship parent, PartType type) {
		super(offset, parent, type);
		// Assuming no texture
		
		maxSpeed = 0;
		force = new Vector2(0, 0);
		thrust = 0;
		thrustDirection = 0;
		
		imgFwd = imgBwd = null;
		texFwd = texBwd = null;
	}
	
	public ShipEngine(Vector2 offset, Ship parent, float w, float h, PartType type) {
		super(offset, parent, w, h, type);
		
		maxSpeed = 0;
		force = new Vector2(0, 0);
		thrust = 0;
		thrustDirection = 0;
		
		imgFwd = imgBwd = null;
		texFwd = texBwd = null;
	}
	
	public ShipEngine(Vector2 offset, Ship parent, float w, float h, String texFileName, String texFileNameFwd, String texFileNameBwd, PartType type) {
		super(offset, parent, w, h, texFileName, type);
		
		maxSpeed = 0;
		force = new Vector2(0, 0);
		thrust = 0;
		thrustDirection = 0;
		
		imgFwd = new Texture(Gdx.files.internal("shipParts/" + texFileNameFwd));
		imgBwd = new Texture(Gdx.files.internal("shipParts/" + texFileNameBwd));
		
		texFwd = new TextureRegion(imgFwd);
		texBwd = new TextureRegion(imgBwd);
		texNeutral = new TextureRegion(tex);
	}
	
	/* Textures */
	@Override
	public TextureRegion getTextureRegion() {
		switch (thrustDirection) {
		case -1:
			return texBwd;
		case 0:
			return texNeutral;
		case 1:
			return texFwd;
		default:
			throw new Error("Invalid Texture in basicEngine");
		}
	}
	
	/* DRAW AND UPDATE */
	@Override
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		// We draw as normal, but we need to update Tex first
		tex = getTextureRegion();
		//System.out.println(thrustDirection);
		super.draw(batch, shapeRenderer);
	}
	
	@Override
	public void update() {
		//super.update();
		thrust(thrustDirection);
		resetThrustDirection();
	}
	
	/* Model */
	
	/* Engine only */
	// Fire engine in a direction
	public void thrust(int sign) {
		// Add force in the direction of the thrust or against it
		force = new Vector2(-1 * (float) Math.sin(degToRad * (parent.getAngle() + angle)), (float) Math.cos(degToRad * (parent.getAngle() + angle)));	// Normalised vector
		
		force.scl(thrust);	// Scale by thrust value
		force.scl(sign);	// Scale by direction
	}
	public void increaseThrust() {
		thrustDirection = 1;
	}
	public void decreaseThrust() {
		thrustDirection = -1;
	}
	private void resetThrustDirection() {
		thrustDirection = 0;
	}
	public void zero() {
		force = new Vector2(0, 0);
		thrustDirection = 0;
	}
	
	// Getters
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public Vector2 getForce() {
		return new Vector2(force);
	}
}
