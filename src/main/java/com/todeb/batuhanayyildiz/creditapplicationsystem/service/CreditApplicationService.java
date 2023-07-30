package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditLimitService creditLimitService;
    private final CustomerService customerService;

    private static final CreditApplicationMapper CREDIT_APPLICATION_MAPPER= Mappers.getMapper(CreditApplicationMapper.class);


    protected CreditApplicationDTO createApplicationByCustomerIdentityNo(String identityNo){
        Customer customer=customerService.findCustomerByIdentityNo(identityNo);
        CreditApplication creditApplication=new CreditApplication();
        creditApplication.setCustomer(customer);
        return CREDIT_APPLICATION_MAPPER.toDto(creditApplicationRepository.save(creditApplication));
    }
    protected CreditApplication getLastCreditApplicationByCustomerIdentityNo(String identityNo){
        List<CreditApplication> creditApplication= creditApplicationRepository.findByCustomer_IdentityNo(identityNo)   .stream().sorted(getCreditApplicationDateComparator()).collect(Collectors.toList());
    }
    protected determineApplicationResultByCustomerIdentityNo(String identityNo){
        Customer customer=customerService.findCustomerByIdentityNo(identityNo);


    }






    private Comparator<CreditApplication> getCreditApplicationDateComparator() {
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