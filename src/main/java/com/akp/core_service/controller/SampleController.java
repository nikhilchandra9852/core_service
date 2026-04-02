package com.akp.core_service.controller;

import com.akp.core_service.model.Sample;
import com.akp.core_service.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleController {
    private final SampleService service;

    @GetMapping
    public List<Sample> getAll() {
        return service.getAll();
    }
}
