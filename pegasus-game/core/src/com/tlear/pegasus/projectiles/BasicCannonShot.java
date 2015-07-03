package com.tlear.pegasus.projectiles;

import com.badlogic.gdx.math.Vector2;

public class BasicCannonShot extends LinearProjectile implements Projectile {

	public BasicCannonShot(Vector2 pos, Vector2 vel) {
		super(pos, vel, 5, 5, "basicCannonShot.png");
		damage = 5;
	}

}
