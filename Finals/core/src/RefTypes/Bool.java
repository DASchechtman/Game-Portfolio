package RefTypes;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */


/*
I needed to pass a boolean value by referance in this game
so I made this class
 */
public class Bool {
    private boolean bool;
    private static Bool b = null;

    public Bool(boolean val){
        bool = val;
    }
    private Bool(){bool = false; }

    public static Bool getInstance(){
        if(b == null){
            b = new Bool();
        }
        return b;
    }

    public void setBool(boolean val){
        bool = val;
    }

    public boolean getBool(){
        return bool;
    }
}
