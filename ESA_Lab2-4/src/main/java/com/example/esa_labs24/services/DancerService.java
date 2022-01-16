package com.example.esa_labs24.services;

import com.example.esa_labs24.models.Dancer;

import java.util.List;
import java.util.Optional;

public interface DancerService {
    Optional<Dancer> findById(Integer id);
    List<Dancer> findAll();
    void save(Dancer dancer);
    void delete(Dancer dancer);
}