package com.codemasters.accommodateme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class IssueNotFoundException extends RuntimeException{
    public IssueNotFoundException(String message){
        super(message);
    }
}
