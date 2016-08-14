package xision.events;

/**
 * Created by Connor on 12/08/2016.
 */
public interface EventBinder<T,E>{

    void bind(T input, E listener);

    void unbind(T input);

    void unbind(T input, E listener);

    //void dispatch(E event);

}
