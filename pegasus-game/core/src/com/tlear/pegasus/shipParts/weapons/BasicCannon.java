package com.tlear.pegasus.shipParts.weapons;


import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.projectiles.ProjectileType;
import com.tlear.pegasus.shipParts.Part;

public class BasicCannon extends ShipCannon implements Part {
	
	public BasicCannon(Vector2 pos, Ship parent) {
		super(pos, parent, 5, 13, "basicCannon.png");
		projectileType = ProjectileType.BASIC_CANNON_SHOT;
	}
}
