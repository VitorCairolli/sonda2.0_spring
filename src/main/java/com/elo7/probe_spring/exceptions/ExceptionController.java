package com.elo7.probe_spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = InvalidPlateauException.class)
    public ResponseEntity<Object> plateauException (InvalidPlateauException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidProbeException.class)
    public ResponseEntity<Object> probeException (InvalidProbeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProbeCollisionException.class)
    public ResponseEntity<Object> probeCollisionException(ProbeCollisionException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProbeOutOfPlateauException.class)
    public ResponseEntity<Object> probeOutOfPlateauException(ProbeOutOfPlateauException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
