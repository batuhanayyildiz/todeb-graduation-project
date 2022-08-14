package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceImplTest {

    @Mock
    private  CreditApplicationRepository creditApplicationRepository;
    @Mock
    private  CreditScoreServiceImpl creditScoreService;
    @Mock
    private  CreditLimitServiceImpl creditLimitService;

    @InjectMocks
    private CreditApplicationServiceImpl creditApplicationService;


    @Test
    void getAllCreditApplications(){

    }
    @Test
    void getAllCreditApplicationsOfCustomerByCustomer() {
    }

    @Test
    void getCreditApplicationById() {
    }



    @Test
    void getLastCreditApplicationByCustomer() {
    }

    @Test
    void createCreditApplication() {
    }

    @Test
    void determineLastCreditApplicationStatusByCustomer() {
    }

    @Test
    void addCreditLimitToCreditApplicationByCustomer() {
    }

    @Test
    void updateCreditLimitOfCreditApplicationByCustomer() {
    }

    @Test
    void viewCreditApplicationResultByCustomer() {
    }
}