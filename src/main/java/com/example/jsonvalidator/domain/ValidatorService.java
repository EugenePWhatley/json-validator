package com.example.jsonvalidator.domain;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ValidatorService {

    private final Schema schema;

    public ValidatorService(String schemoLocation) {
        this.schema = setSchema(schemoLocation);
    }

    public boolean doesSchemaExist() {
        return schema != null;
    }

    public boolean validate(String dataLocation) {
        FileInputStream dataFile = getFileStream(dataLocation);
        if (dataFile == null) return false;
        return valid(extractJsonObjectFrom(dataFile));
    }

    public boolean validate(Map<String, Object> request) {
        return valid(new JSONObject(request));
    }

    private Schema setSchema(String schemoLocation) {
        FileInputStream schemaFile = getFileStream(schemoLocation);
        if (schemaFile == null) return null;
        return SchemaLoader.load(extractJsonObjectFrom(schemaFile));
    }

    private JSONObject extractJsonObjectFrom(FileInputStream schemaFile) {
        JSONTokener schemaData = new JSONTokener(schemaFile);
        return new JSONObject(schemaData);
    }

    private FileInputStream getFileStream(String location) {
        try {
            return new FileInputStream(new ClassPathResource(location).getFile());
        } catch (IOException e) {
            return null;
        }
    }

    private boolean valid(JSONObject jsonObject) {
        try {
            schema.validate(jsonObject);
        } catch (ValidationException e) {
            return false;
        }
        return true;
    }
}
