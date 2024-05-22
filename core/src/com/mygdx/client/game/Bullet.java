package com.mygdx.client.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.client.game.GameWorld.PPM;

public class Bullet {
    private Body body;
    private TextureAtlas textureAtlas;
    private Sprite bulletSprite;
    private World bulletWorld;
    Vector2 old_position;
    Vector2 position;
    boolean dead;

    public Bullet(World world) {
        textureAtlas = new TextureAtlas("Bullet/bullets.txt");
        bulletSprite = textureAtlas.createSprite("green_bullet");
        bulletWorld = world;
        old_position = new Vector2(0,0);
    }

    public Body createBullet(float x, float y, boolean spriteFlipped) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;

        def.position.set(x, y);
        if (spriteFlipped) {
            def.position.set(x - 0.5f, y);
        } else {
            def.position.set(x + 0.5f, y);
        }

        this.body = bulletWorld.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4 / PPM, 5 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        Vector2 velocity = this.body.getLinearVelocity();
        if (spriteFlipped) {
            velocity.x = -20;
        } else {
            velocity.x = 20;
        }
        this.body.setLinearVelocity(velocity);
        this.body.createFixture(fixtureDef).setUserData("bullet");

        this.body.setGravityScale(0f);

        shape.dispose();

        return body;

    }

    public void render(Batch batch) {
        Vector2 position = body.getPosition();

        float distanceSquared = old_position.dst2(position); // Squared distance
        float minDistanceSquared = 0.0001f * 0.0001f; // Adjust this threshold as needed

        boolean moved = distanceSquared > minDistanceSquared;

        if (moved) {
            bulletSprite.setPosition((position.x * PPM) - (bulletSprite.getWidth() / 2), (position.y * PPM) - (bulletSprite.getHeight() / 2));
            bulletSprite.draw(batch);
        }

        old_position.set(position); // Update old position for the next frame








    }

    public boolean isOffScreen(float screenWidth) {
        return body.getPosition().x * PPM < 0 || body.getPosition().x * PPM > screenWidth;
    }


}
