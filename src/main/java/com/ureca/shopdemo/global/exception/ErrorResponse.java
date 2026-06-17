package com.ureca.shopdemo.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private int statusCode;
    private String message;

    // MethodArgumentNotValidException 처리할 때 다른 정보도 넘기기 위한 map
    private Map<String, String> validation = new HashMap<>();

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void addValidation(String field, String message) {
        this.validation.put(field, message);
    }
}
