package com.tlear.pegasus.shipParts.engines;

import com.badlogic.gdx.math.Vector2;
import com.tlear.pegasus.Ship;
import com.tlear.pegasus.shipParts.Part;
import com.tlear.pegasus.shipParts.PartType;

public class BasicRightSideEngine extends ShipEngine implements Part {
	public BasicRightSideEngine(Vector2 pos, Ship parent) {
		super(pos, parent, 15, 11, "basicRightSideEngine.png", "basicRightSideEngineThrust.png", "basicRightSideEngine.png", PartType.RIGHT_ENGINE);
		
		// Set max speed and thrust!
		maxSpeed = 200;
		thrust = 2;
		
		angle = 90;
	}
	
	@Override
	public void decreaseThrust() {
		// DO NOTHING
	}
}
