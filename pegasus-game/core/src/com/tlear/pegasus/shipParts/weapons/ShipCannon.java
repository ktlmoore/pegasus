package com.tlear.pegasus.shipParts.weapons;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.projectiles.BasicCannonShot;
import com.tlear.pegasus.projectiles.LinearProjectile;
import com.tlear.pegasus.projectiles.Projectile;
import com.tlear.pegasus.projectiles.ProjectileType;
import com.tlear.pegasus.shipParts.Part;
import com.tlear.pegasus.shipParts.PartType;

public abstract class ShipCannon extends ShipWeapon implements Part {
	/*** Extra Variables ***/
	/* Texture info */
	
	
	/* Model info */
	protected int cooldownLimit;	// How long before we can fire again
	protected int cooldownTimer;	// How long since we last fired
	
	/* Meta info */
	protected ProjectileType projectileType;
	
	public ShipCannon(Vector2 pos, Ship parent) {
		super(pos, parent, PartType.CANNON);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
		projectileType = ProjectileType.LINEAR;
	}
	
	public ShipCannon(Vector2 pos, Ship parent, float w, float h) {
		super(pos, parent, w, h, PartType.CANNON);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
		projectileType = ProjectileType.LINEAR;
	}
	
	public ShipCannon(Vector2 pos, Ship parent, float w, float h, String texFileName) {
		super(pos, parent, w, h, texFileName, PartType.CANNON);
		cooldownLimit = cooldownTimer = 0;	// Cooldown limit and timer default to 0
		projectileType = ProjectileType.LINEAR;
	}
	
	/* DRAW AND UPDATE */
	// Draw inherits
	
	@Override
	public void update() {
		cooldownTimer++;
	}
	
	/* Texture */
	
	/* WEAPON SPECIFIC */
	@Override
	public Vector2 getFiringOrigin() {
		// Return the centre because why not
		Vector2 tmp = new Vector2(disp);
		tmp.add(new Vector2(width/2, height/2));
		return new Vector2(tmp);
	}
	
	@Override
	public void fire() {
		// First, create the projectile we're firing
		Projectile p = createProjectile(projectileType);
		parent.addProjectile(p);
		// Then give it to the parent to deal with.  All we do is fire it, then it's out of our hair
		// (8) "Out of our haaaaaiiiiiiir" (8)
	}
	
	private Vector2 getFiringVector(int s) {
		// Return the direction that the projectile is being fired in, scaled by its speed s
		Vector2 v = new Vector2((float) Math.cos(parent.getAngle()), (float) Math.sin(parent.getAngle()));
		v.nor();
		v.scl(s);
		return v;
	}
	
	/* CANNON SPECIFIC */
	private Projectile createProjectile(ProjectileType type) {
		Projectile result;
		switch (type) {
		case LINEAR:
			result = new BasicCannonShot(getFiringOrigin(), getFiringVector(1));
			break;
		case BASIC_CANNON_SHOT:
			result = new BasicCannonShot(getFiringOrigin(), getFiringVector(10));
			break;
		default:
			throw new Error("Cannot resolve the projectile type to a known type.");
		}
		return result;
	}

}
