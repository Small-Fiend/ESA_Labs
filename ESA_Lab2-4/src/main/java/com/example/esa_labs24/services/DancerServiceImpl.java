package com.example.esa_labs24.services;

import com.example.esa_labs24.jms.DataModificationTopic;
import com.example.esa_labs24.models.Dancer;
import com.example.esa_labs24.repositories.DanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.esa_labs24.utils.Converter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.util.List;
import java.util.Optional;

public class DancerServiceImpl implements DancerService{
    private final DanceRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public DancerServiceImpl(DanceRepository repository, JmsTemplate template) throws JMSException {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic(template);
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Dancer> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Dancer> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Dancer dancer) {
        repository.save(dancer);
        dataModificationTopic.sendInsertEvent("Dancer", dancer);
    }

    @Override
    public void delete(Dancer dancer) {
        repository.delete(dancer);
        dataModificationTopic.sendDeleteEvent("Dancer", dancer);
    }
}
