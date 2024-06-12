package com.mygdx.client.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class ListenerClass implements ContactListener {
    private final Array<Body> deletionList = new Array<>();

    @Override
    public void beginContact(Contact contact) {
        // No specific logic for when contact begins
    }

    @Override
    public void endContact(Contact contact) {
        // No specific logic for when contact ends
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        CustomUserData userDataA = (CustomUserData) fixtureA.getUserData();
        CustomUserData userDataB = (CustomUserData) fixtureB.getUserData();

        if (userDataA == null || userDataB == null) {
            return;
        }

        // Check if one of the fixtures is the player and the other is the platform
        boolean isPlayerAndPlatform =
                ("player".equals(userDataA.getType()) && "platform".equals(userDataB.getType())) ||
                        ("platform".equals(userDataA.getType()) && "player".equals(userDataB.getType()));

        if (isPlayerAndPlatform) {
            float playerY = "player".equals(userDataA.getType()) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;
            float platformY = "platform".equals(userDataA.getType()) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;

            // Check if the player is below the platform
            if (playerY < platformY + 0.2f) {  // Adjusted to 0.2 to be more clear (20f / 100)
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }
        }




        // Check if a bullet hits the boundary or platform
        if (
                "bullet".equals(userDataA.getType()) && "boundary".equals(userDataB.getType()) ||
                "bullet".equals(userDataA.getType()) && "platform".equals(userDataB.getType())
        ) {
            deletionList.add(fixtureA.getBody());
        } else if (
                "bullet".equals(userDataB.getType()) && "boundary".equals(userDataA.getType()) ||
                "bullet".equals(userDataB.getType()) && "platform".equals(userDataA.getType())

        ) {
            deletionList.add(fixtureB.getBody());
        }

        if (
                "bullet".equals(userDataA.getType()) && "player".equals(userDataB.getType())
        ) {
            System.out.println("HIT");
            userDataB.setHP(userDataB.getHP()-1);
            deletionList.add(fixtureA.getBody());

        } else if (
                "bullet".equals(userDataB.getType()) && "player".equals(userDataA.getType())

        ) {
            System.out.println("HIT");
            userDataA.setHP(userDataA.getHP()-1);
            deletionList.add(fixtureB.getBody());

        }}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        // No specific logic for after contact is solved
    }

    public Array<Body> getDeletionList() {
        return deletionList;
    }
}
