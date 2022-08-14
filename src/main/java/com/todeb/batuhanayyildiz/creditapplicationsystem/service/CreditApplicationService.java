package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CreditApplicationService {
    List<CreditApplication>getAllCreditApplications();
    List<CreditApplication>getAllCreditApplicationsOfCustomerByCustomer(Customer customer);
    CreditApplication getCreditApplicationById(Long id);
    CreditApplication getLastCreditApplicationByCustomer(Customer customer);

    CreditApplication createCreditApplication(Customer customer);

    CreditApplication determineLastCreditApplicationStatusByCustomer(Customer customer);
    CreditApplication addCreditLimitToCreditApplicationByCustomer(Customer customer);
    String viewCreditApplicationResultByCustomer( Customer customer);
    }
