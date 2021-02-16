package com.example.jsonvalidator.config;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SchemaConfiguration {

    @Value("${schema-location}")
    private String schemaLocation;

    @Bean
    public Schema getSchema() {
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
