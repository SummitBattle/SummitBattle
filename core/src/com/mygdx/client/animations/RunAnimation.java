package com.mygdx.client.animations;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class RunAnimation  {

    private TextureAtlas RunAtlas;
    private Animation<Sprite> RunAnim;




    public RunAnimation() {

        RunAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        RunAnim = new Animation<>(0.066f, RunAtlas.createSprites("run"), Animation.PlayMode.LOOP);



    }

    public Animation getrun() {
        return RunAnim;
    }




}
