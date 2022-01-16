package com.example.esa_labs24.repositories;

import com.example.esa_labs24.models.DanceClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DanceClassRepository extends CrudRepository <DanceClass, Integer>{
    List<DanceClass> findByName(String name);
}
