package ObjectBuilder;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.finals.game.GlobalWorld;

import static Constants.ConstValues.PPM;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */

/*
this class just has one function that is used to create a box body
 */
public class BoxBuilder {
    public static Body createBody(float x, float y, float width, float height, boolean isStatic){
        Body body;

        // creates a definition for the body
        BodyDef def = new BodyDef();

        // determines if the body is affected by forces or not
        if(isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }
        else {
            def.type = BodyDef.BodyType.DynamicBody;
        }


        // sets the position of the body
        def.position.set(x / PPM , y / PPM);


        // creates the body
        body = GlobalWorld.getInstance().createBody(def);

        // creates the shape of the body
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        // adds that shap to the body
        body.createFixture(shape, 1.0f);

        shape.dispose();
        return body;
    }

}
