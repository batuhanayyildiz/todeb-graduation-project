package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
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


    private final CreditLimitRepository creditLimitRepository;

    private final CreditApplicationServiceImpl applicationService;


    @Override
    public CreditLimit getCreditLimitById(Long id) {
        return null;
    }

    @Override
    public CreditLimit getCreditLimitByApplicationId(Long id) {
        return null;
    }

    @Override
    public int creditLimitCalculation(CreditApplication creditApplication) {
        return 0;
    }
}
