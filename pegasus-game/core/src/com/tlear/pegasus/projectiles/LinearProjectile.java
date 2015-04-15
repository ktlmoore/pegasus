package com.tlear.pegasus.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;

public abstract class LinearProjectile implements Projectile {
	
	/* Texture */
	protected Texture img;
	protected TextureRegion tex;
	
	protected float texWidth;
	protected float texHeight;
	
	protected Vector2 disp;
	
	/* Model */
	protected Vector2 pos;
	protected Vector2 velocity;
	
	protected int damage;
	protected Hitbox hitbox;
	
	public LinearProjectile() {
		/* Texture */
		img = null;
		tex = null;
		texWidth = texHeight = 0;
		disp = null;
		
		/* Model */
		pos = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
		
		damage = 0;
		hitbox = null;
	}
	
	public LinearProjectile(Vector2 pos, Vector2 vel) {
		this();
		pos = new Vector2(pos);
		velocity = new Vector2(vel);
		
		hitbox = new Hitbox(pos.x, pos.y, 0, 0);
	}
	
	public LinearProjectile(Vector2 pos, Vector2 vel, float texW, float texH, String texFileName) {
		this(pos, vel);
		texWidth = texW;
		texHeight = texH;
		
		img = new Texture(Gdx.files.internal("projectiles/" + texFileName));
		tex = new TextureRegion(img);
		
		disp = new Vector2(pos.x - texWidth / 2, pos.y - texHeight / 2);
		hitbox = new Hitbox(pos.x, pos.y, texWidth, texHeight);
	}
	
	/* UPDATE */
	public void update() {
		move(Gdx.graphics.getDeltaTime());
	}
	/* DRAW */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		System.out.println(disp);
		batch.begin();
		batch.draw(tex, disp.x, disp.y, texWidth, texHeight);
		batch.end();
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
	public Vector2 getPos() {
		return new Vector2(pos);
	}

	@Override
	public int getDamage() {
		return damage;
	}

	// Move the shot
	private void move(float delta) {
		pos.add(velocity.scl(delta));
		// We need to move the hitbox as well
		hitbox.setPosition(new Vector2(hitbox.x + hitbox.disp.x, hitbox.y + hitbox.disp.y));
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}

}
