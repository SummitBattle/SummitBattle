package com.mygdx.client;

import com.badlogic.gdx.physics.box2d.*;

public class ListenerClass implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void endContact(Contact contact) {
        // No specific logic for when contact ends
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();


        // Check if one of the fixtures is the player and the other is the platform
        if ((fixtureA.getUserData() == "player" && fixtureB.getUserData() == "platform") ||
                (fixtureA.getUserData() == "platform" && fixtureB.getUserData() == "player")) {

            // Get the position of the player and the platform
            float playerY = fixtureA.getBody().getPosition().y;
            float platformY = fixtureB.getBody().getPosition().y;


            if (platformY + 20f/100 > playerY){
                contact.setEnabled(false);
                System.out.println("contact");
            } else {

                contact.setEnabled(true);
            }
        }





    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        // No specific logic for after contact is solved
    }
}
