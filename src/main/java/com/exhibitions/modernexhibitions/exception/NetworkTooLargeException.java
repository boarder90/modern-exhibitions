package com.exhibitions.modernexhibitions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if Coordinates are invalid
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NetworkTooLargeException extends RuntimeException {

    public NetworkTooLargeException(String message){
        super(message);
    }
}
