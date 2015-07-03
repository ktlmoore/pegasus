package com.tlear.pegasus.display;

import com.badlogic.gdx.math.Vector2;

public class PegasusWindow {
	// A window class for the window
	public int x, y, width, height;
	public Vector2 bottomLeft;
	public Vector2 topRight;
	public PegasusWindow(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bottomLeft = new Vector2(x, y);
		this.topRight = new Vector2(x + width, y + height);
	}
	public PegasusWindow(int width, int height) {
		this(0, 0, width, height);
	}
	public PegasusWindow() {
		throw new Error("Cannot declare a window with null width or height");
	}
}
