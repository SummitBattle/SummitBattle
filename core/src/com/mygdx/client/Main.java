package com.mygdx.client;

import com.badlogic.gdx.Game;
import com.mygdx.client.screens.EndScreen;
import com.mygdx.client.screens.LoadScreen;
import com.mygdx.client.screens.MainScreen;
import com.mygdx.client.screens.StartScreen;

public class Main extends Game{
	private StartScreen startScreen;
	private EndScreen endScreen;
	private LoadScreen loadScreen;
	private MainScreen mainScreen;

	@Override
	public void create() {
		startScreen = new StartScreen(this);
		endScreen = new EndScreen("Lose");

		setScreen(endScreen);

	}

	public void switchToLoadScreen() {
		setScreen(loadScreen);
	}

	public void switchToEndScreen() {
		setScreen(endScreen);
	}

	public void switchToMainScreen() {
		setScreen(mainScreen);
	}


	public void switchToStartScreen() {
		setScreen(startScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		startScreen.dispose();
		endScreen.dispose();
		mainScreen.dispose();
		loadScreen.dispose();
	}
}
