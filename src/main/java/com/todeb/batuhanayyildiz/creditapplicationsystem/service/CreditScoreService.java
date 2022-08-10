package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;

import java.util.List;

public interface CreditScoreService {

    CreditScore getCreditScoreById(Long id);
    CreditScore getCreditScoreByCustomerIdentityNo(String identityNo);
    List<CreditScore> getAllCreditScores();
    void createCreditScore(CreditScore creditScore);

    boolean deleteCreditScore(Long id);
    boolean deleteCreditScoreByCustomerIdentityNo(String identityNo);

    CreditScore updateCreditScoreByCustomerIdentityNo(String identityNo,CreditScore creditScore);

    int creditScoreCalculation();

}
