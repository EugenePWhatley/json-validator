package com.example.jsonvalidator.rest.filter;

import com.example.jsonvalidator.domain.SchemaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class SchemaFilter implements Filter {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    SchemaValidator schemaValidator;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
        HttpServletResponse resp = (HttpServletResponse) response;

        Map<String, Object> requestBody = mapper.readValue(cachedBodyHttpServletRequest.getReader(), Map.class);

        if (((HttpServletRequest) request).getMethod().equals("POST") && !schemaValidator.valid(requestBody)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input. Does not match schema");
            return;
        }

        chain.doFilter(cachedBodyHttpServletRequest, resp);
    }
}
