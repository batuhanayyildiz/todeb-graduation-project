package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {

    @Override
    public CreditApplication getCreditApplicationById(Long id) {
        return null;
    }

    @Override
    public CreditApplication getCreditApplicationByCustomerIdentityNo(String identityNo) {
        return null;
    }

    @Override
    public List<CreditApplication> getAllCreditApplications() {
        return null;
    }

    @Override
    public CreditApplication createCreditApplication() {
        return null;
    }

    @Override
    public boolean deleteCreditApplication(Long id) {
        return false;
    }

    @Override
    public boolean deleteCreditApplicationByCustomerIdentityNo(String identityNo) {
        return false;
    }

    @Override
    public CreditApplication updateCreditApplicationByCustomerIdentityNo(String identityNo, CreditApplication creditApplication) {
        return null;
    }
}
