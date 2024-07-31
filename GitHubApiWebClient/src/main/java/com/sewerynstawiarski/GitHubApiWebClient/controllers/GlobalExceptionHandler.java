package com.sewerynstawiarski.GitHubApiWebClient.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable{
@ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity webClientExceptionHandling(WebClientResponseException exception) {
    Map<String, String> response = new HashMap<>();

    response.put("status", exception.getStatusCode().toString());

    var statusCode = exception.getStatusCode().value();

    String text = switch (statusCode) {
        case 404 -> "A user with this name was not found on GitHub";
        case 403 -> "Access forbidden! Probable cause - hourly limit of requests used";
        default -> exception.getMessage();
    };

    response.put("message", text);

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }
}
