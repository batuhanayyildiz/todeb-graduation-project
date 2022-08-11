package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CreditScoreService {

    CreditScore getCreditScoreById(Long id);
    CreditScore getCreditScoreByCustomerIdentityNo(String identityNo);
    List<CreditScore> getAllCreditScores();
    CreditScore createCreditScore(Customer customer);

    boolean deleteCreditScore(Long id);
    boolean deleteCreditScoreByCustomerIdentityNo(String identityNo);

    CreditScore updateCreditScoreByCustomerIdentityNo(String identityNo,CreditScore creditScore);

    int creditScoreCalculation();

}
