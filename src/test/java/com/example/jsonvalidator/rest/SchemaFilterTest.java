package com.example.jsonvalidator.rest;

import com.example.jsonvalidator.domain.SchemaValidator;
import com.example.jsonvalidator.rest.filter.CachedBodyHttpServletRequest;
import com.example.jsonvalidator.rest.filter.SchemaFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchemaFilterTest {

    @Mock
    FilterChain filterChain;

    @Mock
    ObjectMapper mapper;

    @Mock
    SchemaValidator schemaValidator;

    @InjectMocks
    SchemaFilter schemaFilter;

    ServletRequest request;

    ServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = Mockito.spy(new MockHttpServletResponse());
    }

    @Test
    void shouldContinueToFilterChainWhenRequestIsAGet() throws IOException, ServletException {
        Map<String, Object> requestBody = Map.of();
        request = new MockHttpServletRequest("GET", "");
        when(mapper.readValue(any(BufferedReader.class), eq(Map.class))).thenReturn(requestBody);

        schemaFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(any(CachedBodyHttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    void shouldContinueToFilterChainWhenValidRequest() throws IOException, ServletException {
        Map<String, Object> requestBody = Map.of();
        request = new MockHttpServletRequest("POST", "");
        when(mapper.readValue(any(BufferedReader.class), eq(Map.class))).thenReturn(requestBody);
        when(schemaValidator.valid(requestBody)).thenReturn(true);

        schemaFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(any(CachedBodyHttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    void shouldSendErrorWhenInvalid() throws IOException, ServletException {
        Map<String, Object> requestBody = Map.of();
        request = new MockHttpServletRequest("POST", "");
        when(mapper.readValue(any(BufferedReader.class), eq(Map.class))).thenReturn(requestBody);
        when(schemaValidator.valid(requestBody)).thenReturn(false);

        schemaFilter.doFilter(request, response, filterChain);

        verify(filterChain, never()).doFilter(any(CachedBodyHttpServletRequest.class), any(HttpServletResponse.class));
        verify((HttpServletResponse) response).sendError(eq(HttpServletResponse.SC_BAD_REQUEST), contains("Invalid input"));
    }
}