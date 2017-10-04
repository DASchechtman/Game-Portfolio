package com.finals.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */


/*
in libgdx, a SpriteBatch is used a lot for drawing and stuff.
so I figured why not make one SpriteBatch avalible throughout the
program using a singleton
 */
public class GlobalBatch {
    private static SpriteBatch batch;

    public static SpriteBatch getInstance(SpriteBatch b){
        if(batch == null){
            batch = b;
        }
        return batch;
    }

    public static SpriteBatch getInstance(){
        return batch;
    }

    public static void dispose(){
        batch.dispose();
    }
}
