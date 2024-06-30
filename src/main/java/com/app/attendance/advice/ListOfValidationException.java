package com.app.attendance.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ListOfValidationException extends RuntimeException{
    private final List<String> errorMessages;

    public ListOfValidationException(List<String> errorMessages){
        this.errorMessages=errorMessages;
    }

}

