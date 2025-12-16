package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message) {
        super("Transaction failed: " + message);
    }
}
