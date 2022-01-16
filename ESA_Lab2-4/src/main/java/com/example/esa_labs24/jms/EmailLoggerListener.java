package com.example.esa_labs24.jms;

import com.example.esa_labs24.models.Email;
import com.example.esa_labs24.models.Event;
import com.example.esa_labs24.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

@Component
public class EmailLoggerListener implements EventListener {
    @Autowired
    private EmailService emailService;

    @JmsListener(destination="event")
    @Override
        public void update(Event event) {
        String msg = String.format("%s happend.", event.getAction());
        Email email = new Email(msg, "admin@mail.ru");
        emailService.save(email);
    }
}
