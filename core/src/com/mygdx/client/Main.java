package com.mygdx.client;

import com.badlogic.gdx.Game;
import com.mygdx.client.screens.EndScreen;
import com.mygdx.client.screens.LoadScreen;
import com.mygdx.client.screens.MainScreen;
import com.mygdx.client.screens.StartScreen;

public class Main extends Game {
	private StartScreen startScreen;
	private EndScreen endScreen;

	private static LoadScreen loadScreen;
	private MainScreen mainScreen;
	@Override
	public void create() {

		startScreen = new StartScreen(this);
		endScreen = new EndScreen();
		mainScreen = new MainScreen();
		loadScreen = new LoadScreen();
		setScreen(mainScreen);
	}

	public void switchToLoadScreen() {setScreen(loadScreen);}

	public void switchToEndScreen() {setScreen(endScreen);}

	public void switchToMainScreen() {setScreen(mainScreen);}

	public void switchToStartScreen() {setScreen(startScreen);}

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
