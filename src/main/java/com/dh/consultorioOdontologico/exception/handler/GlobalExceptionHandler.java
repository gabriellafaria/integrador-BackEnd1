package com.dh.consultorioOdontologico.exception.handler;
import com.dh.consultorioOdontologico.exception.Exceptions;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exceptions.class})
    public ResponseEntity<String> errorResourceNotFound(Exceptions exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> processResourceNotFound(ResourceNotFoundException exception){
        return new  ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
