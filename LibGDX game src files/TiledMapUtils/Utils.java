package TiledMapUtils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.finals.game.GlobalWorld;

import java.util.ArrayList;
import java.util.HashMap;

import ObjectBuilder.BoxBuilder;
import ObjectBuilder.ChainBuilder;
import RefTypes.Bool;

import static Constants.ConstValues.PPM;

/**
 * Created by Daniel Schechtman on 5/8/2017.
 */

public class Utils {

    // gets the physics objects out of the map
    public static void parseMap(MapObjects object, ArrayList<Body> b){

        // gets all the objects from the map
        for(MapObject obj : object){
            Shape shape;
            Body body;

            // makes sure the object can be parsed
            if(obj instanceof PolylineMapObject){
                shape = ChainBuilder.createChainShape((PolylineMapObject)obj);
            }
            else {
                continue;
            }

            // gets the world to create the body
            World world = GlobalWorld.getInstance();

            // creates a body defintions
            BodyDef def = new BodyDef();

            // makes sure the body can't be moved
            def.type = BodyDef.BodyType.StaticBody;


            // creates the body
            body = world.createBody(def);

            // gives the body a shape
            body.createFixture(shape, 1.0f);

            // gives the body an id so the game knows how to use it
            body.setUserData(obj.getName());

            // saves the body to an array for later use
            b.add(body);

            // frees resources
            shape.dispose();

        }
    }
}
