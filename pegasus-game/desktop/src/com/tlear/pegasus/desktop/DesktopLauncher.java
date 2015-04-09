package com.tlear.pegasus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tlear.pegasus.Pegasus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Pegasus";
		config.width = 800;
		config.height = 480;
		
		new LwjglApplication(new Pegasus(), config);
	}
}
