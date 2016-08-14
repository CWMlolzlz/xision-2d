package xision.game.input;

/**
 * Created by Connor on 7/08/2016.
 */
public enum MouseButton{
    LEFT,
    RIGHT,
    MIDDLE;

    public static MouseButton fromInt(int i){
        return MouseButton.values()[i];
    }
}
