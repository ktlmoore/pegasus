package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BasicEngine extends ShipEngine implements ShipPart {

	/* Texture */
	private Texture img;
	private Texture imgFwd;
	private Texture imgBwd;
	private TextureRegion tex;
	private TextureRegion texFwd;
	private TextureRegion texBwd;
	
	private float texWidth;
	private float texHeight;
	
	private Vector2 disp;
	private float dispAngle;
	
	
	
	public BasicEngine(Vector2 pos) {
		super(pos);
		
		img = new Texture(Gdx.files.internal("basicEngine.png"));
		imgFwd = new Texture(Gdx.files.internal("basicEngineForward.png"));
		imgBwd = new Texture(Gdx.files.internal("basicEngineBackward.png"));
		tex = new TextureRegion(img);
		texFwd = new TextureRegion(imgFwd);
		texBwd = new TextureRegion(imgBwd);
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 5;
		
		texWidth = 15;
		texHeight = 69;
		
		dispAngle = angle;
		
		disp = new Vector2(x - texWidth / 2, y - texHeight / 2);
		
	}
	@Override
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
		angle = dispAngle = a;
	}

	@Override
	public float getDispAngle() {
		return dispAngle;
	}

}
