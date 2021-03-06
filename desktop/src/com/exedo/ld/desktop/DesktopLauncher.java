package com.exedo.ld.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.exedo.ld.LudumDare;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640 * 2;
		config.height = 360 * 2;
		config.forceExit = false;
		new LwjglApplication(new LudumDare(), config);
	}
}
