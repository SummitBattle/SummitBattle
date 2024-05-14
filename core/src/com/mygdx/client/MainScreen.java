package com.mygdx.client;


import com.badlogic.gdx.ScreenAdapter;



class MainScreen extends ScreenAdapter {


    GameWorld gameworld;



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
