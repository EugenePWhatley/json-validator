package com.example.jsonvalidator.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TransformationServiceTest {

    TransformationService transformationService;

    @BeforeEach
    void setUp() {
        transformationService = new TransformationService();
    }

    @Test
    void shouldTransformInput() throws JsonQueryException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> input = new HashMap<>();
        input.put("id", 123);
        input.put("name", "Eugene");
        input.put("age", 27);
        JsonNode jsonNode = mapper.valueToTree(input);

        List<JsonNode> actual = transformationService.transform(jsonNode);

        Map<String, Object> output = new HashMap<>();
        output.put("doubleAge", 54);
        JsonNode json = mapper.valueToTree(output);

        assertIterableEquals(Collections.singletonList(json), actual);
    }
}