package com.example.jsonvalidator.rest.filter;

import com.example.jsonvalidator.domain.SchemaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class SchemaFilter implements Filter {

    @Autowired
    private SchemaValidator schemaValidator;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Boolean valid = valid(cachedBodyHttpServletRequest);

        if (valid) {
            chain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
        } else {
            sendError(httpServletResponse);
        }
    }

    private Boolean valid(
            CachedBodyHttpServletRequest request
    ) {
        Map<HttpMethod, Supplier<Boolean>> commandMap = Map.of(
                HttpMethod.POST,
                requestBodyValidation(request)
        );

        return commandMap.getOrDefault(
                HttpMethod.valueOf(request.getMethod()),
                byPassValidation()
        ).get();
    }

    private void sendError(HttpServletResponse resp) {
        try {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input. Does not match schema");
        } catch (IOException ignored) {
        }
    }

    private Supplier<Boolean> requestBodyValidation(CachedBodyHttpServletRequest cachedBodyHttpServletRequest) {
        return () -> schemaValidator.valid(getRequestBody(cachedBodyHttpServletRequest));
    }

    private Supplier<Boolean> byPassValidation() {
        return () -> true;
    }

    private Map<String, Object> getRequestBody(CachedBodyHttpServletRequest cachedBodyHttpServletRequest) {
        try {
            return mapper.readValue(cachedBodyHttpServletRequest.getReader(), Map.class);
        } catch (IOException e) {
            return null;
        }
    }
}
