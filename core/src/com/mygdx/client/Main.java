package com.mygdx.client;

import com.badlogic.gdx.Game;

public class Main extends Game {
	private StartScreen startScreen;
	private EndScreen endScreen;

	private LoadScreen loadScreen;
	private MainScreen mainScreen;

	@Override
	public void create() {
		startScreen = new StartScreen(this);
		endScreen = new EndScreen();
		mainScreen = new MainScreen();
		loadScreen = new LoadScreen();
		setScreen(startScreen);
	}

	public void switchGameScreen() {
		setScreen(endScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
		startScreen.dispose();
		endScreen.dispose();
	}
}
