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
        viewport = new FitViewport(1200, 1000 , camera);
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

        // Create arena
        createRect(0, 0, world, viewport_x,100);
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
        stateTime += Gdx.graphics.getDeltaTime()*0.5;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the background and arena
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
       // batch.draw(background, 0, 0, viewport_x+400,viewport_y);
        batch.draw(arena, 0, 0, viewport_x,viewport_y);
        player.render(stateTime, camera);
        batch.end();

        // Update the world
        update();
        debugRenderer.render(world, camera.combined);
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
        def.position.set(x,y);
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM, height/2/PPM);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }
}
