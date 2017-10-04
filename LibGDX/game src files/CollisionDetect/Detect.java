package CollisionDetect;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import RefTypes.Bool;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */


/*
this class handles collisions between objects
this will be used by the physics engine so I just define
the functions
 */
public class Detect implements ContactListener {

    // detects the collision between two bodies
    private static Fixture body1;
    private static Fixture body2;

    // checks if a player is jumping or not
    private static Bool bool = new Bool(false);


    public Detect(){}

    // what the physics engine calls when two objects collied
    @Override
    public void beginContact(Contact c) {
        body1 = c.getFixtureA();
        body2 = c.getFixtureB();

        // lets the program know the player has landed on the ground
        bool.setBool(true);
    }


    // used when contact is broken between two objects
    @Override
    public void endContact(Contact contact) {
        // lets the program know the player is jumping
        bool.setBool(false);
    }


    // don't know what these do, but I don't use them so it's whatever
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    // checks if the player is currently in contact with another object
    public static boolean getBool(){
        return bool.getBool();
    }


    // this checks if the player is in contact with a certain kind of object
    public static boolean getCollide(String message){
        // default return value since the condition below
        // won't always be met
        boolean ret = false;
        if(body1 != null && body2 != null) {

            // gets the type of object based off the string stored
            // in the collided object
            String bod1 = (String) body1.getBody().getUserData();
            String bod2 = (String) body2.getBody().getUserData();

            // if one of those tags is null, we know that object
            // isn't in contact with the player and so the eval can be skipped
            if (bod1 != null && bod2 != null) {
                ret = bod1.equals(message) || bod2.equals(message);
            }
        }
        return ret;
    }

}
