package com.dannylui.flappybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dannylui.flappybird.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//setting the config in the desktop launcher
		config.width = FlappyBird.WIDTH;
		config.height = FlappyBird.HEIGHT;
		config.title = FlappyBird.TITLE;

		new LwjglApplication(new FlappyBird(), config);
	}
}
