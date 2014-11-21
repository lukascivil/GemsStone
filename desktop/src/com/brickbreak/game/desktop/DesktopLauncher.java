package com.brickbreak.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brickbreak.game.BrickBreakMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 580;
		config.width = 900;
		new LwjglApplication(new BrickBreakMain(), config);
	}
}
