package com.landroute.controller.advice;

import com.landroute.dto.ErrorResponce;
import com.landroute.exception.NoRouteException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class, NoRouteException.class})
    public ErrorResponce handleException(Exception e) {
        return ErrorResponce.builder()
                .error(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
