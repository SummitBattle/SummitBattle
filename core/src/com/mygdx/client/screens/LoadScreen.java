package com.mygdx.client.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.client.ClientHandler;
import com.mygdx.common.ConnectedClient;


public class LoadScreen implements Screen {

    private final Game game;
    private boolean boundary_y_up;
    private boolean boundary_y_down;
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private ParallaxLayer[] layers;

    private String loadingName;
    private float stateTime;
    ClientHandler clientHandler;
    MainScreen mainscreen;



    public LoadScreen(ClientHandler clientHandler, Game game) {
        this.clientHandler = clientHandler;
        this.game = game;

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Texture(Gdx.files.internal("Background/sky.png"));
        batch = new SpriteBatch();

        // Parallax Background
        camera = new OrthographicCamera(background.getWidth() - 20, background.getHeight() - 10);
        createParallaxLayers();

        // Labels
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/pixelfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font24 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;
        loadingName = "Finding match";
        Label loadingLabel = new Label(loadingName, labelStyle);
        loadingLabel.setPosition(420, 390);
        stage.addActor(loadingLabel);

        loadingLabel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                stateTime += delta * 1000; // Increase state time by milliseconds
                if (stateTime >= 1500 && stateTime < 2300) {
                    loadingLabel.setText("Finding match.");
                } else if (stateTime >= 2300 && stateTime < 3100) {
                    loadingLabel.setText("Finding match..");
                } else if (stateTime >= 3100 && stateTime < 3500) {
                    loadingLabel.setText("Finding match...");
                } else if (stateTime >= 3500) {
                    stateTime = 0;
                }
                return false;
            }
        });
    }

    private void createParallaxLayers() {
        layers = new ParallaxLayer[8];
        layers[0] = new ParallaxLayer(background, 0f, true, false);
        layers[1] = new ParallaxLayer(new Texture("Background/far_mountains.png"), 0.2f, true, true);
        layers[2] = new ParallaxLayer(new Texture("Background/grassy_mountains.png"), 0.4f, true, false);
        layers[3] = new ParallaxLayer(new Texture("Background/clouds_mid_t.png"), 0.6f, true, false);
        layers[4] = new ParallaxLayer(new Texture("Background/clouds_mid.png"), 0.8f, true, false);
        layers[5] = new ParallaxLayer(new Texture("Background/hill.png"), 2f, true, false);
        layers[6] = new ParallaxLayer(new Texture("Background/clouds_front_t.png"), 2.5f, true, false);
        layers[7] = new ParallaxLayer(new Texture("Background/clouds_front.png"), 3f, true, false);

        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(255, 1, 1, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int speed = 50;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * delta;
        if (boundary_y_up && Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y -= speed * delta;
        if (boundary_y_down && Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y += speed * delta;

        boundary_y_up = 1 - background.getHeight() + 210 <= camera.position.y;
        boundary_y_down = background.getHeight() - 210 >= camera.position.y;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }

        ConnectedClient c1 = clientHandler.getConnectedClient1();
        ConnectedClient c2 = clientHandler.getConnectedClient2();
        String Playernumber = clientHandler.getPlayerNumber();

        if (clientHandler.getIsReady()){
            System.out.println("READY AND CHANGING SCREEN");
            mainscreen = new MainScreen(c1,c2,Playernumber);
            game.setScreen(mainscreen);
        }
        batch.end();
        stage.act(delta);
        stage.draw();


    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();

    }}