package com.tlear.pegasus.shipParts.engines;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;

public class BasicEngine extends ShipEngine implements Part {
	
	public BasicEngine(Vector2 pos, Ship parent) {
		super(pos, parent, 15, 69, "basicEngine.png", "basicEngineForward.png", "basicEngineBackward.png");
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 2;
	}
}
