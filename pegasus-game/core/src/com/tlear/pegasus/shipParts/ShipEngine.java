package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;

public abstract class ShipEngine implements ShipPart {
	/* Texture */
	protected Texture img;
	protected Texture imgFwd;
	protected Texture imgBwd;
	
	protected TextureRegion tex;
	protected TextureRegion texFwd;
	protected TextureRegion texBwd;
	
	protected float texWidth;
	protected float texHeight;
	
	protected Vector2 disp;
	
	// Model
	protected float x;
	protected float y;
	protected float angle;
	
	protected float maxSpeed;
	protected float thrust;
	
	protected Vector2 velocity;
	
	protected Hitbox hitbox;
	
	protected int thrustDirection;	// -1, 0, 1 for BACK, NONE, FWD
	
	public ShipEngine(Vector2 pos) {
		x = pos.x;
		y = pos.y;
		angle = 0;
		maxSpeed = 0;
		velocity = new Vector2(0, 0);
		thrust = 0;
		hitbox = new Hitbox(x, y, 0, 0);
		thrustDirection = 0;
		
		img = imgFwd = imgBwd = null;
		tex = texFwd = texBwd = null;
		
		disp = null;
	}
	
	public ShipEngine(Vector2 pos, float texW, float texH) {
		this(pos);
		
		img = imgFwd = imgBwd = null;
		tex = texFwd = texBwd = null;
		
		texWidth = texW;
		texHeight = texH;
		
		disp = new Vector2(x - texWidth/2, y - texHeight/2);
	}
	
	public ShipEngine(Vector2 pos, float texW, float texH, String texFileName, String texFileNameFwd, String texFileNameBwd) {
		this(pos, texW, texH);
		
		   img = new Texture(Gdx.files.internal("shipParts/" + texFileName));
		imgFwd = new Texture(Gdx.files.internal("shipParts/" + texFileNameFwd));
		imgBwd = new Texture(Gdx.files.internal("shipParts/" + texFileNameBwd));
		
		   tex = new TextureRegion(img);
		texFwd = new TextureRegion(imgFwd);
		texBwd = new TextureRegion(imgBwd);
	}
	
	/* Textures */
	public TextureRegion getTextureRegion() {
		switch (thrustDirection) {
		case -1:
			return texBwd;
		case 0:
			return tex;
		case 1:
			return texFwd;
		default:
			throw new Error("Invalid Texture in basicEngine");
		}
	}
	public float getTexWidth() {
		return texWidth;
	}
	public float getTexHeight() {
		return texHeight;
	}
	
	/* Model */
	// Set the location of the part in terms of offset of the hull they are attached to
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	// Get the location of the part in terms of offset of the hull they are attached to
	public float getX() { 
		return x;
	}
	public float getY() {
		return y;
	}
	// We do not need any "addX or addY" as these should never be changed, only reset
	
	// Set the angle of the part in relation to the part
	// i.e. 0 degrees is up for that part (add to angle of hull when drawing)
	public void setAngle(float a) {
		this.angle = a;
	}
	// Get the angle of the part in relation to that part
	public float getAngle() {
		 return angle;
	}
	
	// Get the hitbox of the part - this is the square that denotes where the part is
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	// Return display vector
	public Vector2 getDisp() {
		return new Vector2(disp);
	}
	

	@Override
	public void setDispAngle(float a) {
		angle = a;
	}

	@Override
	public float getDispAngle() {
		return angle;
	}
	
	/* Engine only */
	// Fire engine in a direction
	public void thrust(int sign) {
		Vector2 tmp = new Vector2(velocity);
		double dx = (sign * thrust) * Math.cos(degreesToRadians(angle+90));
		double dy = (sign * thrust) * Math.sin(degreesToRadians(angle+90));
		tmp.add(new Vector2((float) dx, (float) dy));
		if (tmp.len() <= maxSpeed) {
			velocity = new Vector2(tmp);
		}
	}
	public void increaseThrust() {
		thrust(1);
		thrustDirection = 1;
	}
	public void decreaseThrust() {
		thrust(-1);
		thrustDirection = -1;
	}
	public void resetThrustDirection() {
		thrustDirection = 0;
	}
	public void zero() {
		velocity = new Vector2(0, 0);
		thrustDirection = 0;
	}
	
	// Getters
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public Vector2 getVelocity() {
		return new Vector2(velocity);
	}
	
	/* MATHS */
	private double degreesToRadians(float deg) {
		return deg * Math.PI / 180;
	}
}
