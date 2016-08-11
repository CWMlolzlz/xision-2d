package xision.game.input;

import xision.game.GameObject;
import xision.game.XisionGame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moodyconn on 8/08/16.
 */
public abstract class Binder<T> extends GameObject{

	public Binder(XisionGame game, GameObject parent){
		super(game, parent);
	}

	private final Map<T, Runnable> runners = new HashMap<>();
	public void bind(T key, Runnable r){
		runners.put(key,r);
	}

	public void bind(T key, Runnable... rs){
		runners.put(key,() -> {
			for(Runnable r : rs){
				r.run();
			}
		});
	}

	public void unbind(T key){
		runners.remove(key);
	}

	public final void draw(Graphics2D g){}

	public final void update(float delta){
		runners.forEach((key, functions) -> {
			if(isOn(key)){
				functions.run();
			}
		});
	}

	protected abstract boolean isOn(T key);
}
