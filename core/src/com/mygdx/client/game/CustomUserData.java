package com.mygdx.client.game;

public class CustomUserData {
    private int HP;



    private String Type;



    public CustomUserData(String TYPE) {

        this.Type = TYPE;

        if (Type.equals("player")) {
        HP = 3;
        }


    }

    public int getHP(){
        return HP;
    }

    public void setHP(int HP){
        this.HP = HP;
    }

    public String getType() {
        return Type;
    }



}
