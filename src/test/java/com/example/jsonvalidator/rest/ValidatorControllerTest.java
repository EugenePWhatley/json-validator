package com.example.jsonvalidator.rest;

import com.example.jsonvalidator.domain.SchemaValidator;
import com.example.jsonvalidator.domain.TransformationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ValidatorController.class)
class ValidatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    SchemaValidator schemaValidator;

    @MockBean
    TransformationService transformationService;

    @Test
    void shouldValidateProperInput() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("id", 123);
        input.put("name", "Eugene");
        input.put("age", 27);

        Map<String, Object> output = new HashMap<>();
        output.put("doubleAge", 54);
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> result = Collections.singletonList(objectMapper.valueToTree(output));

        when(schemaValidator.valid(input))
                .thenReturn(true);
        when(transformationService.transform(objectMapper.valueToTree(input)))
                .thenReturn(result);

        MockHttpServletRequestBuilder request = post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].doubleAge").value(54));
    }
}