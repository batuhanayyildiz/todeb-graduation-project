package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CreditLimitService {
    CreditLimit getCreditLimitById(Long id);
    CreditLimit getCreditLimitByCustomerIdentityNo(String identityNo);
    List<CreditLimit> getAllCreditLimits();
    void createCreditLimit(CreditLimit creditLimit);

    boolean deleteCreditLimit(Long id);
    boolean deleteCreditLimitByCustomerIdentityNo(String identityNo);

    CreditLimit updateCreditLimitByCustomerIdentityNo(String identityNo,CreditLimit creditLimit);
    void addCreditLimitToCustomerByIdentityNo(String identityNo);




}
