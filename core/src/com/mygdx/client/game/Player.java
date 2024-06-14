package com.mygdx.client.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.mygdx.client.ClientHandler;
import com.mygdx.client.animations.DeadAnimation;
import com.mygdx.client.animations.IdleAnimation;
import com.mygdx.client.animations.RunAnimation;
import com.mygdx.client.animations.ShootAnimation;
import com.mygdx.common.PlayerInput;

public class Player {
    private static final float PPM = 100.0f;
    private static final float PLAYER_WIDTH_METERS = 1.0f;
    private static final float PLAYER_HEIGHT_METERS = 1.0f;
    private static float PLAYER_WIDTH_PIXELS = PLAYER_WIDTH_METERS * PPM;
    private static final float PLAYER_HEIGHT_PIXELS = PLAYER_HEIGHT_METERS * PPM;
    private static final float BULLET_COOLDOWN = 0.2f;
    private static final float JUMP_FORCE = 400f;
    private static final float HORIZONTAL_SPEED = 5f;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> shootAnimation;
    private Animation<TextureRegion> deadAnimation;
    private IdleAnimation playerIdle;
    private ShootAnimation playerShoot;
    private DeadAnimation playerDead;
    private RunAnimation playerRun;
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
    private int unflipsprite;
    private boolean A_PRESSED;
    private boolean D_PRESSED;
    private boolean W_PRESSED;
    private boolean ENTER_PRESSED;
    public boolean dead;
    private boolean deadAnimationCompleted;
    public Fixture fixture;
    private ClientHandler clientHandler;




    public Player(World world, boolean isLocalPlayer, Vector2 initialPosition, int playerNumber, ClientHandler clientHandler) {

        this.world = world;
        this.isLocalPlayer = isLocalPlayer;
        this.clientHandler = clientHandler; // Assign client handler for network communication
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
        CustomUserData customPlayer = new CustomUserData("player");
        fixture = playerBody.createFixture(fixtureDef);
        fixture.setUserData(customPlayer);
        shape.dispose();

        spriteBatch = new SpriteBatch();
        sprite = new Sprite();

        playerRun = new RunAnimation();
        runAnimation = playerRun.getrun();

        playerIdle = new IdleAnimation();
        idleAnimation = playerIdle.getidle();

        playerShoot = new ShootAnimation();
        shootAnimation = playerShoot.getshoot();

        playerDead = new DeadAnimation();
        deadAnimation = playerDead.getdead();

        currentAnimation = idleAnimation;

        if (playerNumber == 2) {
            PLAYER_WIDTH_PIXELS = -PLAYER_WIDTH_PIXELS;
        }

        unflipsprite = playerNumber;
        sprite.setSize(-PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);


    }

    public void checkInputs() {
        A_PRESSED = Gdx.input.isKeyPressed(Input.Keys.A);
        D_PRESSED = Gdx.input.isKeyPressed(Input.Keys.D);
        W_PRESSED = Gdx.input.isKeyPressed(Input.Keys.W);
        ENTER_PRESSED = Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    public void ReceiveInputs(boolean A, boolean W, boolean ENTER_PRESSED, boolean D) {
        if (!isLocalPlayer) {
            this.A_PRESSED = A;
            this.W_PRESSED = W;
            this.ENTER_PRESSED = ENTER_PRESSED;
            this.D_PRESSED = D;
        }
    }

    public void update(float stateTime) {
        if (isLocalPlayer) {
            checkInputs();
            // Send inputs to the server (you would have a method to do this)
            sendInputsToServer();
        }

        if (cooldown > 0) {
            cooldown -= Gdx.graphics.getDeltaTime();
        }
        handleInput(stateTime);
    }

    private void sendInputsToServer() {
        PlayerInput playerInput = new PlayerInput(A_PRESSED, D_PRESSED, W_PRESSED, ENTER_PRESSED);
        clientHandler.SendInputs(playerInput);
    }

    private void handleInput(float stateTime) {
        if (playerBody.getLinearVelocity().x == 0) {
            currentAnimation = idleAnimation;
        }

        horizontalForce = 0;

        if (A_PRESSED) {
            horizontalForce -= 1;
            currentAnimation = runAnimation;
        }
        if (D_PRESSED) {
            horizontalForce += 1;
            currentAnimation = runAnimation;
        }

        if (W_PRESSED && playerBody.getLinearVelocity().y == 0) {
            playerBody.setAwake(true);
            playerBody.applyForceToCenter(0, JUMP_FORCE, false);
        }

        if (ENTER_PRESSED && cooldown <= 0) {
            currentAnimation = shootAnimation;
            horizontalForce /= 5;
            Bullet bullet = new Bullet(world);
            bullet.createBullet(playerBody.getPosition().x, playerBody.getPosition().y, sprite.getWidth() > 0);
            bullets.add(bullet);
            cooldown = BULLET_COOLDOWN; // Reset cooldown
        }

        playerBody.setLinearVelocity(horizontalForce * HORIZONTAL_SPEED, playerBody.getLinearVelocity().y);
    }

    public void render(float stateTime, Camera camera) {
        position = playerBody.getPosition();
        spriteBatch.setProjectionMatrix(camera.combined);

        // Set sprite size and flip based on direction
        if (D_PRESSED) {
            sprite.setSize(-PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);
        }
        if (A_PRESSED) {
            sprite.setSize(PLAYER_WIDTH_PIXELS, PLAYER_HEIGHT_PIXELS);
        }

        // Handle animations
        if (!dead) {
            TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
            sprite.setRegion(currentFrame);
            sprite.setPosition(position.x * PPM - (sprite.getWidth() / 2), position.y * PPM - (sprite.getHeight() / 2));
        } else {
            // Handle dead animation
            if (!deadAnimationCompleted) {
                deadAnimation.setPlayMode(Animation.PlayMode.NORMAL);
                TextureRegion deadCurrentFrame = deadAnimation.getKeyFrame(stateTime, false);
                sprite.setRegion(deadCurrentFrame);
                if (deadAnimation.isAnimationFinished(stateTime)) {
                    deadAnimationCompleted = true;
                }
            } else {
                // Stay on the last frame of the dead animation
                TextureRegion lastFrame = deadAnimation.getKeyFrames()[deadAnimation.getKeyFrames().length - 1];
                sprite.setRegion(lastFrame);
            }
        }

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

        // Flip sprite based on player number
        if (unflipsprite == 2) {
            unflipsprite += 1;
            PLAYER_WIDTH_PIXELS = -PLAYER_WIDTH_PIXELS;
        }
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void TurnDead() {
        dead = true;
    }


}