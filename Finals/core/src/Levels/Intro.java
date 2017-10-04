package Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.finals.game.GlobalBatch;
import com.finals.game.GlobalPlayer;
import com.finals.game.GlobalWorld;

import java.util.ArrayList;

import CollisionDetect.Detect;
import Input.UserInput;
import States.CurGameState;
import States.GameState;

import static Constants.ConstValues.PPM;
import static TiledMapUtils.Utils.parseMap;

/**
 * Created by Daniel Schechtman on 5/9/2017.
 */

/*
this state draws the intro of the game and controls the logic
 */

public class Intro extends GameState implements Runnable {
    // I made this state a singleton
    private static Intro intro = null;

    // this draws the map
    private static OrthogonalTiledMapRenderer tmr;

    // this sets the camera
    private static OrthographicCamera cam = null;

    // gets the game map
    private static TiledMap map;

    // used because certain aspects of the code
    // requires an infinite loop
    private Thread thread;

    // used to draw shapes to show players the game machanics
    private static ShapeRenderer shape;

    // used to set the position of the rectangle on screen
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    // draws text to the screen
    private BitmapFont font;

    // text that will be drawn to the screen
    private String message = "move forward";

    // needs to keep a list of bodies so when the game changes
    // states, they can be destroyed, and the physics engine
    // doesn't detect unnessecary collisions
    private ArrayList<Body> bodyList = new ArrayList<Body>();

    // acts as the body of the player
    private Body body;

    private Intro(Body b){
        // creates and starts a new thread
        thread = new Thread(this);
        thread.start();

        // creates the text drawer object
        // and scales it up so it is readable
        font = new BitmapFont();
        font.getData().scale(1);

        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        // sets where the first rectangle should
        // be drawn
        x1 = Gdx.graphics.getWidth()-300;
        y1 = 0;
        x2 = Gdx.graphics.getWidth();
        y2 = Gdx.graphics.getHeight();

        // saves the player's body
        body = b;

        // does some further set up
        init();
    }

    public static Intro getInstance(Body b){
        if(intro == null){
            intro = new Intro(b);
        }
        return intro;
    }

    @Override
    public void draw(CurGameState state){
        // draw the map
        tmr.render();

        // draws the shape to indecate to the player
        // where to touch the screen to move the player
        shape.setColor(new Color(255, 0, 0, 100));
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(x1, y1, x2, y2);
        shape.end();

        // creates a new spritebatch object for drawing
        SpriteBatch batch = GlobalBatch.getInstance();

        float y = Gdx.graphics.getHeight()/4;
        float w = font.getSpaceWidth() * message.length();

        batch.begin();
        font.draw(batch, message, body.getPosition().x*PPM - w/2 , body.getPosition().y *PPM + y);
        batch.end();

        // used to determine when to change to the next state
        boolean finised_action = x1 == -1 && message.equals("Tap to jump");
        boolean is_grounded = Detect.getCollide("Ground");

        if(finised_action){
            if(is_grounded && UserInput.getEvent()) {
                cam.position.set(500, 500, 0);
                cam.update();
                for (Body b : bodyList) {
                    b.getWorld().destroyBody(b);
                }
                UserInput.resetCounter();
                state.changeState(LevelOne.getInstance());
                intro = null;
            }
        }
    }


    @Override
    public void init(){

        // creates the map
        map = new TmxMapLoader().load("intro.tmx");

        // creates a way to draw the map
        tmr = new OrthogonalTiledMapRenderer(map);

        // gets the physics objects out of the map
        parseMap(map.getLayers().get("collide-layer").getObjects(), bodyList);
    }

    @Override
    public void updateCam(OrthographicCamera c){
        if(cam == null){
            cam = c;
        }
        tmr.setView(c);
    }

    @Override
    public void run() {
        Boolean temp = null;
        int loc = 0;
        while (true) {

            // first checks to see if the user starts moving
            while (UserInput.getEvent()) {
                if(x1 > 0){
                    loc = x1;
                }
                x1 = -1;
                y1 = -1;
                x2 = -1;
                y2 = -1;
                if(temp == null) {
                    temp = true;
                }
            }


            // if the user has completed the tutorial
            // exit the thread
            if(temp != null && !temp){
                message = "Tap to jump";
                return;
            }


            // changes the location of the rectanlge to the
            // oposite side of the screen
            if (loc == Gdx.graphics.getWidth()-300) {
                x1 = 0;
                y1 = 0;
                x2 = 300;
                y2 = Gdx.graphics.getHeight();
                message = "move backwards";
            }


            // makes the rectangle disappear
            if (UserInput.getEvent() && loc == Gdx.graphics.getWidth()-300) {
                x1 = -1;
                y1 = -1;
                x2 = -1;
                y2 = -1;
                temp = false;
            }
        }
    }


    // frees up resources
    public static void dispose(){
        tmr.dispose();
        map.dispose();
        shape.dispose();
    }
}
