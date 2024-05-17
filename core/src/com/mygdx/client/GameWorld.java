package com.mygdx.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameWorld extends ApplicationAdapter {
    private static final float PPM = 100.0f;
    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;


    private Viewport viewport;
    private OrthographicCamera camera;
    private Player player;
    private Texture arena;
    private Stage stage;
    private Texture background;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private float accumulator = 0;
    private float stateTime;
    float viewport_y;
    float viewport_x;

    @Override
    public void create() {
        // Initialize Box2D
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Set up the camera and viewport
        camera = new OrthographicCamera(1200, 1000);
        viewport = new ExtendViewport(1200, 1000 , camera);
        stage = new Stage(viewport);

        viewport_x = camera.viewportWidth;
        viewport_y = camera.viewportHeight;
        System.out.println(viewport_x);

        // Load background texture
        background = new Texture(Gdx.files.internal("Arena/BG.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Load arena texture
        arena = new Texture(Gdx.files.internal("Arena/arenaAtlas.png"));

        // Initialize the sprite batch
        batch = new SpriteBatch();

        // Create the player
        player = new Player(world);

        // Create Arenabodies
        //Woodbox
        createRect(650,82,world,28,26);

        //Low Ground
        createRect(0, 0, world, viewport_x,48);

        //High ground
        createRect(355,150,world,46,18);
        createRect(952,145,world,46,18);

        //Lowmidplat form
        createRect(133,346,world,48,18);
        createRect(1129,346,world,47,18);

        // Midplat form
        createRect(290,535,world,205,16);
        createRect(1005,535,world,185,16);

        //Highmidplat form
        createRect(648,690,world,92,16);


        //Lowtop platform
        createRect(365,820,world,72,21);
        createRect(955,820,world,72,21);


        //Hightop platform
        createRect(652,980,world,45,18);



        //World boundaries

        createRect(0,0,world,1,2500);
        createRect(viewport_x,0,world,1,2500);
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
        stateTime += Gdx.graphics.getDeltaTime()*0.9;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the background and arena
        batch.setProjectionMatrix(camera.combined.scl(1/PPM));
        batch.begin();
        batch.draw(background, 0, 0, viewport_x+400,viewport_y);
        batch.draw(arena, 0, 0, viewport_x,viewport_y);
        player.render(stateTime, camera);
        batch.end();

        // Update the world
        update();
        debugRenderer.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        debugRenderer.dispose();
    }

    public void update() {
        stepWorld();
        player.update();
        camera.update();
    }

    public Body createRect(float x, float y, World world, float width, float height) {
        Body body;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x/PPM,y/PPM);
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM, height/PPM);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }
}
