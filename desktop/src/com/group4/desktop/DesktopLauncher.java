package com.group4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.group4.TetrisGame;

import static com.group4.Constants.COLS;
import static com.group4.Constants.ROWS;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=COLS*100+750;
		config.height=ROWS*100;
		new LwjglApplication(new TetrisGame(), config);
	}
}
