package com.mygdx.client.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class ListenerClass implements ContactListener {
    Array<Body> deletionList = new Array<>();

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

        // Check if one of the fixtures is the player and the other is the platform
        if (("player".equals(fixtureA.getUserData()) && "platform".equals(fixtureB.getUserData())) ||
                ("platform".equals(fixtureA.getUserData()) && "player".equals(fixtureB.getUserData()))) {

            // Get the position of the player and the platform
            float playerY = "player".equals(fixtureA.getUserData()) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;
            float platformY = "platform".equals(fixtureA.getUserData()) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;

            if (platformY + 20f / 100 > playerY) {
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }
        }

        // Check if a bullet hits the boundary
        if ("bullet".equals(fixtureA.getUserData())) {
            deletionList.add(fixtureA.getBody());
        } else if  ("bullet".equals(fixtureB.getUserData())) {
            deletionList.add(fixtureB.getBody());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        // No specific logic for after contact is solved
    }

    public Array<Body> getDeletionList() {
        return deletionList;
    }
}
