package com.mygdx.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.World;


class MainScreen extends ScreenAdapter {


    GameWorld gameworld;

    float stateTime;




    @Override
    public void show () {
        gameworld = new GameWorld();
        gameworld.create();








    }


    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render (float delta) {

        gameworld.render();





    }}
