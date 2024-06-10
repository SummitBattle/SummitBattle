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
        String userDataA = (String) fixtureA.getUserData();
        String userDataB = (String) fixtureB.getUserData();

        if (userDataA == null || userDataB == null) {
            return;
        }

        // Check if one of the fixtures is the player and the other is the platform
        boolean isPlayerAndPlatform =
                ("player".equals(userDataA) && "platform".equals(userDataB)) ||
                        ("platform".equals(userDataA) && "player".equals(userDataB));

        if (isPlayerAndPlatform) {
            float playerY = "player".equals(userDataA) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;
            float platformY = "platform".equals(userDataA) ? fixtureA.getBody().getPosition().y : fixtureB.getBody().getPosition().y;

            // Check if the player is below the platform
            if (playerY < platformY + 0.2f) {  // Adjusted to 0.2 to be more clear (20f / 100)
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }
        }




        // Check if a bullet hits the boundary or platform
        if (
                "bullet".equals(userDataA) && "boundary".equals(userDataB) ||
                "bullet".equals(userDataA) && "platform".equals(userDataB)
        ) {
            deletionList.add(fixtureA.getBody());
        } else if (
                "bullet".equals(userDataB) && "boundary".equals(userDataA) ||
                "bullet".equals(userDataB) && "platform".equals(userDataA)

        ) {
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
