package com.vogeez.jilou.events.widgets;

import com.vogeez.jilou.logic.trigger.Event;
import com.vogeez.jilou.ui.widgets.AbstractWidget;

public record WidgetRemoveEvent(AbstractWidget widget, AbstractWidget parent, AbstractWidget container) implements Event { }
