package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
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

    protected CreditScore getLastCreditApplicationByCustomerIdentityNo(String identityNo)
    {
        List<CreditScore> creditScoresOfCustomer = creditScoreRepository.findAll().stream()
                .filter(creditScore -> creditScore.getCustomer().getIdentityNo() == identityNo)
                .sorted(getCreditScoreCalculationDateComparator()).collect(Collectors.toList());
        if (creditScoresOfCustomer.isEmpty()) {
            throw new NotFoundException("Credit Score");
        } else {
            return creditScoresOfCustomer.get(creditScoresOfCustomer.size() - 1);
        }

    }

    protected int creditScoreCalculation()
    {
        log.info("Business logic of creditScoreCalculation starts");
        int creditScore = (int) Math.round((Math.random() * 1300) + 100); // +100 is used if Math.random() gives 0.
        return creditScore;

    }

    private Comparator<CreditScore> getCreditScoreCalculationDateComparator() {
        return (o1, o2) -> {
            if (o1.getCreditScoreCalculationDate().isBefore(o2.getCreditScoreCalculationDate()))
                return -1;
            if (o1.getCreditScoreCalculationDate().isAfter(o2.getCreditScoreCalculationDate()))
                return 1;
            return 0;
        };

    }
}





