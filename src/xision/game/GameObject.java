package xision.game;

import xision.graphics.Drawable;
import xision.math.vector.Vec2;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Connor on 6/08/2016.
 */
public abstract class GameObject implements Drawable, Dynamic{

    protected final XisionGame game;
    protected Vec2 position = Vec2.ZERO;
    protected Vec2 size = Vec2.ZERO;
    protected float rotation = 0;
    protected Vec2 pivot = new Vec2(0.5f,0.5f);
    private GameObject parent = null;
    private Set<GameObject> children = new HashSet<>();

    protected GameObject(XisionGame xisionGame, GameObject parent){
        this.game = xisionGame;
        this.game.addGameObject(this);
        this.parent = parent;
        if(this.parent != null){
            this.parent.addChild(this);
        }
    }

    public GameObject getParent(){
        return parent;
    }

    public void addChild(GameObject newChild){
        if(newChild.owns(this)){ //new child owns this game object
            throw new RuntimeException("A new child may not already own the new parent");
        }
        if(newChild.parent != null){
            newChild.parent.removeChild(newChild);
        }
        this.children.add(newChild);
        newChild.parent = this;
    }

    public void removeChild(GameObject oldChild){
        children.remove(oldChild);
    }

    public boolean owns(GameObject go){
        for(GameObject child : children){
            return child == go || child.owns(go);
        }
        return false;
    }

    public final void render(Graphics2D g){
        float dx = position.x;
        float dy = position.y;

        float rot = rotation;

        float px = pivot.x * size.x;
        float py = pivot.y * size.y;

        g.translate(dx, dy);
        g.rotate(rot, px, py);

        this.draw(g);
        children.forEach((child) -> child.render(g));

        g.rotate(-rot, px, py);
        g.translate(-dx, -dy);
    }

    public Collection<GameObject> getChildren(){
        return getChildren(GameObject.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> Collection<T> getChildren(Class<T> cluzz){
        return Collections.unmodifiableCollection(children.stream().filter(cluzz::isInstance).map(go -> (T) go).collect(Collectors.toSet()));
    }

    public <T extends GameObject> T getChild(Class<T> cluzz){
        return children.stream().filter(cluzz::isInstance).map(go -> (T) go).findFirst().orElse(null);
    }


    public void move(Vec2 v){
        this.position = this.position.add(v);
    }
    public void move(float x, float y){
        move(new Vec2(x,y));
    }

    protected abstract void draw(Graphics2D g);

    public void lateUpdate(){}


}
