package com.example.railwayticket.exception;

public class RuleViolationException extends ApplicationException {

    public RuleViolationException(String code, String message) {
        super(code, message);
    }
}
