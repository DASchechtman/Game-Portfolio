package Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import CollisionDetect.Detect;
import RefTypes.Bool;
import RefTypes.Int;


/**
 * Created by Daniel Schechtman on 5/3/2017.
 */

/*
This class is used to gather input from the user
 */

public class UserInput implements GestureListener, Runnable {

    // used to apply forces to the player
    private Body player;

    // used to avoid some bugs when the player is moving
    private boolean is_moving = false;

    // makes sure the player is touching the ground before
    // a jump action can take place
    private boolean collide = false;

    // makes sure the player can't jump as many times
    // as they want
    private static int counter = 0;

    // determines if the player is moving or not
    private static Bool event;

    // used to decide which way the player animation should be facing
    private Int i;


    public UserInput(Body p, Bool e, Int val){
        player = p;
        event = e;
        i = val;
    }


    // this will detect when the player is touching the screen
    // so it can start moving he player
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        // will only start moving the player if the
        // player is already still
        if(!is_moving) {
            new Thread(this).start();
        }
        return true;
    }

    // will be used to make the player jump
    @Override
    public boolean tap(float x, float y, int count, int button) {

        // makes sure to allow the user to jump agian
        // as soon as he/she hits the ground
        if(Detect.getBool()){
            counter = 0;
        }

        // determines if the player can jump
        collide = Detect.getCollide("Ground") && counter < 1;
        if(collide && !is_moving){
            counter++;
            player.applyForceToCenter(0, 200, false);
        }

        collide = false;
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }


    // there are some bugs if I try to put
    // this code on the same thread as everything else
    // so it gets it's own thread
    @Override
    public void run() {

        // notifies the class that the player is currently moving
        is_moving = true;

        // gets where the player last touched the screen
        float pos = Gdx.input.getX();
        while (Gdx.input.isTouched() && !collide){
            float vel = 0;

            // these are used to make a rectangle on either
            // end of the screen so the game knows which way
            // the player should move
            float backPoint1 = 0;
            float backPoint2 = 300;
            float forwardPoint1 = Gdx.graphics.getWidth()-backPoint2;
            float forwardPoint2 = Gdx.graphics.getWidth();

            if(pos >= backPoint1 && pos <= backPoint2){
                vel = -.03f;
                i.set(-1);
                event.setBool(true);
            }
            else if(pos >= forwardPoint1 && pos <= forwardPoint2){
                vel = .03f;
                i.set(1);
                event.setBool(true);
            }

            // sets the new position of the player
            player.setTransform(player.getPosition().x + vel, player.getPosition().y, 0);

            // tries to keep the movement of the player somewhat consistent with the
            // speed of the game
            try {
                Thread.sleep((long)(1000/Gdx.graphics.getFramesPerSecond()));
            }
            catch (Exception e){}
        }
        event.setBool(false);
        is_moving = false;
    }

    // used to figure out if the player is moving
    public static boolean getEvent(){
        boolean ret = false;
        if(event != null){
            ret = event.getBool();
        }
        return ret;
    }

    // fixes a bug when the game is switching between states
    public static void resetCounter(){
        counter = 0;
    }
}
