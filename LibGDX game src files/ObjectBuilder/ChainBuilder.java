package ObjectBuilder;

import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;

import static Constants.ConstValues.PPM;

/**
 * Created by Daniel Schechtman on 5/8/2017.
 */

public class ChainBuilder {
    // I used a program to make a map for the game, so I need
    // decode that map so I can get the physics objects out of it
    // because of time constraints, I copied/pasted this code here without
    // understanding much of what it's doing. I just know it works
    public static ChainShape createChainShape(PolylineMapObject object){
        float[] verts = object.getPolyline().getTransformedVertices();
        Vector2[] worldVerts = new Vector2[verts.length/2];
        for(int i = 0; i < worldVerts.length; i++){
            worldVerts[i] = new Vector2(verts[i*2]/PPM, verts[i*2+1]/PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVerts);
        return cs;
    }
}
