package com.jpach.patitas.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
// Nos devuelve un objeto en json o texto plano
@RestController
public class GlobalExceptions {

    // @ExceptionHandler(IllegalArgumentException.class)

}
