package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditLimitServiceImpl implements CreditLimitService {


    private final CreditLimitRepository creditLimitRepository;



    @Override
    public CreditLimit getCreditLimitById(Long id) {
        return null;
    }

    @Override
    public CreditLimit getCreditLimitByApplicationId(Long id) {
        return null;
    }
    @Override
    public CreditLimit getLastCreditLimitByCreditApplication(CreditApplication creditApplication) {
        log.info("Business logic of getLastCreditLimitByCreditApplication starts");
        List<CreditLimit> creditLimitByCreditApplication = creditLimitRepository.findAll().stream()
                .filter(creditLimit-> creditLimit.getCreditApplication()==creditApplication)
                .sorted(getCreditLimitComparator()).collect(Collectors.toList());
        if (creditLimitByCreditApplication.size()<1){
            log.error("Credit Limit is not found by customer");
            throw new NotFoundException("Credit Limit");
        }
        Optional<CreditLimit> creditLimit= Optional.of(creditLimitByCreditApplication.get(creditLimitByCreditApplication.size()-1));

        return creditLimit.orElseThrow(()->{
            log.error("Credit Limit is not found by customer");
            return new NotFoundException("Credit Limit");});


    }

    @Override
    public CreditLimit createCreditLimit(CreditApplication creditApplication) {
        log.info("method is started to use");
        CreditLimit creditLimit =  new CreditLimit(creditApplication,0);
        return creditLimitRepository.save(creditLimit);


    }
    @Override
    public double creditLimitCalculation(Customer customer, CreditApplication creditApplication, CreditScore creditScore) {
        int monthlyIncome= customer.getMonthlyIncome();
        int score=creditScore.getScore();
        if (creditApplication.getApplicationStatus() != CreditApplicationStatus.ACCEPTED){
            log.error("Application can not be evaluated");
            return 0;
        }
        else if (score<1000 && monthlyIncome<5000){
            return 10000;
        }
        else if (score<1000 && monthlyIncome>=5000){
            return 20000;
        }

        return monthlyIncome*creditApplication.getCreditMultiplier();





    }
    private Comparator<CreditLimit> getCreditLimitComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }
}
