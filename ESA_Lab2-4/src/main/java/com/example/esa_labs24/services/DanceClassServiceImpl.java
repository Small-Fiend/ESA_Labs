package com.example.esa_labs24.services;

import com.example.esa_labs24.jms.DataModificationTopic;
import com.example.esa_labs24.models.DanceClass;
import com.example.esa_labs24.repositories.DanceClassRepository;
import com.example.esa_labs24.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class DanceClassServiceImpl implements DanceClassService {
    private final DanceClassRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public DanceClassServiceImpl(DanceClassRepository repository, JmsTemplate template) throws JMSException {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic(template);
        //dataModificationTopic.subscribe(factory.createEmailLoggerListener());
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<DanceClass> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<DanceClass> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(DanceClass danceClass) {
        repository.save(danceClass);
        dataModificationTopic.sendInsertEvent("DanceClass", danceClass);
    }

    @Override
    public void delete(DanceClass danceClass) {
        repository.delete(danceClass);
        dataModificationTopic.sendDeleteEvent("DanceClass", danceClass);
    }

    @Override
    public List<DanceClass> findByName(String name) {
        return repository.findByName(name);
    }

}
