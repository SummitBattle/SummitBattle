package com.mygdx.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.badlogic.gdx.physics.box2d.Body;



class GameWorld extends ApplicationAdapter  {
    Viewport viewport = new FitViewport(1000,800);

    OrthographicCamera camera;




    private Stage stage;


    Texture background;





    SpriteBatch batch;



    World world;
    Box2DDebugRenderer debugRenderer;
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;
    PhysicsShapeCache physicsBodies;
    TextureAtlas textureAtlas;
    Sprite ArenaSprite;
    Body ArenaBody;




    @Override
    public void create() {
        //Initializers for BOX2D
        Box2D.init();
        physicsBodies = new PhysicsShapeCache("Arena/arena.xml");
        world = new World(new Vector2(0,0), true);
        debugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1000,800,camera);
        stage = new Stage(viewport);

        //Background

        Gdx.input.setInputProcessor(stage);
        background = new Texture((Gdx.files.internal("Arena/BG.png")));
        textureAtlas = new TextureAtlas("Arena/arenaAtlas.txt");
        ArenaSprite = textureAtlas.createSprite("ArenaSprite");
        ArenaBody = physicsBodies.createBody("arenaBody", world,1,1);

        // lowPlatform = new Texture((Gdx.files.internal("Arena/downplat.png")));
        batch = new SpriteBatch();



    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }}


    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render() {
        Gdx.gl.glClearColor(255, 1, 1, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, 1000, 800);
       ArenaSprite.draw(batch);

        batch.end();

        //update world BOX2D

        stepWorld();

    }}




