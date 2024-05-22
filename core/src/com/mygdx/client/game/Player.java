package com.mygdx.client.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.client.animations.IdleAnimation;
import com.mygdx.client.animations.RunAnimation;
import com.mygdx.client.animations.ShootAnimation;

import java.io.IOException;
import java.util.Iterator;

public class Player {
    private static final float PPM = 100.0f;
    private float widthMeters = 1.0f;
    private float heightMeters = 1.0f;
    private float widthPixels = widthMeters * PPM;
    private float heightPixels = heightMeters * PPM;

    private RunAnimation playerRun;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> shootAnimation;
    private IdleAnimation playerIdle;
    private ShootAnimation playerShoot;
    private Animation<TextureRegion> currentAnimation;
    private int playerHP;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private Body playerBody;
    private float horizontalForce;
    Array<Bullet> bullets;
    World theworld;
    float cooldown;

    Vector2 pos;
    Bullet bullet;

    public Player(World world) {
        bullets = new Array<>();
        theworld = world;

        // Box2D body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(50 / PPM, 20 / PPM);
        playerBody = world.createBody(bodyDef);

        // Fixture and Shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthMeters / 4f, heightMeters / 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.25f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0f;
        playerBody.setFixedRotation(true);
        playerBody.createFixture(fixtureDef).setUserData("player");
        shape.dispose();

        spriteBatch = new SpriteBatch();
        sprite = new Sprite();

        playerHP = 3;

        playerRun = new RunAnimation();
        runAnimation = playerRun.getrun();

        playerIdle = new IdleAnimation();
        idleAnimation = playerIdle.getidle();

        playerShoot = new ShootAnimation();
        shootAnimation = playerShoot.getshoot();

        currentAnimation = idleAnimation;

        sprite.setSize(-widthPixels, heightPixels);
        cooldown = 0; // Initialize cooldown to zero
    }

    public void update(float state) {
        if (playerBody.getLinearVelocity().x == 0 && currentAnimation.isAnimationFinished(state)) {
            currentAnimation = idleAnimation;
        }

        horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce -= 1;
            if (currentAnimation.isAnimationFinished(state)){
            currentAnimation = runAnimation;}
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce += 1;
            if (currentAnimation.isAnimationFinished(state)){
            currentAnimation = runAnimation;}
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && playerBody.getLinearVelocity().y == 0) {
            this.playerBody.setAwake(true);
            this.playerBody.applyForceToCenter(0, 400, false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && cooldown <= 0) {
            if (currentAnimation.isAnimationFinished(state)){
            currentAnimation = shootAnimation;}
            horizontalForce /= 5;
            bullet = new Bullet(theworld);
            bullet.createBullet(playerBody.getPosition().x, playerBody.getPosition().y, sprite.getWidth() > 0);
            bullets.add(bullet);
            System.out.println(bullets);
            cooldown = 0.7f; // Reset cooldown
        }

        this.playerBody.setLinearVelocity(horizontalForce * 5, playerBody.getLinearVelocity().y);

        if (cooldown > 0) {
            cooldown -= Gdx.graphics.getDeltaTime(); // Decrease cooldown over time
        }
    }

    public void render(float stateTime, Camera camera) {
        pos = playerBody.getPosition();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Get the current frame of the animation
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);

        // Set the sprite's size to match the Box2D body size in pixels
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.setSize(-widthPixels, heightPixels);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.setSize(widthPixels, heightPixels);
        }

        // Set the sprite's position based on the Box2D body's position
        sprite.setPosition(pos.x * PPM - (sprite.getWidth() / 2), pos.y * PPM - (sprite.getHeight() / 2));

        // Begin the SpriteBatch
        spriteBatch.begin();

        // Draw the player sprite
        sprite.draw(spriteBatch);

        // Render bullets
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            bullet.render(spriteBatch);
        }

        // End the SpriteBatch
        spriteBatch.end();
    }

    public Vector2 getPosition() {
        return playerBody.getPosition();
    }

    public boolean isFlipped() {
        return sprite.getWidth() < 0;
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
