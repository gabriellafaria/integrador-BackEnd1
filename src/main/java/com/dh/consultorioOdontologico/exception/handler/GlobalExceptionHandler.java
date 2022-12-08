package com.dh.consultorioOdontologico.exception.handler;
import com.dh.consultorioOdontologico.exception.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({exceptions.class})
    public ResponseEntity<String> errorResourceNotFound(exceptions exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RegisterNotFoundException.class})
    public ResponseEntity<String> processRegisterNotFound(RegisterNotFoundException exception){
        return new  ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}