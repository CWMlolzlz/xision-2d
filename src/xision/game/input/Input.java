package xision.game.input;

import xision.math.vector.Vec2;

import javax.swing.event.MouseInputListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Connor on 7/08/2016.
 */
public final class Input extends KeyAdapter implements MouseInputListener{

    Set<Integer> keyMap = new HashSet<>();
    Map<Integer, Boolean> mouseMap = new HashMap<>();
    private Vec2 mousePosition = Vec2.ZERO;

    public boolean isKeyDown(int keyValue){
        return keyMap.contains(keyValue);
    }

    public boolean isKeyUp(int keyValue){
        return !keyMap.contains(keyValue);
    }

    public boolean isMouseDown(int mouseButton){
        return false;
    }

    public boolean isMouseUp(int mouseButton){
        return false;
    }

    public Vec2 mousePosition(){
        return mousePosition;
    }

    @Override
    public void keyPressed(KeyEvent e){
        keyMap.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e){
        keyMap.remove(e.getKeyCode());
    }


    @Override
    public void mouseClicked(MouseEvent e){
    }

    @Override
    public void mousePressed(MouseEvent e){
        mouseMap.put(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mouseMap.put(e.getButton(), false);
    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }

    @Override
    public void mouseDragged(MouseEvent e){

    }

    @Override
    public void mouseMoved(MouseEvent e){
        mousePosition = new Vec2(e.getX(), e.getY());
    }
}
