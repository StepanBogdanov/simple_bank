package com.bogstepan.simple_bank.deal.exception;

import com.bogstepan.simple_bank.clients.dto.InvalidRequestDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<InvalidRequestDataDto> handleDataException(SQLException exception) {
        var data = new InvalidRequestDataDto(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InvalidRequestDataDto> handleRequestException(RequestException exception) {
        log.warn(exception.getMessage());
        var data = new InvalidRequestDataDto(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
