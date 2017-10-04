package States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import Levels.Intro;
import Levels.LevelOne;

/**
 * Created by Daniel Schechtman on 5/2/2017.
 */

/*
This class will be the state machine
It is a pretty simple state macine, so I'm
sure you can figure out what's going on by reading the code
itself
 */
public class CurGameState {
    GameState state;

    public CurGameState(Body b){
        state = Intro.getInstance(b);
    }

    public void draw(){
        state.draw(this);
    }

    // the state may need to use the camera
    // so this is a way for that camera to be passed to the state
    public void updateCam(OrthographicCamera c){
        state.updateCam(c);
    }

    public void changeState(GameState newState){
        state = newState;
    }
}
