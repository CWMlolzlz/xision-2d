package xision.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Connor on 16/08/2016.
 * Annotation used to mark fields within a {@link xision.game.GameObject} that are allowed
 * to broadcast change events upon the field being modified.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Property{}
