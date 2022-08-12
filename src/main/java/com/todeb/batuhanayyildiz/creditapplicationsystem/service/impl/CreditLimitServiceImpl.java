package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
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
    public CreditLimit createCreditLimit(CreditApplication creditApplication) {
        log.info("method is started to use");
        CreditLimit creditLimit =  new CreditLimit(creditApplication,0);
        return creditLimitRepository.save(creditLimit);


    }
    @Override
    public double creditLimitCalculation(String identityNo, CreditApplication creditApplication, CreditScore creditScore) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        int monthlyIncome= customer.getMonthlyIncome();
        int score=creditScore.getScore();
        if (creditApplication.getApplicationStatus() != CreditApplicationStatus.ACCEPTED){
            log.error("Application can not be evaluated");
        }
        else if (score<1000 && monthlyIncome<5000){
            return 10000;
        }
        else if (score<1000 && monthlyIncome>=5000){
            return 20000;
        }

        return monthlyIncome*creditApplication.getCreditMultiplier();





    }
}
