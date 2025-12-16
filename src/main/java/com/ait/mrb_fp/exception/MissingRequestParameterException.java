package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequestParameterException extends RuntimeException {
    public MissingRequestParameterException(String paramName) {
        super("Missing required request parameter: " + paramName);
    }
}
