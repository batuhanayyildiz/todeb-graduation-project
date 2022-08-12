package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
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


    @Override
    public CreditApplication getCreditApplicationById(Long id) {
        Optional<CreditApplication> byId = creditApplicationRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("Credit Application is not found by id "+id);
            return new NotFoundException("Credit Application");});

    }

    @Override
    public CreditApplication getLastCreditApplicationByCustomerIdentityNo(String identityNo) {
        List<CreditApplication> creditApplicationByIdentityNo = creditApplicationRepository.findAll().stream()
                .filter(creditApplication -> creditApplication.getCustomer().getIdentityNo() == identityNo)
                .sorted(getCreditApplicationComparator()).collect(Collectors.toList());
        Optional<CreditApplication> creditApplication=Optional.of(creditApplicationByIdentityNo.get(creditApplicationByIdentityNo.size()-1));
        return creditApplication.orElseThrow(()->{
            log.error("Credit Application is not found by identity number: "+identityNo);
            return new NotFoundException("Credit Application");});
    }


    @Override
    public CreditApplication createCreditApplication(Customer customer) {
        log.info("method is started to use");
        CreditApplication creditApplication =  new CreditApplication(customer);
        return creditApplicationRepository.save(creditApplication);

    }

    @Override
    public CreditApplication changeStatus(CreditApplication creditApplication) {
        return null;
    }

    @Override
    public List<CreditApplication> getAllCreditApplications() {
        return null;
    }

    @Override
    public boolean deleteCreditApplication(Long id) {
        return false;
    }

    @Override
    public boolean deleteCreditApplicationByCustomerIdentityNo(String identityNo) {
        return false;
    }

    @Override
    public CreditApplication updateCreditApplicationByCustomerIdentityNo(String identityNo, CreditApplication creditApplication) {
        return null;
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
