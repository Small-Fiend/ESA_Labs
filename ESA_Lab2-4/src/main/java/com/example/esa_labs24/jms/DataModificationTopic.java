package com.example.esa_labs24.jms;

import com.example.esa_labs24.models.Event;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.jms.JmsException;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataModificationTopic {
    ArrayList<EventListener> listenerList = new ArrayList<>();
    JmsTemplate jmsTemplate;
    Topic eventTopic;

    public DataModificationTopic(JmsTemplate jmsTemplate) throws JmsException, JMSException {
        this.jmsTemplate = jmsTemplate;
        this.eventTopic = jmsTemplate.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE).createTopic("event");
    }

    public void subscribe(EventListener listener) {
        listenerList.add(listener);
    }

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("update", entity, value.toString());
        updateListeners(event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("insert", entity, value.toString());
        updateListeners(event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("delete", entity, value.toString());
        updateListeners(event);
    }

    public void updateListeners(Event event) {
        jmsTemplate.convertAndSend(eventTopic, event);
        //for (EventListener listener: listenerList)
        //    listener.update(event);
    }
}
