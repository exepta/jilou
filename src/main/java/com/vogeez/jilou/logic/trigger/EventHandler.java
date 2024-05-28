package com.vogeez.jilou.logic.trigger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    EventPriority priority() default EventPriority.MODERATE;

    boolean ignoreCancelled() default false;

}
