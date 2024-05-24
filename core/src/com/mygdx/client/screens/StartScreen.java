package com.mygdx.client.screens;


import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.scenes.scene2d.*;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.client.Main;
import com.mygdx.client.animations.IdleAnimation;





public class StartScreen extends ScreenAdapter {
    private Main game;
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private ParallaxLayer[] layers;
    private String playerName;
    private IdleAnimation idleAnimation;

    public StartScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = new Texture(Gdx.files.internal("Background/sky.png"));
        camera = new OrthographicCamera(background.getWidth(), background.getHeight());

        // Initialize parallax layers

        // Initialize UI components

        // Load animations and fonts

        // Setup input listeners

        // Add actors to stage
    }

    @Override
    public void render(float delta) {
        handleInput();
        update(delta);
        draw();
    }

    private void handleInput() {
        // Handle input events
    }

    private void update(float delta) {
        // Update game state
    }

    private void draw() {
        // Clear screen
        ScreenUtils.clear(0.57f, 0.77f, 0.85f, 1);

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render parallax layers
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }
        batch.end();

        // Render UI stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update viewport
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose resources
        batch.dispose();
        background.dispose();
        stage.dispose();
        // Dispose other resources
    }
}
