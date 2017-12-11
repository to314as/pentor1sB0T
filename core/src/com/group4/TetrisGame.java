package com.group4;

import com.badlogic.gdx.Game;


public class TetrisGame extends Game {
	@Override
	public void create() {
		showStartScreen();
	}
	public void showGameScreen() {
		setScreen(new GameScreen(this));
	}
	public void showStartScreen() {
		setScreen(new StartScreen(this));
	}
}