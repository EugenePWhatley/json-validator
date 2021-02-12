package com.example.jsonvalidator.rest;

import com.example.jsonvalidator.domain.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ValidatorController {

    @Autowired
    private ValidatorService validatorService;

    @PostMapping(value = "validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity valid(@RequestBody Map<String, Object> request) {
        boolean result = validatorService.validate(request);
        return ResponseEntity.ok().body(Map.of("valid", result));
    }
}
