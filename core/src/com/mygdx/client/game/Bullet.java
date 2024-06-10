package com.mygdx.client.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.client.game.GameWorld.PPM;

public class Bullet {
    private Body body;
    private static TextureAtlas textureAtlas;  // Load once for all bullets
    private Sprite bulletSprite;
    private World bulletWorld;
    private Vector2 oldPosition;
    private boolean dead;


    // Static block to load the texture atlas once
    static {
        textureAtlas = new TextureAtlas("Bullet/bullets.txt");
    }

    public Bullet(World world) {
        bulletSprite = textureAtlas.createSprite("green_bullet");
        bulletWorld = world;
        oldPosition = new Vector2();
    }

    public Body createBullet(float x, float y, boolean spriteFlipped) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;

        def.position.set(x + (spriteFlipped ? -0.5f : 0.5f), y);

        this.body = bulletWorld.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4 / PPM, 5 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        this.body.createFixture(fixtureDef).setUserData("bullet");

        this.body.setGravityScale(0f);

        shape.dispose();

        this.body.setLinearVelocity(spriteFlipped ? -20 : 20, 0);

        return body;
    }

    public void render(Batch batch) {
        Vector2 position = body.getPosition();

        if (oldPosition.dst2(position) > 0.00000001f) {  // Use precomputed squared distance
            bulletSprite.setPosition((position.x * PPM) - (bulletSprite.getWidth() / 2), (position.y * PPM) - (bulletSprite.getHeight() / 2));
            bulletSprite.draw(batch);
            oldPosition.set(position); // Update old position only if moved
        }
    }

    public boolean isOffScreen(float screenWidth) {
        float posX = body.getPosition().x * PPM;
        return posX < 0 || posX > screenWidth;
    }
}
