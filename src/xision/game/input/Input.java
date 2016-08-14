package xision.game.input;

import xision.math.vector.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

/**
 * Created by Connor on 7/08/2016.
 */
public final class Input extends InputAdapter{

    private EnumSet<Key> keyMap = EnumSet.noneOf(Key.class);
    private EnumSet<MouseButton> mouseMap = EnumSet.noneOf(MouseButton.class);
    private Vec2 mousePosition = Vec2.ZERO;

    public boolean isKeyDown(Key keyValue){
        return keyMap.contains(keyValue);
    }

    public boolean isKeyUp(Key keyValue){
        return !isKeyDown(keyValue);
    }

    public boolean isMouseDown(MouseButton mouseButton){
        return mouseMap.contains(mouseButton);
    }

    public boolean isMouseUp(MouseButton mouseButton){
        return !isMouseDown(mouseButton);
    }

    public Vec2 mousePosition(){
        return mousePosition;
    }

    @Override
    public void keyPressed(KeyEvent e){
        keyMap.add(Key.fromInt(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e){
        keyMap.remove(Key.fromInt(e.getKeyCode()));
    }

    @Override
    public void mousePressed(MouseEvent e){
        mouseMap.add(MouseButton.fromInt(e.getButton()));
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mouseMap.remove(MouseButton.fromInt(e.getButton()));
    }

    @Override
    public void mouseMoved(MouseEvent e){
        mousePosition = new Vec2(e.getX(), e.getY());
    }
}
