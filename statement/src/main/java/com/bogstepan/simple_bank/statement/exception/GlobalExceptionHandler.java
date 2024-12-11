package com.bogstepan.simple_bank.statement.exception;

import com.bogstepan.simple_bank.clients.dto.InvalidRequestDataDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<InvalidRequestDataDto> handleException(RequestException exception) {
        var data = new InvalidRequestDataDto(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
