package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ShipCannon extends ShipWeapon implements ShipPart {
	
	/* Texture info */
	protected Texture img;
	protected TextureRegion tex;
	protected float texWidth;
	protected float texHeight;
	
	protected Vector2 disp;
	
	public ShipCannon(Vector2 pos) {
		super(pos);
		
		texWidth = 0;
		texHeight = 0;
		
		disp = new Vector2();
		
		img = null;
		tex = null;
	}
	
	public ShipCannon(Vector2 pos, float texW, float texH) {
		super(pos);
		
		texWidth = texW;
		texHeight = texH;
		
		disp = new Vector2(x - texW/2, y - texH/2);
		
		img = null;
		tex = null;
	}
	
	public ShipCannon(Vector2 pos, float texW, float texH, String texFileName) {
		super(pos);
		
		texWidth = texW;
		texHeight = texH;
		
		disp = new Vector2(x - texW/2, y - texH/2);
		
		img = new Texture(Gdx.files.internal("shipParts/" + texFileName));
		tex = new TextureRegion(img);
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
