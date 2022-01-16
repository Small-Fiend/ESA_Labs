package com.example.esa_labs24.jms;

import com.example.esa_labs24.models.Event;

public interface EventListener {
    void update(Event event);
}
