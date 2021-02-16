package com.example.jsonvalidator.domain;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class SchemaValidatorTest {

    @Mock
    Schema schema;

    @InjectMocks
    SchemaValidator schemaValidator;

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

        doThrow(ValidationException.class).when(schema).validate(any(JSONObject.class));

        boolean actual = schemaValidator.valid(input);

        assertFalse(actual);
    }
}