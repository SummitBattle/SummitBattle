package com.mygdx.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class RunAnimation extends ApplicationAdapter {
    private SpriteBatch RunBatch;
    private TextureAtlas RunAtlas;
    private Animation<Sprite> RunAnim;
    private Sprite RunSprite;

    private float stateTime = 0;

    @Override
    public void create() {
        RunBatch = new SpriteBatch();
        RunAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        RunAnim = new Animation<>(0.066f, RunAtlas.createSprites("run"), Animation.PlayMode.LOOP);



    }

    public void render(int x, int y, float stateTime) {

        RunSprite = RunAnim.getKeyFrame(stateTime, true);
        RunSprite.setSize(100, 100);
        RunSprite.setPosition(x, y);

        RunBatch.begin();
        RunSprite.draw(RunBatch);
        RunBatch.end();
    }

    @Override
    public void dispose() {
        RunBatch.dispose();
        RunAtlas.dispose();
    }
}
