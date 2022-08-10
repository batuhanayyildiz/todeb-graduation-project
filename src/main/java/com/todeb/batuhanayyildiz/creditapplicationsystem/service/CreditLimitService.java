package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CreditLimitService {
    CreditLimit getCreditLimitById(Long id);
    void createCreditLimit(CreditLimit creditLimit);

    boolean deleteCreditLimit(Long id);
    CreditLimit updateCreditLimit(CreditLimit creditLimit);
    void addCreditLimitToCustomer(Customer customer);

    CreditLimit getCreditLimitByCustomerIdentityNo(String customerIdentityNo);
    List<CreditLimit> getAllCreditLimits();


}
