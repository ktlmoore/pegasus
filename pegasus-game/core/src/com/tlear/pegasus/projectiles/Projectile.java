package com.tlear.pegasus.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.display.PegasusWindow;

public interface Projectile {
	
	/* Texture */
	public TextureRegion getTextureRegion();
	public float getTexWidth();
	public float getTexHeight();
	
	public Vector2 getDisp();
	
	public void update();
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer, PegasusWindow window);		// DRAW!
	
	/* Model */
	public Vector2 getPos();
	public int getDamage();	// The damage caused by the shot
}
