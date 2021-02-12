package com.example.jsonvalidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleValidatorTest {

    private ExampleValidator subject;

    @BeforeEach
    void setUp() {
        subject = new ExampleValidator("schema.json");
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
    void shouldValidateGoodDataAgainstSchema() {
        boolean actual = subject.validate("data.json");

        assertTrue(actual);
    }

    @Test
    void shouldInvalidateBadDataAgainstSchema() {
        boolean actual = subject.validate("bad-data.json");

        assertFalse(actual);
    }
}
