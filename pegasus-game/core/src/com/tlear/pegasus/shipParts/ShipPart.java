package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

public abstract class ShipPart implements Part {
	
	/* Texture */
	protected Vector2 offset;	// The offset from the centre of the parent ship.
	protected Texture img;		// The base image for this texture
	protected TextureRegion tex;// The texture for this part
	
	protected Vector2 disp;		// Where in the world to display this part
	protected Vector2 origin;	// Where in the world to rotate this part around
	
	/* Model */
	protected Vector2 pos;		// The position of the ship in world space
	
	protected float angle;		// The angle that the part has.  Up is 0 deg.  Oh, yeah, in degrees.
	protected Rectangle hitbox;	// The hitbox for this part.  In world coordinates.
	
	protected float width;		// The width of the part (and by extension the texture)
	protected float height;		// The height of the part (and by extension the texture)
	
	protected Ship parent;		// The parent ship
	
	/* Maths */
	public final static float degToRad = (float) (Math.PI / 180);
	public final static float radToDeg = (float) (180 / Math.PI);
	
	public ShipPart(Vector2 offset, Ship parent) {
		// Creates a part at a given offset from a parent's centre
		// If offset = (a, b) then from here to the centre is (-a, -b)
		
		/*
		 * 
		 * 				 x (parent.centre)
		 * 	    		 |
		 * 		 		 |
		 *			 	 |
		 *				 ^ -b
		 *				 |
		 *				 |
		 *		x---->---+
		 *	(this)  -a
		 * 
		 */
		
		this.offset = new Vector2(offset);
		
		this.pos = new Vector2(parent.getPos());		// The position is offset from the centre of the parent by the offset
		this.pos.sub(offset);
		
		this.width = this.height = 0;
		
		this.angle = 0;
		
		this.hitbox = null;		// We cannot instantiate a hitbox without width or height to the part.
		
		this.img = null;				// We cannot instantiate a texture without information about the texture.
		this.tex = null;
		
		disp = origin = null;	// We cannot say anything about rendering vectors until we have an idea of the texture
		
		this.parent = parent;	// We can always set the parent.
	}
	
	public ShipPart(Vector2 offset, Ship parent, float width, float height) {
		this(offset, parent);
		
		// Now we have a width and height we can add this to the information we have.
		this.width = width;
		this.height = height;
	
		// Hitbox is largest possible square in rotated rectangle (smallest at rotation 45deg)
		// Width here is root(w^2 / 2)
		float hitboxWidth = (float) Math.sqrt(Math.pow(width, 2) / 2);
		this.hitbox = new Rectangle(pos.x + (height - width) / 2, pos.y + (height - width) / 2, hitboxWidth, hitboxWidth);
		
		// We can now define disp and origin as well
		disp = new Vector2(parent.getCentre());
		disp.add(offset);
		disp.sub(new Vector2(width / 2, height / 2));
		
		origin = new Vector2(offset);
		origin.scl(-1);
		origin.add(new Vector2(width / 2, height / 2));
	}
	
	public ShipPart(Vector2 offset, Ship parent, float width, float height, String texFileName) {
		this(offset, parent, width, height);
		// Now we have a filename for the texture we can load it.
		
		img = new Texture(Gdx.files.internal("shipParts/" + texFileName));
		tex = new TextureRegion(img);
		
		// Now everything should be declared
	}
	
	/* DRAW AND UPDATE */
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		// If we have a texture, we draw it.  Else we draw the wireframe.
		if (tex != null) {
			batch.draw(tex, disp.x, disp.y, origin.x, origin.y, width, height, 1.0f, 1.0f, parent.getAngle());
		}
	}
	
	public void update() {
		
	}

	/* MODEL FUNCTIONS */

	public void setX(float x) {
		pos.x = x;
	}

	@Override
	public void setY(float y) {
		pos.y = y;
	}

	@Override
	public float getX() {
		return pos.x;
	}

	@Override
	public float getY() {
		return pos.y;
	}

	@Override
	public void setAngle(float a) {
		angle = a;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public Rectangle getHitbox() {
		return new Rectangle(hitbox);
	}

	

	
	
	/* TEXTURE FUNCTIONS */

	public TextureRegion getTextureRegion() {
		return tex;
	}

	public float getTexWidth() {
		return width;
	}

	public float getTexHeight() {
		return height;
	}

	public Vector2 getDisp() {
		return new Vector2(disp);
	}

	public void setDispAngle(float a) {
		angle = a;
	}
	
	public float getDispAngle() {
		return angle;
	}

	
}
