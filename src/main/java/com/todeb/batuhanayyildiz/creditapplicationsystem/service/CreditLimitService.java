package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CreditLimitService {

    CreditLimit getCreditLimitById(Long id);
    CreditLimit getCreditLimitByApplicationId(Long id);
    CreditLimit getLastCreditLimitByCreditApplication(CreditApplication creditApplication);
    CreditLimit createCreditLimit(CreditApplication creditApplication);
    double creditLimitCalculation(Customer customer, CreditApplication creditApplication, CreditScore creditScore);






}
