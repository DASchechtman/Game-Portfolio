package Levels;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.finals.game.GlobalPlayer;
import com.finals.game.GlobalWorld;

import java.util.ArrayList;

import CollisionDetect.Detect;
import RefTypes.Bool;
import States.CurGameState;
import States.GameState;

import static TiledMapUtils.Utils.parseMap;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */


/*
this class draws the main level
 */
public class LevelOne extends GameState{
    // the state is a singleton
    private static LevelOne instance = null;

    // used to draw the map
    private static OrthogonalTiledMapRenderer tmr;

    // used to get the map
    private static TiledMap map;

    // saves the physics bodies from the map, so they can be
    // destroyed if nesecary
    private ArrayList<Body> bodyList = new ArrayList<Body>();

    // checks if the state has been switched, because
    // in the win and lose state, it keeps drawing the
    // main level and then draws text over that
    // so this makes sure that it's only switching
    // states once
    private boolean swiched_state = false;

    // used to set the postion of the camera
    private OrthographicCamera camera;

    private LevelOne(){
        init();
    }

    public static LevelOne getInstance(){
        if(instance == null){
            instance = new LevelOne();
        }
        return instance;
    }

    @Override
    public void draw(CurGameState state){

        // draws the map
        tmr.render();

        // used to check if the player meets certian conditions
        Body b = GlobalPlayer.getBody();
        try {
            if(!swiched_state) {
                // checks if the player loses
                if (b.getPosition().y < -50) {
                    state.changeState(LoseState.getInstance(this, camera));
                    swiched_state = true;
                }
                // check if the player wins
                else if (Detect.getCollide("Win")) {
                    state.changeState(WinState.getInstance(this, camera));
                    swiched_state = true;
                }
            }
        }
        catch (Exception e){}
    }

    @Override
    public void init(){
        // sets up the map, and gets the physics objects from it
        map = new TmxMapLoader().load("lev.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        parseMap(map.getLayers().get("collide-layer").getObjects(), bodyList);
    }

    // frees up resources
    public static void dispose(){
        map.dispose();
        tmr.dispose();
    }

    @Override
    public void updateCam(OrthographicCamera c){
        tmr.setView(c);
        if(camera == null){
            camera = c;
        }
    }

}
