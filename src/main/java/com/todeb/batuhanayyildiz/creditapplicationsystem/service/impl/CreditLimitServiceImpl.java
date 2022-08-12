package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditLimitServiceImpl implements CreditLimitService {


    private final CreditLimitRepository creditLimitRepository;

    private final CreditApplicationServiceImpl creditApplicationService;
    private final CreditScoreServiceImpl creditScoreService;
    private final CustomerServiceImpl customerService;


    @Override
    public CreditLimit getCreditLimitById(Long id) {
        return null;
    }

    @Override
    public CreditLimit getCreditLimitByApplicationId(Long id) {
        return null;
    }

    @Override
    public double creditLimitCalculation(String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        CreditApplication creditApplication= creditApplicationService.getLastCreditApplicationByCustomer(customer);
        int monthlyIncome= customer.getMonthlyIncome();
        int creditScore=creditScoreService.getLastCreditScoreByCustomer(customer).getScore();
        if (creditApplication.getApplicationStatus() != CreditApplicationStatus.ACCEPTED){
            log.error("Application can not be evaluated");
        }
        else if (creditScore<1000 && monthlyIncome<5000){
            return 10000;
        }
        else if (creditScore<1000 && monthlyIncome>=5000){
            return 20000;
        }
        else if (creditScore>=1000){
            return monthlyIncome*creditApplication.getCreditMultiplier();
        }


        return 0;





    }
}
