package com.tlear.pegasus.shipParts;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Hitbox;

public class BasicEngine extends ShipEngine implements ShipPart {
	
	public BasicEngine(Vector2 pos) {
		super(pos, 15, 69, "basicEngine.png", "basicEngineForward.png", "basicEngineBackward.png");
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 1;
		
		hitbox = new Hitbox(x, y-15, 15, 39);
		
	}

}
