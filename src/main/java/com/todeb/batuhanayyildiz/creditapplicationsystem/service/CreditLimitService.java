package com.todeb.batuhanayyildiz.creditapplicationsystem.service;


import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditLimitService {
    private final CreditLimitRepository creditLimitRepository;

protected double calculateCreditLimit(int creditMultiplier,int monthlyIncome,int creditScore)
{

    if (creditScore<1000 && monthlyIncome<5000)
    {
        return 10000;
    }
    else if (creditScore<1000 && monthlyIncome>=5000)
    {

        return 20000;
    }

    else
    {
        return (double)monthlyIncome*(double)creditMultiplier;
    }
    }

}


