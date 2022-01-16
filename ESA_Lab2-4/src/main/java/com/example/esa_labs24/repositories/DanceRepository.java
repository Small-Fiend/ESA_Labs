package com.example.esa_labs24.repositories;

import com.example.esa_labs24.models.Dancer;
import org.springframework.data.repository.CrudRepository;

public interface DanceRepository extends CrudRepository<Dancer, Integer> {
}
