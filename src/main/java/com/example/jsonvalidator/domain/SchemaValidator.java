package com.example.jsonvalidator.domain;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SchemaValidator {

    @Autowired
    private Schema schema;

    public boolean valid(Map<String, Object> request) {
        if (schema == null) return false;
        try {
            schema.validate(new JSONObject(request));
        } catch (ValidationException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
