package com.akp.core_service.service;

import com.akp.core_service.model.Sample;
import com.akp.core_service.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository repository;

    public List<Sample> getAll() {
        return repository.findAll();
    }
}
