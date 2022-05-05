package com.pay.organism.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NoEntityAddedException extends RuntimeException{

    public NoEntityAddedException(String s){
        super(s);
    }
}