package com.example.jsonvalidator.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorServiceTest {

    private ValidatorService subject;

    @BeforeEach
    void setUp() {
        subject = new ValidatorService("schema.json");
    }

    @Test
    void shouldInstantiateValidator() {
        assertNotNull(subject);
    }

    @Test
    void shouldValidateSchemaExist() {
        boolean actual = subject.doesSchemaExist();

        assertTrue(actual);
    }

    @Test
    void shouldValidateGoodDataFromFileAgainstSchema() {
        boolean actual = subject.validate("data.json");

        assertTrue(actual);
    }

    @Test
    void shouldInvalidateBadDataFromFileAgainstSchema() {
        boolean actual = subject.validate("bad-data.json");

        assertFalse(actual);
    }

    @Test
    void shouldValidateGoodDataObjectAgainstSchema() {
        Map<String, Object> input = new HashMap<>();
        input.put("id", 123);
        input.put("name", "Eugene");
        input.put("age", 27);

        boolean actual = subject.validate(input);

        assertTrue(actual);
    }

    @Test
    void shouldValidateBadDataObjectAgainstSchema() {
        Map<String, Object> input = new HashMap<>();
        input.put("id", "123");
        input.put("name", false);

        boolean actual = subject.validate(input);

        assertFalse(actual);
    }
}
