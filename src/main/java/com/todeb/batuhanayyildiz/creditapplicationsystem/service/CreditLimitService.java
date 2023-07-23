package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CreditLimitCalculatedException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditLimitService {


    private final CreditLimitRepository creditLimitRepository;



public CreditLimit getCreditLimitByCreditApplication(CreditApplication creditApplication) {
    log.info("Business logic of getLastCreditLimitByCreditApplication starts");
    Optional<CreditLimit> creditLimitByCreditApplication = creditLimitRepository.findByCreditApplication_Id(creditApplication.getId());

    return creditLimitByCreditApplication.orElseThrow(()->{
        log.error("Credit Limit is not found from credit application");
        return new NotFoundException("Credit Limit");});


}


public double creditLimitCalculation(Customer customer, CreditApplication creditApplication, CreditScore creditScore) {
    int monthlyIncome= customer.getMonthlyIncome();
    int score=creditScore.getScore();
    if (creditApplication.getApplicationStatus() != CreditApplicationStatus.WAITING){
        log.error("Limit is already calculated");
        throw new CreditLimitCalculatedException(creditApplication.getId());}
        if (score<1000 && monthlyIncome<5000){
            return 10000;
        }
        else if (score<1000 && monthlyIncome>=5000){
            return 20000;
        }
        else{
            return monthlyIncome*creditApplication.getCreditMultiplier();
        }
    }
    public CreditLimit addCreditLimitToCreditApplicationByCreditApplication(CreditApplication creditApplication) {
        log.info("method is started to use");
        CreditLimit creditLimit =  new CreditLimit(creditApplication,0);
        return creditLimitRepository.save(creditLimit);

    }

}

/*
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
*/
