package xision.events;

import xision.game.GameObject;
import xision.game.XisionGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by moodyconn on 8/08/16.
 */
public abstract class PolledBinder<T> extends GameObject implements EventBinder<T, Runnable>{

	public PolledBinder(XisionGame game, GameObject parent){
		super(game, parent);
	}

	private final Map<T, List<Runnable>> runners = new HashMap<>();

    @Override
	public void bind(T key, Runnable r){
	    runners.putIfAbsent(key, new ArrayList<>());
		runners.get(key).add(r);
	}

	public void bind(T key, Runnable... rs){
	    for(Runnable r : rs){
	        bind(key,r);
        }
	}

	public void unbind(T key){
		runners.remove(key);
	}

    public void unbind(T key, Runnable r){
        if(runners.containsKey(key)){
            runners.get(key).remove(r);
        }
    }

	public final void draw(Graphics2D g){}

	public final void update(float delta){
        runners.forEach((key, functions) -> {
            if(isOn(key)){
                functions.forEach(Runnable::run);
            }
        });
	}

	protected abstract boolean isOn(T key);
}
