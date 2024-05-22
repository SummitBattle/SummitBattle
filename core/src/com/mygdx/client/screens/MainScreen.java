package com.mygdx.client.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.client.game.GameWorld;


public class MainScreen extends ScreenAdapter {


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
