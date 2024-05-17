package com.mygdx.client;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Vector;

public class Player {
    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;
    private RunAnimation playerRun;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> shootAnimation;
    private IdleAnimation playerIdle;
    private ShootAnimation playerShoot;
    private Animation<TextureRegion> currentAnimation;
    private int playerHP;
    Vector2 pos;

    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private Body PlayerBody;

    public Player(World world) {

        //Box2D body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        x = 100;
        y = 50;
        bodyDef.position.set(x,y);
        PlayerBody = world.createBody(bodyDef);

        pos = this.PlayerBody.getPosition();



        //Fixture and Shape

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.8f, 0.8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f;
        PlayerBody.createFixture(fixtureDef);
        shape.dispose();


        spriteBatch = new SpriteBatch();
        sprite = new Sprite();

        playerHP = 3;

        playerRun = new RunAnimation();
        playerRun.create();
        runAnimation = playerRun.getrun();

        playerIdle = new IdleAnimation();
        playerIdle.create();
        idleAnimation = playerIdle.getidle();

        playerShoot = new ShootAnimation();
        playerShoot.create();
        shootAnimation = playerShoot.getshoot();

        currentAnimation = idleAnimation;


    }





    public void run() {
        currentAnimation = runAnimation;

    }

    public void jump() {

        this.PlayerBody.applyLinearImpulse(0f,2f,pos.x,pos.y,true);


    }

    public void shoot() {
        currentAnimation = shootAnimation;
    }

    public void hit() {
        playerHP--;
    }

    public void render(float stateTime) {
        pos = this.PlayerBody.getPosition();






        // Get the current frame of the animation
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        // Set the position of the sprite
        sprite.setPosition(x,y);

        // Begin the SpriteBatch
        spriteBatch.begin();

        // Draw the current frame of the animation
        spriteBatch.draw(currentFrame, pos.x,pos.y);

        // End the SpriteBatch
        spriteBatch.end();
    }
}
