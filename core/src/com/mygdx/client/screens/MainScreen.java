package com.mygdx.client.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.client.Main;
import com.mygdx.client.game.GameWorld;
import com.mygdx.common.ConnectedClient;


public class MainScreen extends ScreenAdapter {
    ConnectedClient player1;
    ConnectedClient player2;
    String Playernumber;


    GameWorld gameworld;




    public MainScreen(ConnectedClient player1, ConnectedClient player2,String Playernumber){
        this.player1 = player1;
        this.player2 = player2;
        this.Playernumber = Playernumber;
    }






    @Override
    public void show () {
        gameworld = new GameWorld(player1,player2, Playernumber);
        gameworld.create();








    }


    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render (float delta) {

        gameworld.render();





    }}
