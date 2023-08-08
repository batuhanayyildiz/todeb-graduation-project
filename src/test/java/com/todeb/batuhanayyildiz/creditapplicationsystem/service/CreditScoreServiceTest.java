package com.todeb.batuhanayyildiz.creditapplicationsystem.service;



import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditScoreRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreditScoreServiceTest {
    private CreditScoreRepository creditScoreRepository;
    private CreditScoreService creditScoreService;


    @BeforeEach
    public void  setup()
    {
        creditScoreRepository= Mockito.mock(CreditScoreRepository.class);

        creditScoreService=new CreditScoreService(creditScoreRepository);

    }



    @Test
    void testFindLastCreditScoreByCustomerIdentityNo_ifCreditScoreExists_shouldReturnCreditScore()
    {
        Customer customer=new Customer("customer-id","identityNo","customer-name", "customer-surname"
                ,0,"123",Set.of(),Set.of());
        CreditScore creditScore1=new CreditScore("1", LocalDateTime.now(),2,customer);
        CreditScore creditScore2=new CreditScore("2", LocalDateTime.now(),5,customer);

        List<CreditScore> creditScores= new ArrayList<>(
                List.of(creditScore1,creditScore2));
        Mockito.when(creditScoreRepository.findAll()).thenReturn(creditScores);

        CreditScore result= creditScoreService.findLastCreditScoreByCustomerIdentityNo(customer.getIdentityNo());

        assertEquals(result,creditScore2);
    }
    @Test
    void testFindLastCreditScoreByCustomerIdentityNo_ifCreditScoreDoesNotExist_shouldThrowNotFoundException()
    {
        Mockito.when(creditScoreRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class,
                () -> creditScoreService.findLastCreditScoreByCustomerIdentityNo("identity_no"));
    }



}