package com.example.esa_labs24.services;

import com.example.esa_labs24.models.DanceClass;

import java.util.List;
import java.util.Optional;

public interface DanceClassService {
    Optional<DanceClass> findById(Integer id);
    List<DanceClass> findAll();
    void save(DanceClass danceClass);
    void delete(DanceClass danceClass);
    List<DanceClass> findByName(String name);
}
