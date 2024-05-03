package com.mygdx.client;


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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


class StartScreen extends ScreenAdapter  {
    private String PlayerName;
    private boolean boundary_y_up;
    private boolean boundary_y_down;
    Main game;
    OrthographicCamera camera;
    Label hoverlabel;
    private boolean False;
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

    public StartScreen(Main game) {
        this.game = game;
    }





@Override
    public void show () {


        batch = new SpriteBatch();
        //Spritesheet



        textureAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        animation = new Animation<>(0.09f, textureAtlas.createSprites("Idle"), Animation.PlayMode.LOOP);

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
        Button FindBattle = new TextButton("Find a battle", mySkin);
        FindBattle.setSize(COL_WIDTH * 4, ROW_HEIGHT);
        FindBattle.setPosition(10, Gdx.graphics.getHeight()-200);
        FindBattle.addListener(new ClickListener() {


            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {




            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {



            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.set(Log.LEVEL_DEBUG);
                new ClientHandler(PlayerName);
                System.out.println("sending name:" + PlayerName);




        }});
        stage.addActor(FindBattle);






        Label Name = new Label("Name:",labelStyle);
        Name.setSize(5,5);
        Name.setPosition(200,50);
        stage.addActor(Name);

        TextField textField = new TextField("Enter Name", mySkin);
        textField.setAlignment(Align.center);
        textField.setSize(300, 40);
        textField.setPosition(550, 550);
        textField.setMaxLength(10);

        TextButton confirmButton = new TextButton("Confirm", mySkin);
        confirmButton.setSize(700, -200);
        confirmButton.setPosition(350, 550);
        confirmButton.addListener(new ClickListener() {
                                      @Override
                                      public void clicked(InputEvent event, float x, float y) {
                                          PlayerName = textField.getText();

                                          // Handle the input text here
                                          Name.addAction(new Action() {
                                              @Override
                                              public boolean act(float v) {
                                                  Name.setText("Name:" + "             " + PlayerName);
                                                  return false;
                                              }
                                          });
                                      }
        });
        stage.addActor(textField);
        stage.addActor(confirmButton);







        Button Settings = new TextButton("        Settings", mySkin);
        Settings.setSize(COL_WIDTH*2 , ROW_HEIGHT);
        Settings.setPosition(10, Gdx.graphics.getHeight()-300);

        Settings.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            }
        });
        stage.addActor(Settings);

        Button Character = new TextButton("         Change Character", mySkin);
        Character.setSize(COL_WIDTH * 4, ROW_HEIGHT);
        Character.setPosition(10, Gdx.graphics.getHeight()-400);
        Character.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                hover = true;
                hoverlabel.setText("use <- or -> key");


            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hover = false;
                hoverlabel.setText(" ");


            }


        });


        stage.addActor(Character);



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
        if (hover) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
                SelectCharacter = (SelectCharacter == 1) ? 2 : 1;            }}




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
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }


        stateTime += Gdx.graphics.getDeltaTime();
        Sprite sprite = animation.getKeyFrame(stateTime, true);
        sprite.setSize(20,20);
        sprite.setPosition(-10,-70);
        //Char Render
        if (hover) {
            sprite.draw(batch);
        };
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
