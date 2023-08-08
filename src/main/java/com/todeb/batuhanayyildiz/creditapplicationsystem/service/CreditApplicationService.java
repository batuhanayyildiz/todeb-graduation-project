package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CreditLimitCalculatedException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationResult;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditLimitService creditLimitService;
    private final CustomerService customerService;
    private final SmsService smsService;
    private final CreditScoreService creditScoreService;

    private static final CreditApplicationMapper CREDIT_APPLICATION_MAPPER= Mappers.getMapper(CreditApplicationMapper.class);


    public CreditApplicationDTO createApplicationByCustomerIdentityNo(String identityNo)
    {
        Customer customer=customerService.findCustomerByIdentityNo(identityNo);
        CreditApplication creditApplication=new CreditApplication();
        creditApplication.setCustomer(customer);
        return CREDIT_APPLICATION_MAPPER.toDto(creditApplicationRepository.save(creditApplication));
    }
    protected CreditApplication findLastCreditApplicationByCustomerIdentityNo(String identityNo)
    {
        Customer customer= customerService.findCustomerByIdentityNo(identityNo);
        List<CreditApplication> creditApplicationsOfCustomer = creditApplicationRepository.findAll().stream()
                .filter(creditApplication ->creditApplication.getCustomer().equals(customer))
                .sorted(getCreditApplicationDateComparator()).collect(Collectors.toList());
        if(creditApplicationsOfCustomer.isEmpty()){
            throw new NotFoundException("Credit Application");
        }
        else{
            return creditApplicationsOfCustomer.get(creditApplicationsOfCustomer.size()-1);
        }

    }
    protected CreditApplication determineApplicationResultByCustomerIdentityNo(String identityNo)
    {

        Customer customer=customerService.findCustomerByIdentityNo(identityNo);
        CreditApplication creditApplication= findLastCreditApplicationByCustomerIdentityNo(identityNo);
        if (creditApplication.getApplicationResult() != CreditApplicationResult.WAITING){
            log.error("Limit is already calculated");
            throw new CreditLimitCalculatedException(creditApplication.getId());}
        log.info("CreditScoreService is reached by determineApplicationResultByCustomerIdentityNo() method");
        int creditScore=creditScoreService.findLastCreditScoreByCustomerIdentityNo(identityNo).getScore();

        if (creditScore<500){
            creditApplication.setApplicationResult(CreditApplicationResult.DENIED);
        }
        else{
            creditApplication.setApplicationResult(CreditApplicationResult.ACCEPTED);
            double limit =creditLimitService.calculateCreditLimit(creditApplication.getCreditMultiplier()
                    ,customer.getMonthlyIncome(),creditScore);

            CreditLimit creditLimit= new CreditLimit(creditApplication,limit);
            creditApplication.setCreditLimit(creditLimit);

        }
        smsService.sendNotificationByPhoneNumber(customer.getPhoneNo());
        return creditApplicationRepository.save(creditApplication);

    }

    public CreditApplicationDTO getLastCreditApplicationByIdentityNo(String identityNo)

    {
        CreditApplication creditApplication=findLastCreditApplicationByCustomerIdentityNo(identityNo);
        if (creditApplication.getApplicationResult()==CreditApplicationResult.WAITING)
        {
            creditApplication=determineApplicationResultByCustomerIdentityNo(identityNo);
        }
        return CREDIT_APPLICATION_MAPPER.toDto(creditApplication);
    }
    public String viewLastCreditApplicationResultByIdentityNo(String identityNo)
    {

        CreditApplicationDTO creditApplicationDTO= getLastCreditApplicationByIdentityNo(identityNo);
        Customer customer = customerService.findCustomerByIdentityNo(identityNo);
        String result;
        if (creditApplicationDTO.getApplicationResult()!=CreditApplicationResult.ACCEPTED)
        {
            result = "Identity number: " + customer.getIdentityNo() + "<br/>"
                    + "Name: " + customer.getName() + "<br/>"
                    + "Surname: " + customer.getSurname() + "<br/>"
                    + "Credit Result: " + creditApplicationDTO.getApplicationResult().toString() + "<br/>";
            log.info("Business logic of viewCreditApplicationResultByIdentityNo method ends");
        }
        else
        {
            result = "Identity number: " + customer.getIdentityNo() + "<br/>"
                    + "Name: " + customer.getName() + "<br/>"
                    + "Surname: " + customer.getSurname() + "<br/>"
                    + "Credit Result: " + creditApplicationDTO.getApplicationResult().toString() + "<br/>"
                    + "Credit Limit: " + creditApplicationDTO.getCreditLimit().getCreditLimit();
            log.info("Business logic of viewCreditApplicationResultByIdentityNo method ends");
        }
        return result;

    }








    private Comparator<CreditApplication> getCreditApplicationDateComparator()
    {
        return (o1, o2) -> {
            if (o1.getCreditApplicationDate().isBefore(o2.getCreditApplicationDate()))
                return -1;
            if (o1.getCreditApplicationDate().isAfter(o2.getCreditApplicationDate()))
                return 1;
            return 0;
        };
    }

}
/*
    private Comparator<CreditApplication> getCreditApplicationComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }

 */