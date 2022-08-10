package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;

import java.util.List;

public interface CreditApplicationService {
    CreditApplication getCreditApplicationById(Long id);
    CreditApplication getCreditApplicationByCustomerIdentityNo(String identityNo);
    List<CreditApplication> getAllCreditApplications();
    CreditApplication createCreditApplication();

    boolean deleteCreditApplication(Long id);
    boolean deleteCreditApplicationByCustomerIdentityNo(String identityNo);

    CreditApplication updateCreditApplicationByCustomerIdentityNo(String identityNo,CreditApplication creditApplication);
}
