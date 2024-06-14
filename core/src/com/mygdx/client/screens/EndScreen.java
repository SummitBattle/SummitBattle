package com.mygdx.client.screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.client.animations.IdleAnimation;


public class EndScreen extends ScreenAdapter  {

    private String PlayerName;
    private boolean boundary_y_up;
    private boolean boundary_y_down;
    Game game;
    OrthographicCamera camera;
    Label hoverlabel;
    boolean False;
    boolean labelDisplayed = False;

    private Stage stage;

    private boolean hover;
    private boolean ready;

    Texture background;

    Animation<Sprite> animation;
    float stateTime = 0;


    SpriteBatch batch;

    ParallaxLayer[] layers;

    TextureAtlas textureAtlas;
    int SelectCharacter = 1;
    IdleAnimation idleAnimation;
    StartScreen startScreen;
    String WinLose;
    Label Name;


    public EndScreen(Game game, String WinLose, StartScreen startScreen){
        this.game = game;
        this.WinLose = WinLose;
        this.startScreen = startScreen;

    }







    @Override
    public void show () {
        idleAnimation = new IdleAnimation();





        batch = new SpriteBatch();
        //Spritesheet



        //Background
        stage = new Stage(new ScreenViewport());
        int HELP_GUIDES = 12;
        int ROW_HEIGHT = Gdx.graphics.getWidth() / 12;
        int COL_WIDTH = Gdx.graphics.getWidth() / 12;
        Gdx.input.setInputProcessor(stage);
        background = new Texture((Gdx.files.internal("Background/sky.png")));


        //Display
        camera = new OrthographicCamera(background.getWidth(), background.getHeight());

        layers = new ParallaxLayer[8];

        layers[0] = new ParallaxLayer(background, 0.1f, true, false);

        layers[1] = new ParallaxLayer(new Texture("Background/far_mountains.png"), 0.6f, true, true);
        layers[2] = new ParallaxLayer(new Texture("Background/grassy_mountains.png"), 0.6f, true, false);
        layers[3] = new ParallaxLayer(new Texture("Background/clouds_mid_t.png"), 0.6f, true, false);
        layers[4] = new ParallaxLayer(new Texture("Background/clouds_mid.png"), 0.6f, true, false);
        layers[5] = new ParallaxLayer(new Texture("Background/hill.png"), 0.6f, true, false);
        layers[6] = new ParallaxLayer(new Texture("Background/clouds_front_t.png"), 0.6f, true, false);
        layers[7] = new ParallaxLayer(new Texture("Background/clouds_front.png"), 0.6f, true, false);








        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }
        //Labels
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/pixelfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 5, 0.75f);
        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;




        Label hoverlabel = new Label(" ", labelStyle);

        hoverlabel.setPosition(350,300);
        stage.addActor(hoverlabel);

        Label Title = new Label("Summit Battle",labelStyle);
        Title.setSize((float) Gdx.graphics.getWidth() /HELP_GUIDES*5,ROW_HEIGHT);
        Title.setPosition((float) Gdx.graphics.getWidth() /2 - 125,Gdx.graphics.getHeight()-100);
        stage.addActor(Title);





        //Button

        Skin mySkin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));
        Button FindBattle = new TextButton("Return to Startscreen", mySkin);
        FindBattle.setSize(COL_WIDTH * 4, ROW_HEIGHT);
        FindBattle.setPosition(320,300);
        FindBattle.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(startScreen);




            }});
        stage.addActor(FindBattle);





        if (WinLose.equals("Win")) {
            Name = new Label("You won", labelStyle);
        }
        else if (WinLose.equals("Lose")) {
            Name = new Label("You lost", labelStyle);
        }
        Name.scaleBy(3);
        Name.setPosition(440,500);
        stage.addActor(Name);










    }


    @Override
    public void resize(int width, int height) {

    }

    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render (float delta) {


        ScreenUtils.clear(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClearColor(255, 0, 0, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        //Parallax Moving

        int speed = 50;
        if (!hover) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed * Gdx.graphics.getDeltaTime();
            if (boundary_y_up) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();
            }
            if (boundary_y_down) {
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
            }}
        boundary_y_up = 1 - background.getHeight() + 210 <= camera.position.y;
        boundary_y_down = background.getHeight() - 210 >= camera.position.y;





        camera.update();
        stateTime += Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }


        batch.end();
        stage.act();
        stage.draw();



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
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
