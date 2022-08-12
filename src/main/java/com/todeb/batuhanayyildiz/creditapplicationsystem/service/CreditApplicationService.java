package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CreditApplicationService {
    CreditApplication getCreditApplicationById(Long id);

    CreditApplication getLastCreditApplicationByCustomerIdentityNo(String identityNo);


    CreditApplication createCreditApplication(Customer customer);
    CreditApplication changeStatus(CreditApplication creditApplication);
    List<CreditApplication> getAllCreditApplications();
    boolean deleteCreditApplication(Long id);
    boolean deleteCreditApplicationByCustomerIdentityNo(String identityNo);
    CreditApplication updateCreditApplicationByCustomerIdentityNo(String identityNo,CreditApplication creditApplication);
}
