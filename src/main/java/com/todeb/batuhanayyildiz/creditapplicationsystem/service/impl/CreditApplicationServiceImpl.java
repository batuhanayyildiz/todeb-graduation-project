package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;

    private final CreditScoreServiceImpl creditScoreService;

    private final CreditLimitServiceImpl creditLimitService;



    @Override
    public CreditApplication getCreditApplicationById(Long id) {
        Optional<CreditApplication> byId = creditApplicationRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("Credit Application is not found by id "+id);
            return new NotFoundException("Credit Application");});

    }

    @Override
    public CreditApplication getLastCreditApplicationByCustomer(Customer customer) {
        List<CreditApplication> creditApplicationByIdentityNo = creditApplicationRepository.findAll().stream()
                .filter(creditApplication -> creditApplication.getCustomer()==customer)
                .sorted(getCreditApplicationComparator()).collect(Collectors.toList());
        Optional<CreditApplication> creditApplication=Optional.of(creditApplicationByIdentityNo.get(creditApplicationByIdentityNo.size()-1));

        return creditApplication.orElseThrow(()->{

            //log.error("Credit Applications is not found by identity number: "+identityNo);
            return new NotFoundException("Credit Application");});
    }


    @Override
    public CreditApplication createCreditApplication(Customer customer) {
        log.info("method is started to use");
        CreditApplication creditApplication =  new CreditApplication(customer);
        return creditApplicationRepository.save(creditApplication);

    }
    @Override
    public CreditApplication determineLastCreditApplicationStatusByCustomer(Customer customer) {
        CreditApplication creditApplication = getLastCreditApplicationByCustomer(customer);
        int customerCreditScore = creditScoreService.getLastCreditScoreByCustomer(customer).getScore();
        if (customerCreditScore<500){
            creditApplication.setApplicationStatus(CreditApplicationStatus.DENIED);
        }
        else{
            creditApplication.setApplicationStatus(CreditApplicationStatus.ACCEPTED);
        }

        return creditApplicationRepository.save(creditApplication);
    }
    public CreditApplication addCreditLimitToCreditApplicationByCustomer(Customer customer) {
        log.info("Business logic is started");
        CreditApplication creditApplication= getLastCreditApplicationByCustomer(customer);
        CreditLimit creditLimit = creditLimitService.createCreditLimit(creditApplication);
        creditApplication.getCreditLimits().add(creditLimit);
        log.info("Business logic is completed");
        return creditApplicationRepository.save(creditApplication);
    }








    private Comparator<CreditApplication> getCreditApplicationComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }
}
