package com.sewerynstawiarski.GitHubApiWebClient.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable{
@org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity NotFoundException (WebClientResponseException exception) {
    Map<String, String> response = new LinkedHashMap<>();
    response.put("status" , exception.getStatusCode().toString());
    var statusCode = exception.getStatusCode().value();
    String text = switch (statusCode) {
        case 404 -> "User of this name was not found on GitHub";
        case 403 -> "Access forbidden! Probable cause - hourly limit of requests used";
        default -> exception.getMessage();
    };

    response.put("message", text);

        return ResponseEntity.status(exception.getStatusCode()).body(response);
}
}
