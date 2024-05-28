package com.vogeez.jilou.events;

import com.vogeez.jilou.logic.trigger.Event;
import lombok.Getter;

public record JILOUPreLoadEvent(Class<?> applicationClass) implements Event { }
