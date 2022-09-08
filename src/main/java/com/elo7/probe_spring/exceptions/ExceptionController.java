package com.elo7.probe_spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = InvalidPlateauException.class)
    public ResponseEntity<Object> plateauException (InvalidPlateauException exception) {
        return new ResponseEntity<>(exception.getBody(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidProbeException.class)
    public ResponseEntity<Object> probeException (InvalidProbeException exception) {
        return new ResponseEntity<>(exception.getBody(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidCommandException.class)
    public ResponseEntity<Object> commandException (InvalidCommandException exception) {
        return new ResponseEntity<>(exception.getBody(), HttpStatus.BAD_REQUEST);
    }
}
