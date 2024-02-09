package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
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
	SpriteBatch batch;
	OrthographicCamera camera;
	Sprite sprite;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("assets/badlogic.jpg"));
		sprite.setPosition(-0,0);
		sprite.setSize(100,100);
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

		deltaTime = Gdx.graphics.getDeltaTime();
		distance = distance + (20*deltaTime);


		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		sprite.draw(batch);
		batch.end();
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

