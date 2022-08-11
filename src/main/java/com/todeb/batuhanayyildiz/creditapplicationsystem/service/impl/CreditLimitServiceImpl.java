package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
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
public class CreditLimitServiceImpl implements CreditLimitService {


    //Baştan yapmalısın customer'dan applicationa attın bağlantıyı customeridentityno içeren her şey hata verecek
    /*


    private final CreditLimitRepository creditLimitRepository;

    private final CreditApplicationServiceImpl applicationService;


    @Override
    public CreditLimit getCreditLimitById(Long id) {
        Optional<CreditLimit> byId = creditLimitRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("CreditLimit is not found by id "+id);
            return new NotFoundException("CreditLimit");});

    }
    @Override
    public CreditLimit getCreditLimitByCustomerIdentityNo(String identityNo) {

        Optional<CreditLimit> creditLimitByCustomerIdentityNo = creditLimitRepository.findByCustomer_IdentityNo(identityNo);
        return creditLimitByCustomerIdentityNo.orElseThrow(()->{
            log.error("CreditLimit is not found by identity number: "+identityNo);
            return new NotFoundException("CreditLimit");});


    }

    @Override
    public List<CreditLimit> getAllCreditLimits() {
        return creditLimitRepository.findAll();
    }

    @Override
    public CreditLimit createCreditLimit(CreditLimit creditLimit) {
        log.info("method is started to use");
        return creditLimitRepository.save(creditLimit);

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
    public boolean deleteCreditLimitByCustomerIdentityNo(String identityNo){
        Optional<CreditLimit> creditLimitByCustomerIdentityNo = creditLimitRepository.findByCustomer_IdentityNo(identityNo);
        CreditLimit creditLimit=creditLimitByCustomerIdentityNo.get();
        Long id=creditLimit.getId();
        if(!ObjectUtils.isEmpty(creditLimitByCustomerIdentityNo)){
            log.info("in if condition");
            creditLimitRepository.delete(getCreditLimitById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());

    }

    @Override
    public CreditLimit updateCreditLimitByCustomerIdentityNo(String identityNo,CreditLimit creditLimit) {
        Optional<CreditLimit> creditLimitByCustomerIdentityNo = creditLimitRepository.findByCustomer_IdentityNo(identityNo);
        if (!creditLimitByCustomerIdentityNo.isPresent()) {
            throw new NotFoundException("Credit Limit");
        }
        CreditLimit updatedCreditLimit = creditLimitByCustomerIdentityNo.get();
        if (!ObjectUtils.isEmpty(creditLimit.getCreditLimit())){
            updatedCreditLimit.setCreditLimit(creditLimit.getCreditLimit());}

        return creditLimitRepository.save(updatedCreditLimit);
        }

    @Override
    public int creditLimitDeterminationByIdentityNo(String identityNo) {
        return 0;

    }


*/

}
