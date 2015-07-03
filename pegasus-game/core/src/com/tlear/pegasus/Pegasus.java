package com.tlear.pegasus;

import java.util.HashSet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tlear.pegasus.projectiles.BasicCannonShot;
import com.tlear.pegasus.projectiles.Projectile;
import com.tlear.pegasus.display.PegasusWindow;

public class Pegasus extends ApplicationAdapter {
	
	public Pegasus(int width, int height) {
		window = new PegasusWindow(0, 0, width, height);
	}

	private PegasusWindow window;
	
	/* Deprecated 3/6/15
	private int windowWidth;
	private int windowHeight;
	private Vector2 window;
	
	private Vector2 bottomLeft;
	*/
	
	BitmapFont font;
	
	// Camera init
	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	// Texture init
	private Background background;
	
	// Model init
	private Ship ship;
	private HashSet<Projectile> projectiles;
	
	// TEST
	private BasicCannonShot t;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		
		background = new Background(window.width, window.height);
		
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, window.width, window.height);
		
		// Initialise ship
		ship = new Ship(window.width, window.height, this);
		projectiles = new HashSet<Projectile>();
		
		// TEST
		t = new BasicCannonShot(new Vector2(0, 0), new Vector2(1, 1));
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the camera.
		camera.update();
		

		
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		// Draw
		draw();
		// Update
		update();
		// Check for input
		checkInput();

		
		
	}
	
	private void draw() {
		// Draws all objects
		/* TODO ONLY RENDER THINGS THAT ARE ON THE SCREEN!!! */
		//System.out.println("DRAW");
		// Render the ship
		background.draw(batch, shapeRenderer, ship.getPos());
		ship.draw(batch, shapeRenderer);
		
		
		// Render projectiles
		for (Projectile p : projectiles) {
			p.draw(batch, shapeRenderer, window);
		}
	}
	
	private void update() {
		// Updates all objects
		//System.out.println("UPDATE");
		// Update the ship
		ship.update();
		
		// Update the bottom left coordinates
		window.x += (ship.getVelocity().x);
		window.y += (ship.getVelocity().y);
		
		// Update projectiles
		for (Projectile p : projectiles) {
			p.update();
		}
	}
	
	private void checkInput() {
		
		// Checking for keyboard input
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			ship.addAngle(25);
		}
		if (Gdx.input.isKeyPressed(Keys.E)) {
			ship.addAngle(-25);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			ship.thrustLeft();
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			ship.thrustRight();
		}
		if (Gdx.input.isKeyPressed(Keys.S)) { 
			ship.reduceSpeed();
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			ship.addSpeed();
		}
		if (Gdx.input.isKeyPressed(Keys.X)) {
			ship.stopMoving();
		}
		
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			//touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//camera.unproject(touchPos);
			ship.testFire();
			//ship.fireLasers(touchPos);
		}
	}
	
	/* Projectile adding */
	protected void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
}
