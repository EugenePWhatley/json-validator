package com.example.jsonvalidator.domain;

import com.fasterxml.jackson.databind.JsonNode;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransformationService {

    public static final String JQ_TRANSFORMATION = "{doubleAge: (.age * 2)}";

    public List<JsonNode> transform(JsonNode input) throws JsonQueryException {
        Scope rootScope = Scope.newEmptyScope();
        Scope childScope = Scope.newChildScope(rootScope);
        JsonQuery q = JsonQuery.compile(JQ_TRANSFORMATION);
        return q.apply(childScope, input);
    }
}
