package com.example.esa_labs24.services;

import com.example.esa_labs24.jms.DataModificationTopic;
import com.example.esa_labs24.models.DanceStudio;
import com.example.esa_labs24.repositories.DanceStudioRepository;
import com.example.esa_labs24.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.util.List;
import java.util.Optional;

public class DanceStudioServiceImpl implements DanceStudioService {
    private final DanceStudioRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public DanceStudioServiceImpl(DanceStudioRepository repository, JmsTemplate template) throws JMSException {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic(template);
        //dataModificationTopic.subscribe(factory.createEmailLoggerListener());
        //dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<DanceStudio> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<DanceStudio> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(DanceStudio danceStudio) {
        repository.save(danceStudio);
        dataModificationTopic.sendInsertEvent("DanceStudio", danceStudio);
    }

    @Override
    public void delete(DanceStudio danceStudio) {
        repository.delete(danceStudio);
        dataModificationTopic.sendDeleteEvent("DanceStudio", danceStudio);
    }
}
