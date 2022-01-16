package com.example.esa_labs24.repositories;

import com.example.esa_labs24.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
