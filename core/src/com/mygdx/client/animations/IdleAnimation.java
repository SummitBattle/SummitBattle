package com.mygdx.client.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class IdleAnimation {
    private SpriteBatch IdleBatch;
    private TextureAtlas idleAtlas;
    private Animation<Sprite> IdleAnim;
    private Sprite idleSprite;




    public IdleAnimation() {
        IdleBatch = new SpriteBatch();
        idleAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        IdleAnim = new Animation<>(0.066f, idleAtlas.createSprites("Idle"), Animation.PlayMode.LOOP);


    }

    public Animation getidle() {
        return IdleAnim;
    }

    public void render(int x, int y, float stateTime) {

        idleSprite = IdleAnim.getKeyFrame(stateTime, true);
        idleSprite.setSize(100, 100);
        idleSprite.setPosition(x, y);

        IdleBatch.begin();
        idleSprite.draw(IdleBatch);
        IdleBatch.end();
    }


}
