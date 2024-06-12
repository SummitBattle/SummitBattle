package com.mygdx.client.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DeadAnimation  {

    private TextureAtlas DeadAtlas;
    private Animation<Sprite> DeadAnim;




    public DeadAnimation() {

        DeadAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        DeadAnim = new Animation<>(0.066f, DeadAtlas.createSprites("dead"), Animation.PlayMode.LOOP);



    }

    public Animation getdead() {
        return DeadAnim;
    }




}
