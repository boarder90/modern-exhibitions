package com.exhibitions.modernexhibitions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCoordinatesException extends RuntimeException {

    public InvalidCoordinatesException(String message){
        super(message);
    }
}
