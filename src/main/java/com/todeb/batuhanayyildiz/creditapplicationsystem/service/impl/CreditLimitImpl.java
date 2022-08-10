package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditLimitRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditLimitImpl implements CreditLimitService {

    private CreditLimitRepository creditLimitRepository;


    @Override
    public CreditLimit getCreditLimitById(Long id) {
        Optional<CreditLimit> byId = creditLimitRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("CreditLimit is not found by id "+id);
            return new NotFoundException("CreditLimit");});

    }

    @Override
    public void createCreditLimit(CreditLimit creditLimit) {
        log.info("method is started to use");
        creditLimitRepository.save(creditLimit);

    }

    @Override
    public boolean deleteCreditLimit(Long id) {
        CreditLimit creditLimit=getCreditLimitById(id);
        if(!ObjectUtils.isEmpty(creditLimit)){
            log.info("in if condition");
            creditLimitRepository.delete(getCreditLimitById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());


    }

    @Override
    public CreditLimit updateCreditLimit(CreditLimit creditLimit) {
        Optional<CreditLimit> creditLimitById = creditLimitRepository.findBookByName(creditLimitName);
        if (!creditLimitByName.isPresent()) {
            throw new NotFoundException("Book");
        }
        CreditLimit updatedCreditLimit = creditLimitByName.get();
        if (!ObjectUtils.isEmpty(creditLimitDTO.getName())){
            updatedBook.setName(creditLimitDTO.getName());

        return creditLimitRepository.save(updatedBook);
        return null;
    }

    @Override
    public void addCreditLimitToCustomer(Customer customer) {

    }

    @Override
    public CreditLimit getCreditLimitByCustomerIdentityNo(String customerIdentityNo) {
        return null;
    }

    @Override
    public List<CreditLimit> getAllCreditLimits() {
        return null;
    }
}
