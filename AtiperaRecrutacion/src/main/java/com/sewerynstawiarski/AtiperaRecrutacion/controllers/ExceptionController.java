package com.sewerynstawiarski.AtiperaRecrutacion.controllers;

import com.sewerynstawiarski.AtiperaRecrutacion.exceptionHandling.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
@ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity NotFoundException (WebClientResponseException exception) {
    Map<String, String> response = new LinkedHashMap<>();
    response.put("status",exception.getStatusCode().toString());
    response.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}
}
