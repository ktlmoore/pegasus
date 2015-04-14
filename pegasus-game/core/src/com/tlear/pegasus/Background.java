package com.tlear.pegasus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Background {
	
	/* Model */
	private Vector2 window;
	
	// Other textures
	private BitmapFont font;
	
	private int gridWidth;

	public Background(int windowWidth, int windowHeight) {
		// Load other textures
		font = new BitmapFont();
		font.setColor(Color.RED);
		
		window = new Vector2(windowWidth, windowHeight);
		
		gridWidth = 150;
	}
	// Contains information on how to draw the background
	
	public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer, Vector2 shipPos) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.setColor(1, 0, 0, 1);
		// Go to next multiple of 50
		int xoff = (int) shipPos.x % gridWidth;
		int yoff = (int) shipPos.y % gridWidth;
		for (int row = -yoff; row < window.y + yoff; row += gridWidth) {
			shapeRenderer.line(new Vector2(0, row), new Vector2(window.x, row));
		}
		for (int col = -xoff; col < window.x + xoff; col += gridWidth) {
			shapeRenderer.line(new Vector2(col, 0), new Vector2(col, window.y));
		}
		shapeRenderer.end();
		batch.begin();
		Vector2 bottomLeft = new Vector2(shipPos.x - window.x/2 + 1/3, shipPos.y - window.y/2);
		for (int row = -yoff; row < window.y + yoff; row += gridWidth) {
			String yStr = ""+Math.round((row + bottomLeft.y)/150);
			font.draw(batch, yStr, 5, row - 2);
		}
		for (int col = -xoff; col < window.x + xoff; col += gridWidth) {
			String xStr = ""+Math.round((col + bottomLeft.x)/150);
			font.draw(batch, xStr, col + 2, 20);
		}
		batch.end();
	}
}
