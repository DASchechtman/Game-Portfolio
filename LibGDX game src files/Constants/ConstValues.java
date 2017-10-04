package Constants;

/**
 * Created by Daniel Schechtman on 5/3/2017.
 */

public class ConstValues {
    // this class is just to get the actual size of the
    // body. when I create a body box2d assumes 1 pixel = 1 meter
    // which is way to big of a calculation, so this variable scales
    // that down, so there is better performance
    public static final float PPM = 128;
}
