package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceImplTest {

    @Mock
    private  CreditApplicationRepository creditApplicationRepository;
    @Mock
    private  CreditScoreServiceImpl creditScoreService;
    @Mock
    private  CreditLimitServiceImpl creditLimitService;

    @InjectMocks
    private CreditApplicationServiceImpl creditApplicationService;


    @Test
    void getAllCreditApplications(){

    }
    @Test
    void getAllCreditApplicationsOfCustomerByCustomer() {
    }

    @Test
    void getCreditApplicationById() {
    }



    @Test
    void getLastCreditApplicationByCustomer() {
    }

    @Test
    void createCreditApplication() {
    }

    @Test
    void determineLastCreditApplicationStatusByCustomer() {
    }

    @Test
    void addCreditLimitToCreditApplicationByCustomer() {
    }

    @Test
    void updateCreditLimitOfCreditApplicationByCustomer() {
    }

    @Test
    void viewCreditApplicationResultByCustomer() {
    }
    /*
    private List<CreditApplication> getSampleTestCreditApplications() {
        List<CreditApplication> sampleList = new ArrayList<>();
        //CreditApplication creditApplication = new CreditApplication(1L,"06.07.2022", CreditApplicationStatus.WAITING,new Customer(),)
        CreditApplication creditApplication2 = new CreditApplication;
        CreditApplication creditApplication3 = new CreditApplication;
        sampleList.add(creditApplication2);
       // sampleList.add(creditApplication);
        sampleList.add(creditApplication3);
        return sampleList;
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
*/

}