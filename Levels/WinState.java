package Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finals.game.GlobalBatch;

import RefTypes.Bool;
import States.CurGameState;
import States.GameState;

/**
 * Created by Daniel Schechtman on 5/14/2017.
 */

/*
this state tells the player they won
this state works the exact same as LoseState, but displays "You Won"
 */

public class WinState extends GameState {
    private static WinState winner = null;
    private GameState state = null;
    private static BitmapFont font = null;
    private OrthographicCamera camera;

    private WinState(GameState s, OrthographicCamera c){
        state = s;
        font = new BitmapFont();
        font.getData().scale(3);
        camera = c;
    }

    public static WinState getInstance(GameState s, OrthographicCamera c){
        if(winner == null){
            winner = new WinState(s, c);
        }
        return winner;
    }

    @Override
    public void draw(CurGameState state){
        this.state.draw(state);
        SpriteBatch batch = GlobalBatch.getInstance();
        Bool.getInstance().setBool(false);
        batch.begin();
        font.draw(batch, "You Win", Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);
        batch.end();
        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        camera.update();
    }

    public static void dispose(){
        font.dispose();
    }
}
