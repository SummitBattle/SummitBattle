package com.mygdx.client;

import com.badlogic.gdx.Game;
import com.mygdx.client.screens.StartScreen;

public class Main extends Game{
	public StartScreen getStartScreen() {
		return startScreen;
	}

	private StartScreen startScreen;


	@Override
	public void create() {
		startScreen = new StartScreen(this);


		setScreen(startScreen);

	}

	@Override
	public void dispose() {
		super.dispose();
		startScreen.dispose();
;
	}
}
