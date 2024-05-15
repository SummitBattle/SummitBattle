package com.mygdx.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.client.RunAnimation;

public class Player {
    float x;
    float y;
    float xSpeed;
    float ySpeed;
    private RunAnimation playerRun;
    private Animation Idle;
    private Animation Run;
    private Animation Shoot;
    private IdleAnimation playerIdle;
    private ShootAnimation playerShoot;
    private Animation currentAnimation;
    int PlayerHP;


    public Player(){
        PlayerHP = 3;
        playerRun = new RunAnimation();
        playerRun.create();

        playerIdle = new IdleAnimation();

        playerIdle.create();
        Idle = playerIdle.getidle();
        playerShoot = new ShootAnimation();
        playerShoot.create();




        currentAnimation = Idle;

        this.x = Gdx.graphics.getWidth()/2;
        this.y = Gdx.graphics.getHeight()/2;


    }



    public void setPos(float x, float y){
        this.x = x;
        this.y = y;

    }

    public void changePos(float x, float y){
        this.x +=x;
        this.y +=y;

    }

    public void move(){

        changePos(xSpeed, ySpeed);
    }

    public Animation animate(){
        return currentAnimation;
    }

    public void run(){
        currentAnimation = Run;
        ySpeed = 0;
    }

    public void jump(){

        ySpeed = 10;
    }


    public void hit(){
        PlayerHP -= 1;
    }
}