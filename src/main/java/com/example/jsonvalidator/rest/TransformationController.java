package com.example.jsonvalidator.rest;

import com.example.jsonvalidator.domain.TransformationService;
import com.fasterxml.jackson.databind.JsonNode;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformationController {

    @Autowired
    TransformationService transformationService;

    @PostMapping(value = "transform", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transform(@RequestBody JsonNode request) throws JsonQueryException {
        return ResponseEntity.ok().body(transformationService.transform(request));
    }
}
