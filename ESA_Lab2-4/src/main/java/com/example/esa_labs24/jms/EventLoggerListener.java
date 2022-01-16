package com.example.esa_labs24.jms;

import com.example.esa_labs24.models.Event;
import com.example.esa_labs24.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventLoggerListener implements EventListener{
    @Autowired
    private EventService eventService;

    @Override
    @JmsListener(destination="event")
    public void update(Event event) {
        eventService.save(event);
    }
}
