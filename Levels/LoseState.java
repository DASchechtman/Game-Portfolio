package Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.finals.game.GlobalBatch;
import com.finals.game.GlobalPlayer;

import States.CurGameState;
import States.GameState;

/**
 * Created by Daniel Schechtman on 5/14/2017.
 */

/*
this state tells the player they lost
 */
public class LoseState extends GameState {
    // this state is a singleton
    private static LoseState loser = null;

    // needs the game state so it can draw
    // the main level
    private GameState state = null;

    // draws the text to the screen
    private static BitmapFont font = null;

    // coordinates for where the text should be drawn
    private float x = Gdx.graphics.getWidth()/2;
    private float y = Gdx.graphics.getHeight()/2;

    // used to focus where the text is
    private OrthographicCamera camera;

    private LoseState(GameState s, OrthographicCamera c){
        state = s;
        font = new BitmapFont();
        font.getData().scale(3);
        camera = c;
    }

    public static LoseState getInstance(GameState s, OrthographicCamera c){
        if(loser == null){
            loser = new LoseState(s, c);
        }
        return loser;
    }

    @Override
    public void draw(CurGameState state){
        // draws the map of the main level
        this.state.draw(state);

        // used to draw the text
        SpriteBatch batch = GlobalBatch.getInstance();

        // draw the text to screen
        batch.begin();
        font.draw(batch, "You Lose", x, y);
        batch.end();

        // updates camera position
        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        camera.update();
    }

    // frees up resources
    public static void dispose(){
        font.dispose();
    }
}
