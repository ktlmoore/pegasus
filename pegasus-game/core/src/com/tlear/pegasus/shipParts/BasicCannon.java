package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;

public class BasicCannon extends ShipCannon implements ShipPart {
	
	public BasicCannon(Vector2 pos) {
		super(pos, 5, 13, "basicCannon.png");
	}

}
