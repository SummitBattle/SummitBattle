package com.mygdx.client.screens;


import com.badlogic.gdx.*;
    import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class EndScreen extends ScreenAdapter {
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private ParallaxLayer[] layers;
    private boolean boundaryYUp;
    private boolean boundaryYDown;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        int helpGuides = 12;
        int rowHeight = Gdx.graphics.getWidth() / 12;
        int colWidth = Gdx.graphics.getWidth() / 12;
        Gdx.input.setInputProcessor(stage);
        background = new Texture(Gdx.files.internal("Background/sky.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera(background.getWidth() - 20, background.getHeight() - 10);
        createParallaxLayers();

        // Add UI elements
        addUIElements(colWidth, rowHeight);

        // Set initial camera boundaries
        boundaryYUp = false;
        boundaryYDown = false;
    }

    private void createParallaxLayers() {
        // Create parallax layers
        // (Code from your original method)
    }

    private void addUIElements(int colWidth, int rowHeight) {
        // Add UI elements such as labels and buttons
        // (Code from your original method)
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        handleInput();
        updateCamera();
        renderScene();
    }

    private void handleInput() {
        // Handle user input
        // (Code from your original render method)
    }

    private void updateCamera() {
        // Update camera position based on user input and boundaries
        // (Code from your original render method)
    }

    private void renderScene() {
        // Clear the screen
        Gdx.gl.glClearColor(255, 1, 1, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera and batch
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
    public void dispose() {
        // Dispose of resources properly to avoid memory leaks
        stage.dispose();
        batch.dispose();
        background.dispose();
        // Dispose other resources if any
    }
}
