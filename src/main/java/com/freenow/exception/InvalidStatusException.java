package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid driver online status.")
public class InvalidStatusException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public InvalidStatusException(String message)
    {
        super(message);
    }

}
