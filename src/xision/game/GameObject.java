package xision.game;

import xision.events.PropertyChangedListener;
import xision.graphics.Drawable;
import xision.math.vector.Vec2;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Connor on 6/08/2016.
 */
public abstract class GameObject implements Drawable, Dynamic{

    private Map<String,List<Runnable>> listeners = new HashMap<>();

    protected Vec2 position = Vec2.ZERO;
    protected Vec2 size = Vec2.ZERO;
    protected float rotation = 0;
    protected Vec2 pivot = new Vec2(0.5f,0.5f);

    protected final XisionGame game;
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

    @SuppressWarnings("unchecked")
    public <T extends GameObject> T getChild(Class<T> cluzz){
        return children.stream().filter(cluzz::isInstance).map(go -> (T) go).findFirst().orElse(null);
    }

    public void setPosition(Vec2 vec){
        this.position = vec;
        firePropertyChanged("position");

    }

    public void setPosition(float x, float y){
        this.setPosition(new Vec2(x,y));
    }

    public void move(Vec2 v){
        this.setPosition(this.position.add(v));
    }

    public void move(float x, float y){
        move(new Vec2(x,y));
    }

    protected abstract void draw(Graphics2D g);

    public void lateUpdate(){}

    //listener dispatches

    public final void addListener(String property, PropertyChangedListener<?> listener){

        Object tempInstance = this;

        //find field to tie to
        Class cluzz = this.getClass();
        Field tempField = null;
        while(GameObject.class.isAssignableFrom(cluzz)){
            try{
                tempField = cluzz.getDeclaredField(property);
                break;
            }catch(NoSuchFieldException ignored){}
            cluzz = cluzz.getSuperclass();
            tempInstance = cluzz.cast(tempInstance);
        }

        if(tempField == null) throw new IllegalArgumentException(property +" does not exist in " + this.getClass().getName());

        final Field field = tempField;
        final Object instance = tempInstance;

        Method eventMethod = listener.getClass().getMethods()[0];
        eventMethod.setAccessible(true);
        Class<?> acceptingType = eventMethod.getParameterTypes()[0];

        Runnable runnable = () -> {
            try{
                eventMethod.invoke(listener,acceptingType.cast(field.get(this)));
            }catch(IllegalAccessException | InvocationTargetException e){
                e.printStackTrace();
            }
        };

        listeners.putIfAbsent(property, new ArrayList<>());
        listeners.get(property).add(runnable);
    }

    private void firePropertyChanged(String property){
        if(listeners.containsKey(property)){
            listeners.get(property).forEach(Runnable::run);
        }
    }



}
