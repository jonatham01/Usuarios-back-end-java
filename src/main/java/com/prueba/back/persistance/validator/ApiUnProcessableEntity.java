package com.prueba.back.persistance.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//EXCEPCION PERSONALIZADA DE STATUS 422
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ApiUnProcessableEntity extends Exception {
    public ApiUnProcessableEntity(String message) {
        super(message);
    }
}
