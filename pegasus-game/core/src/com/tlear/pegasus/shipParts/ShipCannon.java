package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

public abstract class ShipCannon extends ShipWeapon implements ShipPart {
	
	/* Texture info */
	protected Texture img;
	protected TextureRegion tex;
	protected float texWidth;
	protected float texHeight;
	
	protected Vector2 disp;
	
	/* Model info */
	protected int cooldownLimit;
	protected int cooldownTimer;
	
	protected Ship parent;
	
	public ShipCannon(Vector2 pos, Ship parent) {
		super(pos, parent);
		
		texWidth = 0;
		texHeight = 0;
		
		disp = new Vector2();
		
		img = null;
		tex = null;
		
		cooldownLimit = cooldownTimer = 0;
		
		this.parent = parent;
	}
	
	public ShipCannon(Vector2 pos, float texW, float texH, Ship parent) {
		this(pos, parent);
		
		texWidth = texW;
		texHeight = texH;
		
		disp = new Vector2(x - texW/2, y - texH/2);
	}
	
	public ShipCannon(Vector2 pos, float texW, float texH, String texFileName, Ship parent) {
		this(pos, texW, texH, parent);
		
		img = new Texture(Gdx.files.internal("shipParts/" + texFileName));
		tex = new TextureRegion(img);
	}
	
	/* DRAW AND UPDATE */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		// We draw if we have a texture
		if (tex != null) {
			// Add the parent's disp to get the display coordinates
			Vector2 dispVec = new Vector2(disp);
			dispVec.add(parent.getDisp());
			
			// Work out the origin of rotation
			Vector2 origin = new Vector2(parent.getCentre());
			
			// Draw
			batch.draw(getTextureRegion(), dispVec.x, dispVec.y, origin.x, origin.y, texWidth, texHeight, 1.0f, 1.0f, angle);
		}
	}
	public void update() {
		cooldownTimer++;
	}
	
	/* Texture */
	@Override
	public TextureRegion getTextureRegion() {
		return tex;
	}

	@Override
	public float getTexWidth() {
		return texWidth;
	}

	@Override
	public float getTexHeight() {
		return texHeight;
	}

	@Override
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

}
