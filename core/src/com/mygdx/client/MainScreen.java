package com.mygdx.client;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


class MainScreen extends ScreenAdapter {

    Main game;
    OrthographicCamera camera;


    private Stage stage;


    Texture background;
    Texture arena;
    Texture lowPlatform;


    SpriteBatch batch;



    @Override
    public void show () {
        //Background

        Gdx.input.setInputProcessor(stage);
        background = new Texture((Gdx.files.internal("Arena/BG.png")));
        arena = new Texture((Gdx.files.internal("Arena/arena.png")));
       // lowPlatform = new Texture((Gdx.files.internal("Arena/downplat.png")));
        batch = new SpriteBatch();
        //Display


    }




    @Override
    public void resize(int width, int height) {

    }

    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(255, 1, 1, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(arena,0,0);

        batch.end();

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
        background.dispose();
        arena.dispose();

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
