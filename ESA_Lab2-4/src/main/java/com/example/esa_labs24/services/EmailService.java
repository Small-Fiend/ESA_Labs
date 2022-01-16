package com.example.esa_labs24.services;

import com.example.esa_labs24.models.Email;

import java.util.List;
import java.util.Optional;

public interface EmailService {
    Optional<Email> findById(Integer id);
    List<Email> findAll();
    void save(Email email);
    void delete(Email email);
}
