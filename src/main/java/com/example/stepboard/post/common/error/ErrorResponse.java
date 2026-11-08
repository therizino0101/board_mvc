package com.example.stepboard.post.common.error;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse (
    int status,
    String code,
    String message,
    Map<String, String> fieldErrors
) {
    
}
