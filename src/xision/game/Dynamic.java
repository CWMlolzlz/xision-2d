package xision.game;

/**
 * Created by Connor on 6/08/2016.
 */
public interface Dynamic{
    /**
     * Called before every frame
     */
    void update(float delta);

    /**
     * Called after every frame
     */
    void lateUpdate();
}
