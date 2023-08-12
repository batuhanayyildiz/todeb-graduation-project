package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.TestSupport;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

class CreditApplicationServiceTest extends TestSupport {

    private  CreditApplicationRepository creditApplicationRepository;

    private  CreditLimitService creditLimitService;

    private  CustomerService customerService;

    private  SmsService smsService;

    private  CreditScoreService creditScoreService;

    private  CreditApplicationMapper CREDIT_APPLICATION_MAPPER;

    CreditApplicationService creditApplicationService;
    @BeforeEach
    public void setUp()
    {
        creditApplicationRepository=mock(CreditApplicationRepository.class);
        creditLimitService =mock(CreditLimitService.class);
        customerService=mock(CustomerService.class);
        smsService=mock(SmsService.class);
        creditScoreService =mock(CreditScoreService.class);
        CREDIT_APPLICATION_MAPPER=mock(CreditApplicationMapper.class);

        creditApplicationService=new CreditApplicationService(creditApplicationRepository
                ,creditLimitService,customerService,smsService,creditScoreService);
    }

    @Test
    void testCreateApplicationByCustomerIdentityNo_whenCustomerExists()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();
        CreditApplication creditApplication=generateCreditApplication(customer);
        CreditApplicationDTO expectedCreditApplicationDTO=generateCreditApplicationDTO(creditApplication);

        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(CREDIT_APPLICATION_MAPPER.toDto(creditApplication)).thenReturn(expectedCreditApplicationDTO);

        CreditApplicationDTO result=creditApplicationService.createApplicationByCustomerIdentityNo(identityNo);


        assertEquals(result,expectedCreditApplicationDTO);

        verify(customerService).findCustomerByIdentityNo(identityNo);
        verify(creditApplicationRepository).save(any());



    }

    @Test
    void findLastCreditApplicationByCustomerIdentityNo()
    {
    }

    @Test
    void determineApplicationResultByCustomerIdentityNo()
    {
    }

    @Test
    void getLastCreditApplicationByIdentityNo()
    {
    }

    @Test
    void viewLastCreditApplicationResultByIdentityNo()
    {
    }
}