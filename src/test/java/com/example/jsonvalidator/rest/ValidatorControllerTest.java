package com.example.jsonvalidator.rest;

import com.example.jsonvalidator.domain.ValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ValidatorController.class)
class ValidatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ValidatorService validatorService;

    private Map<String, Object> input;

    @BeforeEach
    void setUp() {
        input = new HashMap<>();
    }

    @Test
    void shouldValidateProperInput() throws Exception {
        given(validatorService.validate(input)).willReturn(true);

        MockHttpServletRequestBuilder request = post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(input));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("valid").value(true));
    }

    @Test
    void shouldAcknowledgeSchemaExists() throws Exception {
        given(validatorService.doesSchemaExist()).willReturn(true);

        MockHttpServletRequestBuilder request = get("/exist");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("exist").value(true));
    }
}