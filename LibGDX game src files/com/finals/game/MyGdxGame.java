package com.finals.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import CollisionDetect.Detect;
import Input.UserInput;
import Levels.Intro;
import Levels.LevelOne;
import Levels.LoseState;
import Levels.WinState;
import ObjectBuilder.BoxBuilder;
import RefTypes.Bool;
import RefTypes.Int;
import States.CurGameState;

import static Constants.ConstValues.PPM;


// this is the class from which everything is run
// so this is Main
public class MyGdxGame extends ApplicationAdapter{

    // below are just referances to all the variables
    // needed to run the program

    // used to determine where in the game world
    // the person playing this game can see
    private OrthographicCamera camera;

    // used for alot of the rendering in the game
    private SpriteBatch batch;

    // used for all the Physics calculations in the game
    private World world;

    // this will draw the current state of the game
    private CurGameState state;

    // this will hold all the images for the character
    // animation
    private TextureAtlas atlas;

    // this will animate the character
    private Animation<TextureRegion> animation;

    // used to create the body that the sprite will
    // be attached to
    private Body body;

    // demensions for the sprite
    // and physics body
    private float x;
    private float y;
    private float w;
    private float h;

    // the Animation variable above
    // will figure out what image to show onscreen
    // but Main needs to be the time keeper
    // so it knows what image to show
    private float timePassed = 0;

    // used to help determine which way the character should be faced
    private Int i;

    @Override
    public void create(){
        i = new Int(0);

        // sets where the camera should look
        x = 500;
        y = 500;

        // sets the size of the body so it
        // fits the sprite
        w = 70;
        h = 94;

        camera = new OrthographicCamera();

        // performs nessecary actions for the camera to function
        // don't ask me why it wasn't done in the constructor
        camera.setToOrtho(false, x, y);

        // sets where the camera should look
        camera.position.set(x, y, 0);

        // sets the size of the camera window
        camera.viewportWidth = 1000;
        camera.viewportHeight = 1000;

        // applies all the changes to the camera
        camera.update();


        // creates a new Global world, and allows Main to use it
        world = GlobalWorld.getInstance(new World(new Vector2(0.0f, -9.8f), false));

        // allows for collision detection
        world.setContactListener(new Detect());


        // same thing as World above, except for SpriteBatch
        batch = GlobalBatch.getInstance(new SpriteBatch());


        // creates the body
        body = BoxBuilder.createBody(x, y, w, h, false);

        // sets an id for the body
        body.setUserData("Player");

        // gets all the images needed for the animation
        atlas = new TextureAtlas("character.atlas");

        // same thing as World but with the player object
        animation = GlobalPlayer.getInstance(new Animation<TextureRegion>(1f/60f, atlas.getRegions()), body);


        // creates the state
        state = new CurGameState(body);


        // creates a new user input
        UserInput input = new UserInput(body, new Bool(false), i);

        // makes the object that allows for an input object to be
        // used
        InputMultiplexer im = new InputMultiplexer();

        // adds the input object
        im.addProcessor(new GestureDetector(input));

        // lets the game recieve inputs
        Gdx.input.setInputProcessor(im);

        // used to determine if the player has won or lost
        // so the camera can know to stop following the player
        Bool.getInstance().setBool(true);

    }


    // this method is used to actually draw everything to the screen
    @Override
    public void render(){

        // just stuff needed by libgdx
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // does all the non-drawing stuff.
        update();


        // draws the player
        batch.begin();
        batch.draw(animation.getKeyFrame(timePassed, UserInput.getEvent()),body.getPosition().x*PPM - w/2, body.getPosition().y*PPM - h/2);
        batch.end();

        // calculates the physics
        world.step(1.0f/60.0f, 6, 2);
    }


    // libgdx won't automatically free things from memory, so
    // this method is called that does that
    @Override
    public void dispose(){
        GlobalWorld.dispose();
        GlobalBatch.dispose();
        LevelOne.dispose();
        WinState.dispose();
        LoseState.dispose();
        Intro.dispose();
    }

    // this method deals with camera movement
    private void updateCam(){
        // checks if the player is still player
        if(Bool.getInstance().getBool()) {

            // gets the position of the camera
            Vector3 pos = camera.position;

            // variables used to determine when the player
            // has reached the edge of the map so the camera
            // stops following
            int xBound = 135;
            int yBound = 350;

            // determine if the camera should stop following
            // if the player is moving in the x plane
            if (body.getPosition().x * PPM > Gdx.graphics.getWidth() + xBound) {
                pos.x = Gdx.graphics.getWidth() + xBound;
            } else if (body.getPosition().x * PPM > x) {
                pos.x = body.getPosition().x * PPM;
            } else if (body.getPosition().x * PPM <= x) {
                pos.x = x;
            }


            // determine if the camera should stop following
            // if the player is moving in the y plane
            if (body.getPosition().y * PPM > Gdx.graphics.getHeight() + yBound) {
                pos.y = Gdx.graphics.getHeight() + yBound;
            } else if (body.getPosition().y * PPM > y) {
                pos.y = body.getPosition().y * PPM;
            } else if (body.getPosition().y * PPM <= y) {
                pos.y = y;
            }

            // sets the new position and applies it
            camera.position.set(pos);
            camera.update();
        }
    }

    private void update(){

        // gets the time since the game has started
        timePassed += Gdx.graphics.getDeltaTime();

        // keeps track of the time
        GlobalPlayer.setTimeEllapsed(timePassed);


        // checks if the player animation should be facing left or right
        if(!animation.getKeyFrame(timePassed, UserInput.getEvent()).isFlipX() && i.getInt() == -1){
            animation.getKeyFrame(timePassed, UserInput.getEvent()).flip(true, false);
        }
        else if(animation.getKeyFrame(timePassed, UserInput.getEvent()).isFlipX() && i.getInt() == 1){
            animation.getKeyFrame(timePassed, UserInput.getEvent()).flip(true, false);
        }


        // updates the camera
        updateCam();

        // draws the game
        state.draw();

        // the state needs the camera
        // so that is passed into the state
        state.updateCam(camera);
        batch.setProjectionMatrix(camera.combined);
    }

}
