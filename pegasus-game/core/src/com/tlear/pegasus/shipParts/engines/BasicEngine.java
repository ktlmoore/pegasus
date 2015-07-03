package com.tlear.pegasus.shipParts.engines;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;
import com.tlear.pegasus.shipParts.PartType;

public class BasicEngine extends ShipEngine implements Part {
	
	public BasicEngine(Vector2 pos, Ship parent) {
		super(pos, parent, 15, 69, "basicEngine.png", "basicEngineForward.png", "basicEngineBackward.png", PartType.ENGINE);
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 2;
	}
}
