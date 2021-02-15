package com.example.jsonvalidator.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchemaValidatorTest {

    SchemaValidator schemaValidator;

    @BeforeEach
    void setUp() {
        schemaValidator = new SchemaValidator();
    }

    @Test
    void shouldValidateGoodDataObjectAgainstSchema() {
        Map<String, Object> input = new HashMap<>();
        input.put("id", 123);
        input.put("name", "Eugene");
        input.put("age", 27);

        boolean actual = schemaValidator.valid(input);

        assertTrue(actual);
    }

    @Test
    void shouldValidateBadDataObjectAgainstSchema() {
        Map<String, Object> input = new HashMap<>();
        input.put("id", "123");
        input.put("name", false);

        boolean actual = schemaValidator.valid(input);

        assertFalse(actual);
    }
}