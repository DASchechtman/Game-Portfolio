package com.finals.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */
/*
A World object is used for all the physics calculations
so I thought it be easier to make a global World with a Singleton
 */

public class GlobalWorld {
    private static World world;

    public static World getInstance(World w) {
        if (world == null) {
            world = w;
        }
        return world;
    }

    public static World getInstance(){
        return world;
    }

    public static  void dispose(){
        world.dispose();
    }
}
