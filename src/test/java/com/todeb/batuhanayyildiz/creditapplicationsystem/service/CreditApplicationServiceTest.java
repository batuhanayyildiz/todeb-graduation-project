package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.TestSupport;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CreditLimitCalculatedException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationResult;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    void testFindLastCreditApplicationByCustomerIdentityNo_whenCreditApplicationExists()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();
        CreditApplication creditApplication1=generateCreditApplication(customer);
        CreditApplication creditApplication2=new CreditApplication("creditApplicationId2",getDifferentLocalDateTime()
                , CreditApplicationResult.WAITING,null,4,customer);


        List<CreditApplication> creditApplications= new ArrayList<>(
                List.of(creditApplication1,creditApplication2));
        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);

        CreditApplication result= creditApplicationService.findLastCreditApplicationByCustomerIdentityNo(customer.getIdentityNo());

        assertEquals(result,creditApplication2);
    }
    @Test
    void testFindLastCreditApplicationByCustomerIdentityNo_whenCreditApplicationDoesNotExist_shouldThrowNotFoundException()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();


        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class
                ,()-> creditApplicationService.findLastCreditApplicationByCustomerIdentityNo(identityNo));



    }

    @Test
    void testDetermineApplicationResultByCustomerIdentityNo_creditApplicationResultEqualsToNotWaiting_shouldThrowCreditLimitCalculatedException()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        CreditApplication creditApplication1=generateCreditApplication(customer);
        CreditApplication creditApplication2=new CreditApplication("creditApplicationId2",getDifferentLocalDateTime()
                , CreditApplicationResult.ACCEPTED,null,4,customer);


        List<CreditApplication> creditApplications= new ArrayList<>(
                List.of(creditApplication1,creditApplication2));

        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(creditApplications);


        assertThrows(CreditLimitCalculatedException.class
                ,()-> creditApplicationService.determineApplicationResultByCustomerIdentityNo(identityNo));

        verifyNoInteractions(creditScoreService);
        verifyNoInteractions(creditLimitService);
        verifyNoInteractions(smsService);

    }
    @Test
    void testDetermineApplicationResultByCustomerIdentityNo_creditApplicationResultEqualsToWaitingAndCreditScoreLessThan500()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        CreditApplication creditApplication=new CreditApplication("creditApplicationId",getDifferentLocalDateTime()
                , CreditApplicationResult.WAITING,null,4,customer);

        CreditScore creditScore=new CreditScore("1", LocalDateTime.now(),5,customer);

        CreditApplication expectedCreditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.DENIED,null,4,customer);




        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(creditScoreService.findLastCreditScoreByCustomerIdentityNo(identityNo)).thenReturn(creditScore);
        when(smsService.sendNotificationByPhoneNumber(customer.getPhoneNo()))
                .thenReturn("Sms was sent to related customer's phone number: " + customer.getPhoneNo());
        when(creditApplicationRepository.save(creditApplication)).thenReturn(expectedCreditApplication);

        CreditApplication result=creditApplicationService.determineApplicationResultByCustomerIdentityNo(identityNo);

        assertEquals(expectedCreditApplication,result);
        verifyNoInteractions(creditLimitService);



    }
    @Test
    void testDetermineApplicationResultByCustomerIdentityNo_creditApplicationResultEqualsToWaitingAndCreditScoreGreaterThan500()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();
        String phoneNo=customer.getPhoneNo();

        CreditApplication creditApplication=new CreditApplication("creditApplicationId",getDifferentLocalDateTime()
                , CreditApplicationResult.WAITING,null,4,customer);

        CreditScore creditScore=new CreditScore("1", LocalDateTime.now(),1000,customer);

        CreditLimit creditLimit=new CreditLimit();
        creditLimit.setCreditLimit((double)10000);

        CreditApplication expectedCreditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.ACCEPTED,creditLimit,4,customer);

        creditLimit.setCreditApplication(creditApplication);




        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(creditScoreService.findLastCreditScoreByCustomerIdentityNo(identityNo)).thenReturn(creditScore);
        when(smsService.sendNotificationByPhoneNumber(phoneNo))
                .thenReturn("Sms was sent to related customer's phone number: " + phoneNo);
        when(creditApplicationRepository.save(creditApplication)).thenReturn(expectedCreditApplication);
        when(creditLimitService.calculateCreditLimit(creditApplication.getCreditMultiplier()
                ,customer.getMonthlyIncome(),creditScore.getScore())).thenReturn((double)10000);

        CreditApplication result=creditApplicationService.determineApplicationResultByCustomerIdentityNo(identityNo);

        assertEquals(expectedCreditApplication,result);
        verify(smsService).sendNotificationByPhoneNumber(phoneNo);




    }

    @Test
    void testGetLastCreditApplicationByIdentityNo_whenCreditApplicationExistsAndCreditApplicationResultEqualsToWaiting()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();
        String phoneNo=customer.getPhoneNo();

        CreditApplication creditApplication=new CreditApplication("creditApplicationId",getDifferentLocalDateTime()
                , CreditApplicationResult.WAITING,null,4,customer);

        CreditScore creditScore=new CreditScore("1", LocalDateTime.now(),1000,customer);

        CreditLimit creditLimit=new CreditLimit();
        creditLimit.setCreditLimit((double)10000);

        CreditApplication expectedCreditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.ACCEPTED,creditLimit,4,customer);

        creditLimit.setCreditApplication(creditApplication);

        CreditApplicationDTO expectedCreditApplicationDto=generateCreditApplicationDTO(expectedCreditApplication);


        when(creditScoreService.findLastCreditScoreByCustomerIdentityNo(identityNo)).thenReturn(creditScore);
        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(creditScoreService.findLastCreditScoreByCustomerIdentityNo(identityNo)).thenReturn(creditScore);
        when(smsService.sendNotificationByPhoneNumber(phoneNo))
                .thenReturn("Sms was sent to related customer's phone number: " + phoneNo);
        when(creditLimitService.calculateCreditLimit(creditApplication.getCreditMultiplier()
                ,customer.getMonthlyIncome(),creditScore.getScore())).thenReturn((double)10000);
        when(creditApplicationRepository.save(creditApplication)).thenReturn(expectedCreditApplication);
        when(CREDIT_APPLICATION_MAPPER.toDto(expectedCreditApplication)).thenReturn(expectedCreditApplicationDto);

        CreditApplicationDTO result=creditApplicationService.getLastCreditApplicationByIdentityNo(identityNo);

        assertEquals(expectedCreditApplicationDto,result);
        verify(smsService).sendNotificationByPhoneNumber(phoneNo);

    }
    @Test
    void testGetLastCreditApplicationByIdentityNo_whenCreditApplicationExistsAndCreditApplicationResultEqualsToNotWaiting()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        CreditApplication creditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.DENIED,null,4,customer);

        CreditApplicationDTO expectedCreditApplicationDto=generateCreditApplicationDTO(creditApplication);

        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(CREDIT_APPLICATION_MAPPER.toDto(creditApplication)).thenReturn(expectedCreditApplicationDto);

        CreditApplicationDTO result=creditApplicationService.getLastCreditApplicationByIdentityNo(identityNo);

        assertEquals(expectedCreditApplicationDto,result);








    }

    @Test
    void testGetLastCreditApplicationByIdentityNo_whenCreditApplicationDoesNotExist_shouldThrowNotFoundException()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(Collections.emptyList());


        assertThrows(NotFoundException.class
                ,()->creditApplicationService.getLastCreditApplicationByIdentityNo(identityNo));

        verifyNoInteractions(CREDIT_APPLICATION_MAPPER);
    }

    @Test
    void testViewLastCreditApplicationResultByIdentityNo_IfCreditApplicationResultEqualsToAccepted()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        CreditScore creditScore=new CreditScore("1", LocalDateTime.now(),1000,customer);

        CreditLimit creditLimit=new CreditLimit();
        creditLimit.setCreditLimit((double)10000);

        CreditApplication creditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.ACCEPTED,creditLimit,4,customer);

        CreditApplicationDTO creditApplicationDTO=generateCreditApplicationDTO(creditApplication);


        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(CREDIT_APPLICATION_MAPPER.toDto(creditApplication)).thenReturn(creditApplicationDTO);

        String expected="Identity number: " + customer.getIdentityNo() + "<br/>"
                + "Name: " + customer.getName() + "<br/>"
                + "Surname: " + customer.getSurname() + "<br/>"
                + "Credit Result: " + creditApplicationDTO.getApplicationResult().toString() + "<br/>"
                + "Credit Limit: " + creditApplicationDTO.getCreditLimit().getCreditLimit();

        String result=creditApplicationService.viewLastCreditApplicationResultByIdentityNo(identityNo);

        assertEquals(expected,result);
    }

    @Test
    void testViewLastCreditApplicationResultByIdentityNo_IfCreditApplicationResultEqualsToNotAccepted()
    {
        Customer customer=generateCustomer();
        String identityNo=customer.getIdentityNo();

        CreditScore creditScore=new CreditScore("1", LocalDateTime.now(),1000,customer);

        CreditApplication creditApplication=new CreditApplication("creditApplicationId"
                ,getDifferentLocalDateTime()
                , CreditApplicationResult.DENIED,null,4,customer);

        CreditApplicationDTO creditApplicationDTO=generateCreditApplicationDTO(creditApplication);


        when(customerService.findCustomerByIdentityNo(identityNo)).thenReturn(customer);
        when(creditApplicationRepository.findAll()).thenReturn(List.of(creditApplication));
        when(CREDIT_APPLICATION_MAPPER.toDto(creditApplication)).thenReturn(creditApplicationDTO);

        String expected="Identity number: " + customer.getIdentityNo() + "<br/>"
                + "Name: " + customer.getName() + "<br/>"
                + "Surname: " + customer.getSurname() + "<br/>"
                + "Credit Result: " + creditApplicationDTO.getApplicationResult().toString() + "<br/>";


        String result=creditApplicationService.viewLastCreditApplicationResultByIdentityNo(identityNo);

        assertEquals(expected,result);
    }


}