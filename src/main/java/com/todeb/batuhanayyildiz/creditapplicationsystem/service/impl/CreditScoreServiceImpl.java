package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditScoreRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditScoreServiceImpl implements CreditScoreService {
    private final CreditScoreRepository creditScoreRepository;



    @Override
    public CreditScore getCreditScoreById(Long id) {
        Optional<CreditScore> byId = creditScoreRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("CreditScore is not found by id "+id);
            return new NotFoundException("CreditScore");});

    }
    @Override
    public CreditScore getCreditScoreByCustomerIdentityNo(String identityNo) {
        Optional<CreditScore> creditScoreByCustomerIdentityNo = creditScoreRepository.findByCustomer_IdentityNo(identityNo);
        return creditScoreByCustomerIdentityNo.orElseThrow(()->{
            log.error("CreditScore is not found by identity number: "+identityNo);
            return new NotFoundException("CreditScore");});
    }

    @Override
    public List<CreditScore> getAllCreditScores() {
        return creditScoreRepository.findAll();
    }

    @Override
    public CreditScore createCreditScore(Customer customer) {
        log.info("method is started to use");
        CreditScore creditScore =  new CreditScore(customer,creditScoreCalculation());
        return creditScoreRepository.save(creditScore);


    }

    @Override
    public boolean deleteCreditScore(Long id) {
        CreditScore creditScore=getCreditScoreById(id);
        if(!ObjectUtils.isEmpty(creditScore)){
            log.info("in if condition");
            creditScoreRepository.delete(getCreditScoreById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());


    }
    @Override
    public boolean deleteCreditScoreByCustomerIdentityNo(String identityNo){
        Optional<CreditScore> creditScoreByCustomerIdentityNo = creditScoreRepository.findByCustomer_IdentityNo(identityNo);
        CreditScore creditScore=creditScoreByCustomerIdentityNo.get();
        Long id=creditScore.getId();
        if(!ObjectUtils.isEmpty(creditScoreByCustomerIdentityNo)){
            log.info("in if condition");
            creditScoreRepository.delete(getCreditScoreById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());

    }

    @Override
    public CreditScore updateCreditScoreByCustomerIdentityNo(String identityNo,CreditScore creditScore) {
        Optional<CreditScore> creditScoreByCustomerIdentityNo = creditScoreRepository.findByCustomer_IdentityNo(identityNo);
        if (!creditScoreByCustomerIdentityNo.isPresent()) {
            throw new NotFoundException("Credit Score");
        }
        CreditScore updatedCreditScore = creditScoreByCustomerIdentityNo.get();
        if (!ObjectUtils.isEmpty(creditScore.getScore())){
            updatedCreditScore.setScore(creditScore.getScore());}

        return creditScoreRepository.save(updatedCreditScore);
    }

    @Override
    public int creditScoreCalculation() {
        int creditScore=(int)Math.round((Math.random()*1300)+100); // +100 is used if Math.random() gives 0.
        return creditScore;

    }


}
