package RefTypes;

/**
 * Created by Daniel Schechtman on 5/9/2017.
 */

/*
I needed to pass an integer by referance
so I mad this class
 */

public class Int {
    private int val;
    public Int(int v){
        val = v;
    }

    public void set(int v){
        val = v;
    }

    public int getInt(){
        return val;
    }
}
