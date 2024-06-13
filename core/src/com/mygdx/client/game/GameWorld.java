package com.mygdx.client.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.client.ClientHandler;
import com.mygdx.client.Log;
import com.mygdx.client.screens.EndScreen;
import com.mygdx.client.screens.LoadScreen;
import com.mygdx.common.ConnectedClient;

public class GameWorld extends ApplicationAdapter {
    String WinLose;
    public static final float PPM = 100.0f;
    int Timer5;


    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private Viewport viewport;
    private OrthographicCamera camera;
    Player player1;
    Player player2;
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

    CustomUserData player1data;
    CustomUserData player2data;
    Skin mySkin;
    ClientHandler clientHandler;
    boolean DeadHandling = true;


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
            mySkin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

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

                player1 = new Player(world,true, player1StartPos,1);
                player2 = new Player(world,  false, player2StartPos,2);
            } else if (playernumber.equals("Player 2")) {
                player1 = new Player(world,   false, player1StartPos,1);
                player2 = new Player(world,  true, player2StartPos,2);
            }

            player1data = (CustomUserData) player1.getFixture().getUserData();
            player2data = (CustomUserData) player2.getFixture().getUserData();

            // Verify player initialization
            if (player1 == null || player2 == null) {
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
            Gdx.app.log("GameWorld", "Rendering players");
            player1.render(stateTime, camera);
            player2.render(stateTime, camera);

            batch.end();

            // Update the world
            update();
            debugRenderer.render(world, camera.combined.scl(PPM));
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
            player1.update(stateTime);
            player2.update(stateTime);
            camera.update();
            for (Body body : listenerClass.getDeletionList()) {
                world.destroyBody(body);
            }
            listenerClass.getDeletionList().clear();
        } catch (Exception e) {
            Gdx.app.log("GameWorld", "Error during update", e);
            throw e;  // Rethrow the exception after logging it
        }
        if (!player1.dead || !player2.dead) {
            DeadCheck();
    }
        if (DeadHandling && (player1.dead || player2.dead)) {

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
        if (player1data.getHP() == 0 && !player1.dead) {
            world.destroyBody(player1.fixture.getBody());
            player1.TurnDead();
            stateTime = 0;
        }


        if (player2data.getHP() == 0 && !player2.dead) {
            world.destroyBody(player2.fixture.getBody());
            player2.TurnDead();
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

        if ((player1.dead && playernumber.equals("Player 1")) || (player2.dead && playernumber.equals("Player 2"))){
            WinLose = "Lose";

        } else if ((player1.dead && playernumber.equals("Player 2")) || (player2.dead && playernumber.equals("Player 1")))
        {
            WinLose = "Win";

        }



        clientHandler.DisconnectClient();
        DeadHandling = false;



    }
}
