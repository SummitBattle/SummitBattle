package com.mygdx.client.animations;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ShootAnimation  {

    private TextureAtlas ShootAtlas;
    private Animation<Sprite> ShootAnim;
    private Sprite ShootSprite;




    public ShootAnimation() {
        ShootAtlas = new TextureAtlas("Gungirl/gungirl.txt");
        ShootAnim = new Animation<>(0.066f, ShootAtlas.createSprites("shoot"), Animation.PlayMode.LOOP);

    }

    public Animation getshoot() {
        return ShootAnim;
    }




}

