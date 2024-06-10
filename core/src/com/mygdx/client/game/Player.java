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
import com.mygdx.common.ConnectedClient;

public class Player {
    private static final float PPM = 100.0f;
    private static final float PLAYER_WIDTH_METERS = 1.0f;
    private static final float PLAYER_HEIGHT_METERS = 1.0f;
    private static final float PLAYER_WIDTH_PIXELS = PLAYER_WIDTH_METERS * PPM;
    private static final float PLAYER_HEIGHT_PIXELS = PLAYER_HEIGHT_METERS * PPM;
    private static final float BULLET_COOLDOWN = 0.7f;
    private static final float JUMP_FORCE = 400f;
    private static final float HORIZONTAL_SPEED = 5f;

    private RunAnimation playerRun;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> shootAnimation;
    private IdleAnimation playerIdle;
    private ShootAnimation playerShoot;
    private Animation<TextureRegion> currentAnimation;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private Body playerBody;
    private float horizontalForce;
    private Array<Bullet> bullets;
    private World world;
    private float cooldown;
    private Vector2 position;
    private boolean isLocalPlayer;

    public Player(World world, ConnectedClient client, boolean isLocalPlayer, Vector2 initialPosition) {
        this.world = world;
        this.isLocalPlayer = isLocalPlayer;
        bullets = new Array<>();
        cooldown = 0; // Initialize cooldown to zero

        // Box2D body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(initialPosition);
        playerBody = world.createBody(bodyDef);

        // Fixture and Shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PLAYER_WIDTH_METERS / 4f, PLAYER_HEIGHT_METERS / 3);
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

        playerRun = new RunAnimation();
        runAnimation = playerRun.getrun();

        playerIdle = new IdleAnimation();
        idleAnimation = playerIdle.getidle();

        playerShoot = new ShootAnimation();
        shootAnimation = playerShoot.getshoot();

        currentAnimation = idleAnimation;

        sprite.setSize(-PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);
    }

    public void update(float stateTime) {
        if (isLocalPlayer) {
            handleInput(stateTime);
        }
        updatePosition(stateTime);

        if (cooldown > 0) {
            cooldown -= Gdx.graphics.getDeltaTime();
        }
    }

    private void handleInput(float stateTime) {
        if (playerBody.getLinearVelocity().x == 0 && currentAnimation.isAnimationFinished(stateTime)) {
            currentAnimation = idleAnimation;
        }

        horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce -= 1;
            if (currentAnimation.isAnimationFinished(stateTime)) {
                currentAnimation = runAnimation;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce += 1;
            if (currentAnimation.isAnimationFinished(stateTime)) {
                currentAnimation = runAnimation;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && playerBody.getLinearVelocity().y == 0) {
            playerBody.setAwake(true);
            playerBody.applyForceToCenter(0, JUMP_FORCE, false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && cooldown <= 0) {
            currentAnimation = shootAnimation;
            horizontalForce /= 5;
            Bullet bullet = new Bullet(world);
            bullet.createBullet(playerBody.getPosition().x, playerBody.getPosition().y, sprite.getWidth() > 0);
            bullets.add(bullet);
            cooldown = BULLET_COOLDOWN; // Reset cooldown
        }

        playerBody.setLinearVelocity(horizontalForce * HORIZONTAL_SPEED, playerBody.getLinearVelocity().y);
    }

    private void updatePosition(float stateTime) {
        position = playerBody.getPosition();
        if (playerBody.getLinearVelocity().x == 0 && currentAnimation.isAnimationFinished(stateTime)) {
            currentAnimation = idleAnimation;
        }
    }




    public void render(float stateTime, Camera camera) {
        position = playerBody.getPosition();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Get the current frame of the animation
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        sprite.setRegion(currentFrame);

        // Set the sprite's size and flip based on direction
        if (Gdx.input.isKeyPressed(Input.Keys.D) && isLocalPlayer) {
            sprite.setSize(-PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && isLocalPlayer) {
            sprite.setSize(PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);
        }

        // Set the sprite's position based on the Box2D body's position
        sprite.setPosition(position.x * PPM - (sprite.getWidth() / 2), position.y * PPM - (sprite.getHeight() / 2));

        // Begin the SpriteBatch
        spriteBatch.begin();

        // Draw the player sprite
        sprite.draw(spriteBatch);

        // Render bullets
        for (Bullet bullet : bullets) {
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