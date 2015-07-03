package com.tlear.pegasus.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.physics.PhysicsObject;
import com.tlear.pegasus.display.PegasusWindow;

public abstract class LinearProjectile extends PhysicsObject implements Projectile {
	
	/* Texture */
	protected Texture img;
	protected TextureRegion tex;
	
	protected float texWidth;
	protected float texHeight;
	
	protected Vector2 disp;
	
	/* Model */
	
	protected int damage;
	
	public LinearProjectile() {
		/* Texture */
		img = null;
		tex = null;
		texWidth = texHeight = 0;
		disp = null;
		
		/* Model */
		
		damage = 0;
	}
	
	public LinearProjectile(Vector2 pos, Vector2 vel) {
		this();
		setPos(pos);
		setVelocity(vel);
	}
	
	public LinearProjectile(Vector2 pos, Vector2 vel, float texW, float texH, String texFileName) {
		this(pos, vel);
		texWidth = texW;
		texHeight = texH;
		
		img = new Texture(Gdx.files.internal("projectiles/" + texFileName));
		tex = new TextureRegion(img);
		
		disp = new Vector2(pos.x - texWidth / 2, pos.y - texHeight / 2);
	}
	
	/* UPDATE */
	public void update() {
		super.update();
		
		disp.add(getVelocity());
	}
	/* DRAW */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer, PegasusWindow window) {
		if (!isInWindow(window)) return;	// do not draw if not in the window.
		System.out.println(disp);
		batch.begin();
		batch.draw(tex, disp.x, disp.y, texWidth, texHeight);
		batch.end();
	}
	
	private boolean isInWindow(PegasusWindow window) {
		// Returns true if the object is in the window to be drawn
		if (disp.x - texWidth / 2 > window.topRight.x) return false;
		if (disp.y - texWidth / 2 > window.topRight.y) return false;
		if (disp.x + texWidth / 2 < window.bottomLeft.x) return false;
		if (disp.y + texWidth / 2 < window.bottomLeft.y) return false;
		
		return true;
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
	public int getDamage() {
		return damage;
	}
}
