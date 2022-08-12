package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CreditLimitService {

    CreditLimit getCreditLimitById(Long id);
    CreditLimit getCreditLimitByApplicationId(Long id);
    double creditLimitCalculation(String identityNo);






}
