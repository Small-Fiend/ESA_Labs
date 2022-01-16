package com.example.esa_labs24.services;

import com.example.esa_labs24.models.DanceStudio;

import java.util.List;
import java.util.Optional;

public interface DanceStudioService {
    Optional<DanceStudio> findById(Integer id);
    List<DanceStudio> findAll();
    void save(DanceStudio danceStudio);
    void delete(DanceStudio danceStudio);
}
