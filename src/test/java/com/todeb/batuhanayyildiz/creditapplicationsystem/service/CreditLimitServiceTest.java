package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CreditLimitServiceTest {
    private CreditLimitService creditLimitService;
    private CreditLimitRepository creditLimitRepository;
    @BeforeEach
    void setUp()
    {
        creditLimitRepository= Mockito.mock(CreditLimitRepository.class);
        creditLimitService= new CreditLimitService(creditLimitRepository);
    }

    @Test
    void testCalculateCreditLimit_ifMonthlyIncomeLessThan5000AndCreditScoreLessThan1000()
    {
        int creditMultiplier=4, monthlyIncome=499,creditScore=999;

        double result = creditLimitService.calculateCreditLimit(creditMultiplier,monthlyIncome,creditScore);

        assertEquals(result, 10000);
    }
    @Test
    void testCalculateCreditLimit_ifMonthlyIncomeGreaterThanOrEqualsTo5000AndCreditScoreLessThan1000()
    {
        int creditMultiplier=4, monthlyIncome1=5000,monthlyIncome2=5001,creditScore=999;

        double result1 = creditLimitService.calculateCreditLimit(creditMultiplier,monthlyIncome1,creditScore);
        double result2 = creditLimitService.calculateCreditLimit(creditMultiplier,monthlyIncome2,creditScore);

        assertEquals(result1, 20000);
        assertEquals(result2, 20000);
    }

    @Test
    void testCalculateCreditLimit_ifCreditScoreGreaterThanOrEqualsTo1000()
    {
        int creditMultiplier=4, monthlyIncome=5000,creditScore1=1000,creditScore2=2000;

        double result1 = creditLimitService.calculateCreditLimit(creditMultiplier,monthlyIncome,creditScore1);
        double result2 = creditLimitService.calculateCreditLimit(creditMultiplier,monthlyIncome,creditScore2);

        assertEquals(result1, (double)creditMultiplier*(double)monthlyIncome);
        assertEquals(result2, (double)creditMultiplier*(double)monthlyIncome);
    }


}

