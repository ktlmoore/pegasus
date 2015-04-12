package com.tlear.pegasus;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("serial")
public class Hitbox extends Rectangle {
	public Vector2 disp;
	
	public Hitbox() {
		super();
	}
	
	public Hitbox(float x, float y, float w, float h) {
		super(x, y, w, h);
		disp = new Vector2(x - w/2, y - w/2);
	}
}
