package com.tlear.pegasus.shipParts;


import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;

public class BasicCannon extends ShipCannon implements Part {
	
	public BasicCannon(Vector2 pos, Ship parent) {
		super(pos, parent, 5, 13, "basicCannon.png");
	}
}
