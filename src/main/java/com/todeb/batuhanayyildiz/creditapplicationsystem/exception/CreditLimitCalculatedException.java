package com.todeb.batuhanayyildiz.creditapplicationsystem.exception;

public class CreditLimitCalculatedException extends RuntimeException{
    public CreditLimitCalculatedException(String creditApplicationId) {
        super("Credit limit is already calculated : " + creditApplicationId);
    }
}
