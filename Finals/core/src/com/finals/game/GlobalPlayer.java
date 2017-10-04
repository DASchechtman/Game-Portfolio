package com.finals.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Daniel Schechtman on 5/9/2017.
 */

/*
Certain data from the player object is needed all over the
program, so I figured make it avalible via a singleton
 */

public class GlobalPlayer {

    // gets the object tha animates the player
    private static Animation<TextureRegion> animation = null;

    // used to keep track of how long the program was running
    // so the animation can be performed
    private static float timeEllapsed = 0;

    // since the body is associated with the player, I figured
    // it wouldn't hurt to make that avalible too
    private static Body b = null;

    public static Animation<TextureRegion> getInstance(Animation<TextureRegion> a, Body bd){
        if(animation == null){
            animation = a;
            b = bd;
        }
        return animation;
    }

    public static Animation<TextureRegion> getInstance(){
        return animation;
    }

    public static Body getBody(){
        return b;
    }

    public static void setTimeEllapsed(float v){
        timeEllapsed = v;
    }
}
