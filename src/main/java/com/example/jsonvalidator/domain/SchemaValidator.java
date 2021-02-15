package com.example.jsonvalidator.domain;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class SchemaValidator {

    private String schemaLocation = "schema.json";

    public boolean valid(Map<String, Object> request) {
        Schema schema = getSchema();
        if (schema == null) return false;
        try {
            schema.validate(new JSONObject(request));
        } catch (ValidationException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private Schema getSchema() {
        InputStream schemaFile = getInputStream();
        if (schemaFile == null) return null;
        return SchemaLoader.load(extractJsonObjectFrom(schemaFile));
    }

    private JSONObject extractJsonObjectFrom(InputStream schemaFile) {
        JSONTokener schemaData = new JSONTokener(schemaFile);
        return new JSONObject(schemaData);
    }

    private InputStream getInputStream() {
        try {
            return new ClassPathResource(this.schemaLocation).getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
