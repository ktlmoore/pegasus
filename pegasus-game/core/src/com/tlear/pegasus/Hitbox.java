package com.tlear.pegasus;

import com.badlogic.gdx.math.Rectangle;

@SuppressWarnings("serial")
public class Hitbox extends Rectangle {
	public float dispX;
	public float dispY;
	
	public Hitbox(float x, float y, float w, float h) {
		super(x, y, w, h);
	}
}
