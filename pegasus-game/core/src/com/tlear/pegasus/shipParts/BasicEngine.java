package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BasicEngine extends ShipEngine implements ShipPart {

	/* Texture */
	private Texture img;
	private TextureRegion tex;
	
	private float texWidth;
	private float texHeight;
	
	private Vector2 disp;
	private float dispAngle;
	
	public BasicEngine(Vector2 pos) {
		super(pos);
		
		img = new Texture(Gdx.files.internal("basicEngine.png"));
		tex = new TextureRegion(img);
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 5;
		
		texWidth = 15;
		texHeight = 39;
		
		dispAngle = angle;
		
		disp = new Vector2(x - texWidth / 2, y - texHeight / 2);
		
	}
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
		dispAngle = a;
	}

	@Override
	public float getDispAngle() {
		return dispAngle;
	}

}
