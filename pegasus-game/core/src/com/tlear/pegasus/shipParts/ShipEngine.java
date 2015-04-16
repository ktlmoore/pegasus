package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;
import com.tlear.pegasus.Ship;

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
	
	protected Vector2 disp;	// The coordinates in the window at which to display this
	protected Vector2 origin;	// The coordinates in the window around which to scale/rotate this
	
	// Model
	protected Vector2 pos;		// Space coordinates
	protected Vector2 offset;	// The offset from the centre (i.e. what we add to our position to get to the centre of the ship)
	protected float angle;	// The angle the part has (relative to north)
	
	protected float maxSpeed;
	protected float thrust;
	
	protected Vector2 velocity;
	
	protected Hitbox hitbox;
	
	protected int thrustDirection;	// -1, 0, 1 for BACK, NONE, FWD
	
	protected Ship parent;	// The ship we are attached to
	
	public ShipEngine(Vector2 offset, Ship parent) {
		// Assuming no texture
		
		// Pos here is the position on the parent of the part.
		// So if it is given (a, b) then it will be -a and -b from the parent's centre.
		// Offset is (a, b).
		offset = new Vector2(offset);
		System.out.println(offset);
		
		pos = new Vector2(parent.getPos());
		
		angle = 0;
		maxSpeed = 0;
		velocity = new Vector2(0, 0);
		thrust = 0;
		hitbox = new Hitbox(pos.x, pos.y, 0, 0);
		thrustDirection = 0;
		
		img = imgFwd = imgBwd = null;
		tex = texFwd = texBwd = null;
		
		disp = origin = null;
		
		this.parent = parent;
	}
	
	public ShipEngine(Vector2 offset, float texW, float texH, Ship parent) {
		this(offset, parent);
		
		img = imgFwd = imgBwd = null;
		tex = texFwd = texBwd = null;
		
		texWidth = texW;
		texHeight = texH;
		
		disp = new Vector2(parent.getCentre());	// The display vector - where in the window to display this
		disp.add(offset);	// We subtract the offset.
		disp.sub(texWidth/2, texHeight/2);	// And then we subtract our own disp
		
		System.out.println(parent.getDisp());
		System.out.println(disp);
		
		origin = new Vector2(offset);	// The origin of rotation.  We add half the texture dimensions to find the centre from the bottom left corner
		origin.scl(-1);
		origin.add(new Vector2(texWidth/2, texHeight/2));
	}
	
	public ShipEngine(Vector2 pos, float texW, float texH, String texFileName, String texFileNameFwd, String texFileNameBwd, Ship parent) {
		this(pos, texW, texH, parent);
		
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
	
	/* DRAW AND UPDATE */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		// We draw the texture ONLY if we have a texture.  To do otherwise would be stupid
		if (tex != null) {
			
			
			batch.draw(getTextureRegion(), disp.x, disp.y, origin.x, origin.y, texWidth, texHeight, 1.0f, 1.0f, angle);
		}
	}
	public void update() {
		resetThrustDirection();
	}
	
	/* Model */
	// Set the location of the part in terms of offset of the hull they are attached to
	public void setX(float x) {
		pos.x = x;
	}
	public void setY(float y) {
		pos.y = y;
	}
	// Get the location of the part in terms of offset of the hull they are attached to
	public float getX() { 
		return pos.x;
	}
	public float getY() {
		return pos.y;
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
	private void resetThrustDirection() {
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
