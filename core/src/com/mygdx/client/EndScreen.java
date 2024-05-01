package com.mygdx.client;


import com.badlogic.gdx.*;
    import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


class EndScreen extends ScreenAdapter {

    private boolean boundary_y_up;
    private boolean boundary_y_down;
    private Stage stage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private ParallaxLayer[] layers;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        int Help_Guides = 12;
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
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
        // Set other font parameters...
        BitmapFont font24 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;
        Label titleLabel = new Label("Summit Battle", labelStyle);
        titleLabel.setPosition(300, Gdx.graphics.getHeight() - 100);
        titleLabel.setFontScale(2f);
        stage.addActor(titleLabel);

        // Button
        Skin mySkin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));
        Button newBattleButton = new TextButton("Find a New Battle", mySkin);
        newBattleButton.setSize(col_width * 4, row_height);
        newBattleButton.setPosition(350, Gdx.graphics.getHeight() - 400);
        newBattleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add logic to switch to a new battle screen or handle the button click
            }
        });
        stage.addActor(newBattleButton);


        Button Quit = new TextButton("Quit game", mySkin);
        Quit.setSize(col_width * 4, row_height);
        Quit.setPosition(350, Gdx.graphics.getHeight() - 500);
        Quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
            }
        });
            stage.addActor(Quit);

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
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 1, 1, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        int speed = 50;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
        if (boundary_y_up) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();
        }
        if (boundary_y_down) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
        }
        boundary_y_up = 1 - background.getHeight() + 210 <= camera.position.y;
        boundary_y_down = background.getHeight() - 210 >= camera.position.y;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }
        batch.end();
        stage.act();
        stage.draw();

    }
}
