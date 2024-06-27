package com.mygdx.client.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.client.ClientHandler;
import com.mygdx.common.ConnectedClient;
import com.mygdx.common.PlayerInput;

public class GameWorld extends ApplicationAdapter {
    String WinLose;
    public static final float PPM = 100.0f;
    int Timer5;


    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private Viewport viewport;
    private OrthographicCamera camera;
    Player localplayer;
    Player remoteplayer;
    private Texture arena;
    private Stage stage;
    private Texture background;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float accumulator = 0;
    private float stateTime;
    private Vector2 player1StartPos = new Vector2(50 / PPM, 20 / PPM);
    private Vector2 player2StartPos = new Vector2(1200 / PPM, 20 / PPM);
    private ListenerClass listenerClass;

    ConnectedClient client1;
    ConnectedClient client2;
    String playernumber;

    CustomUserData localplayerdata;
    CustomUserData remoteplayerdata;

    ClientHandler clientHandler;
    boolean DeadHandling = true;
    Label localLabel;
    Label remoteLabel;
    String remoteName;
    String localName;

    Label.LabelStyle labelStyle;

    Vector2 remotePos;
    Vector2 localPos;

    public GameWorld(ConnectedClient client1, ConnectedClient client2, String playernumber, ClientHandler clientHandler) {
        this.client1 = client1;
        this.client2 = client2;
        this.playernumber = playernumber;
        this.clientHandler = clientHandler;


        System.out.println(playernumber);

    }

    @Override
    public void create() {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/pixelfont.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 15;
            parameter.borderWidth = 2;
            parameter.color = Color.WHITE;
            parameter.shadowOffsetX = 3;
            parameter.shadowOffsetY = 3;
            parameter.shadowColor = new Color(1,1,1,0);
            BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
            generator.dispose();

            labelStyle = new Label.LabelStyle();
            labelStyle.font = font24;




            // Initialize Box2D
            Box2D.init();
            world = new World(new Vector2(0, -18f), true);
            listenerClass = new ListenerClass();
            world.setContactListener(listenerClass);

            debugRenderer = new Box2DDebugRenderer();

            // Set up the camera and viewport
            camera = new OrthographicCamera(1200, 1000);
            viewport = new ExtendViewport(1200, 1000, camera);
            stage = new Stage(viewport);

            // Load background texture
            background = new Texture(Gdx.files.internal("Arena/BG.png"));
            background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            // Load arena texture
            arena = new Texture(Gdx.files.internal("Arena/arenaAtlas.png"));

            // Initialize the sprite batch
            batch = new SpriteBatch();

            // Logging before player creation
            Gdx.app.log("GameWorld", "Initializing players");



            // Create the players
            if (playernumber.equals("Player 1")) {

                localplayer = new Player(world,true, player1StartPos,1,clientHandler);
                remoteplayer = new Player(world,  false, player2StartPos,2,clientHandler);

                localName = client1.getName();
                remoteName = client2.getName();




            } else if (playernumber.equals("Player 2")) {
                remoteplayer = new Player(world,false, player1StartPos,1,clientHandler);
                localplayer = new Player(world,  true, player2StartPos,2,clientHandler);

                localName = client2.getName();
                remoteName = client1.getName();



            }

            localplayerdata = (CustomUserData) localplayer.getFixture().getUserData();
            remoteplayerdata = (CustomUserData) remoteplayer.getFixture().getUserData();

            // Verify player initialization
            if (localplayer == null || remoteplayer == null) {
                throw new NullPointerException("Player initialization failed");
            }

            Gdx.app.log("GameWorld", "Players initialized successfully");

            // Create arena bodies
            createRect(650, 82, world, 28, 26, true);
            createRect(0, 0, world, camera.viewportWidth, 48, true);
            createRect(355, 150, world, 46, 18, false);
            createRect(952, 145, world, 46, 18, false);
            createRect(133, 346, world, 48, 18, false);
            createRect(1129, 346, world, 47, 18, false);
            createRect(290, 535, world, 205, 16, false);
            createRect(1005, 535, world, 185, 16, false);
            createRect(648, 690, world, 92, 16, false);
            createRect(365, 820, world, 72, 21, false);
            createRect(955, 820, world, 72, 21, false);
            createRect(652, 980, world, 45, 18, false);
            createRect(0, 0, world, 1, 2500, true);
            createRect(camera.viewportWidth, 0, world, 1, 2500, true);
        } catch (Exception e) {
            Gdx.app.log("GameWorld", "Error during create", e);
            throw e;  // Rethrow the exception after logging it
        }



        remoteLabel = new Label(remoteName, labelStyle);
        remoteLabel.setPosition(0,0);
        stage.addActor(remoteLabel);

        localPos = localplayer.getFixture().getBody().getPosition();
        localLabel = new Label(localName,labelStyle);


        localLabel.setPosition(0,0);
        stage.addActor(localLabel);



    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    @Override
    public void render() {
        try {
            camera.combined.scl(PPM);
            System.out.println(DeadHandling);
            stateTime += Gdx.graphics.getDeltaTime() * 0.9;
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Render the background and arena
            batch.setProjectionMatrix(camera.combined.scl(1 / PPM));
            batch.begin();
            batch.draw(background, 0, 0, camera.viewportWidth + 400, camera.viewportHeight);
            batch.draw(arena, 0, 0, camera.viewportWidth, camera.viewportHeight);

            if (!DeadHandling) {stage.draw();}

            // Logging player rendering
            localplayer.render(stateTime, camera);
            remoteplayer.render(stateTime, camera);

            batch.end();

            stage.draw();

            // Update the world
            update();
        } catch (Exception e) {
            Gdx.app.log("GameWorld", "Error during render", e);
            throw e;  // Rethrow the exception after logging it
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        debugRenderer.dispose();
        background.dispose();
        arena.dispose();
    }


    public void update() {
        try {

            stepWorld();

            PlayerInput Enemyinput = clientHandler.getEnemyInput();

            remoteplayer.ReceiveInputs(Enemyinput.A_PRESSED, Enemyinput.W_PRESSED, Enemyinput.ENTER_PRESSED, Enemyinput.D_PRESSED);

            localplayer.update(stateTime);
            remoteplayer.update(stateTime);

            localPos = localplayer.getFixture().getBody().getPosition();
            localPos.x = (localPos.x * PPM)-40;
            localPos.y = (localPos.y * PPM)-70;

            remotePos = remoteplayer.getFixture().getBody().getPosition();
            remotePos.x = (remotePos.x * PPM)-40;
            remotePos.y = (remotePos.y * PPM)-70;



            localLabel.setPosition(localPos.x, localPos.y);
            remoteLabel.setPosition(remotePos.x, remotePos.y);


            camera.update();
            for (Body body : listenerClass.getDeletionList()) {
                world.destroyBody(body);
            }
            listenerClass.getDeletionList().clear();
        } catch (Exception e) {
            Gdx.app.log("GameWorld", "Error during update", e);
            throw e;  // Rethrow the exception after logging it
        }
        if (!localplayer.dead || !remoteplayer.dead) {
            DeadCheck();
        }
        if (DeadHandling && (localplayer.dead || remoteplayer.dead)) {

            if (Timer5 < 250)
            {
                Timer5 += 1;
            }

            if (Timer5 >= 250) {
                IfDead();
            }
        }}



    public void createRect(float x, float y, World world, float width, float height, boolean boundary) {
        try {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            def.position.set(x / PPM, y / PPM);
            def.fixedRotation = true;
            Body body = world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / PPM, height / PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            if (!boundary) {
                CustomUserData customPlatform = new CustomUserData("platform");
                body.createFixture(fixtureDef).setUserData(customPlatform);
            } else {
                CustomUserData customBoundary = new CustomUserData("boundary");
                body.createFixture(fixtureDef).setUserData(customBoundary);
            }
            shape.dispose();

        } catch (Exception e) {
            Gdx.app.log("GameWorld", "Error during createRect", e);
            throw e;  // Rethrow the exception after logging it
        }
    }

    public void DeadCheck() {
        if (localplayerdata.getHP() == 0 && !localplayer.dead) {
            world.destroyBody(localplayer.fixture.getBody());
            localplayer.TurnDead();
            stateTime = 0;
        }


        if (remoteplayerdata.getHP() == 0 && !remoteplayer.dead) {
            world.destroyBody(remoteplayer.fixture.getBody());
            remoteplayer.TurnDead();
            stateTime = 0;
        }

    }

    public boolean isDeadHandling() {
        return DeadHandling;
    }


    public String getWinLose() {
        return WinLose;
    }

    public void IfDead() {

        if (localplayer.dead) {
            WinLose = "Lose";
        }

        if (remoteplayer.dead)
        {
            WinLose = "Win";

        }



        clientHandler.DisconnectClient();
        DeadHandling = false;



    }

}
