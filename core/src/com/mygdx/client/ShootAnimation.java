package com.mygdx.client;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ShootAnimation extends ApplicationAdapter {
    private SpriteBatch ShootBatch;
    private TextureAtlas ShootAtlas;
    private Animation<Sprite> ShootAnim;
    private Sprite ShootSprite;

    private float stateTime = 0;

    @Override
    public void create() {
        ShootBatch = new SpriteBatch();
        ShootAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        ShootAnim = new Animation<>(0.066f, ShootAtlas.createSprites("shoot"), Animation.PlayMode.LOOP);

    }


    public void render(int x, int y, float stateTime) {

        ShootSprite = ShootAnim.getKeyFrame(stateTime, true);
        ShootSprite.setSize(100, 100);
        ShootSprite.setPosition(x, y);

        ShootBatch.begin();
        ShootSprite.draw(ShootBatch);
        ShootBatch.end();
    }

    @Override
    public void dispose() {
        ShootBatch.dispose();
        ShootAtlas.dispose();
    }
}

