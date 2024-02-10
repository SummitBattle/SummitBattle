package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

public class main extends InputAdapter implements ApplicationListener {
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.SPACE:
				Gdx.app.exit();
				break;
		}
		return true;
	}
	private float deltaTime = 0;
	private float distance = 0;
	OrthographicCamera camera;


	private Stage stage;
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		Texture texture = new Texture((Gdx.files.internal("Background/background1.png")));
		Image background = new Image(texture);
		background.setPosition((float) Gdx.graphics.getWidth() /3-background.getWidth()/2, (float) (Gdx.graphics.getHeight() * 2) /3-background.getHeight()/2);
		stage.addActor(background);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {

	}

	// @Override
	//public void resize(int width, int height) {

	//}
	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 1, 1, 255);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		deltaTime = Gdx.graphics.getDeltaTime();
		distance = distance + (20*deltaTime);

	}




	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}}

	//@Override
	//public void pause() {

	//}

	//@Override
	//public void resume() {

	//}

	//@Override
	//public void dispose() {

	//}

