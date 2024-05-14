package com.mygdx.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class IdleAnimation extends ApplicationAdapter {
    private SpriteBatch IdleBatch;
    private TextureAtlas idleAtlas;
    private Animation<Sprite> idleAnimation;
    private Sprite idleSprite;

    private float stateTime = 0;

    @Override
    public void create() {
        IdleBatch = new SpriteBatch();
        idleAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        idleAnimation= new Animation<>(0.066f, idleAtlas.createSprites("Idle"), Animation.PlayMode.LOOP);


    }

    public void render(int x, int y, float stateTime) {

        idleSprite = idleAnimation.getKeyFrame(stateTime, true);
        idleSprite.setSize(100, 100);
        idleSprite.setPosition(x, y);

        IdleBatch.begin();
        idleSprite.draw(IdleBatch);
        IdleBatch.end();
    }

    @Override
    public void dispose() {
        IdleBatch.dispose();
        idleAtlas.dispose();
    }
}
