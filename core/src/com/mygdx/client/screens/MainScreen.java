package com.mygdx.client.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.client.ClientHandler;
import com.mygdx.client.Main;
import com.mygdx.client.game.GameWorld;
import com.mygdx.common.ConnectedClient;


public class MainScreen extends ScreenAdapter {
    ConnectedClient player1;
    ConnectedClient player2;
    String Playernumber;


    GameWorld gameworld;
    ClientHandler clientHandler;
    private final Game game;
    EndScreen endScreen;





    public MainScreen(ConnectedClient player1, ConnectedClient player2, String Playernumber, ClientHandler clientHandler, Game game){
        this.player1 = player1;
        this.player2 = player2;
        this.Playernumber = Playernumber;
        this.clientHandler = clientHandler;
        this.game = game;
    }






    @Override
    public void show () {


        gameworld = new GameWorld(player1,player2, Playernumber,clientHandler);
        gameworld.create();













    }


    // @Override
    //public void resize(int width, int height) {

    //}
    @Override
    public void render (float delta) {

        gameworld.render();
        gameworld.isDeadHandling();


        if (!gameworld.isDeadHandling()){
            endScreen = new EndScreen(game,gameworld.getWinLose());
            game.setScreen(endScreen);
        }






    }}
