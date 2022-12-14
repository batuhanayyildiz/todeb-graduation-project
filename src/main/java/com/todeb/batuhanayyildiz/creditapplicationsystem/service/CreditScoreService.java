package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditScoreService {
    private final CreditScoreRepository creditScoreRepository;

    public CreditScore getCreditScoreById(Long id) {
        Optional<CreditScore> byId = creditScoreRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("CreditScore is not found by id "+id);
            return new NotFoundException("CreditScore");});

    }

    public CreditScore getAllCreditScoresByCustomerIdentityNo(String identityNo) {
        Optional<CreditScore> creditScoreByCustomerIdentityNo = creditScoreRepository.findByCustomer_IdentityNo(identityNo);
        return creditScoreByCustomerIdentityNo.orElseThrow(()->{
            log.error("CreditScore is not found by identity number: "+identityNo);
            return new NotFoundException("CreditScore");});
    }



    public CreditScore getLastCreditScoreByCustomer(Customer customer) {
        log.info("Business logic of getLastCreditScoreByCustomer starts");
        List<CreditScore> creditScoreByCustomer = creditScoreRepository.findAll().stream()
                .filter(creditScore -> creditScore.getCustomer()==customer)
                .sorted(getCreditScoreComparator()).collect(Collectors.toList());
        if (creditScoreByCustomer.size()<1){
            log.error("Credit Score is not found by customer");
            throw new NotFoundException("Credit Score");
        }
        Optional<CreditScore> creditScore= Optional.of(creditScoreByCustomer.get(creditScoreByCustomer.size()-1));

        return creditScore.orElseThrow(()->{

            log.error("Credit Score is not found");
            return new NotFoundException("Credit Score");});
    }


    public List<CreditScore> getAllCreditScores() {
        return creditScoreRepository.findAll();
    }



    public boolean deleteCreditScore(Long id) {
        CreditScore creditScore=getCreditScoreById(id);
        if(!ObjectUtils.isEmpty(creditScore)){
            log.info("in if condition");
            creditScoreRepository.delete(getCreditScoreById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());


    }

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


    public int creditScoreCalculation() {
        log.info("Business logic of creditScoreCalculation starts");
        int creditScore=(int)Math.round((Math.random()*1300)+100); // +100 is used if Math.random() gives 0.
        return creditScore;

    }

    public CreditScore addCreditScoreToCustomerByCustomer(Customer customer){
        log.info("Business logic of addCreditScoreToCustomerByCustomer starts");
        if(!ObjectUtils.isEmpty(customer)){
            log.info("method is started to use");
            CreditScore creditScore =  new CreditScore(customer,creditScoreCalculation());
            log.info("Business logic is completed");
            return creditScoreRepository.save(creditScore);

        }
        else throw new NotFoundException("Customer");


    }

    private Comparator<CreditScore> getCreditScoreComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }


}
