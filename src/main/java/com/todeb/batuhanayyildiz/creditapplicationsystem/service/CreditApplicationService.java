package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CanApplyConditionException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.*;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CreditApplicationRepository;
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
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;

    private final CreditScoreService creditScoreService;

    private final CreditLimitService creditLimitService;



    public List<CreditApplication> getAllCreditApplications() {
        log.info("Business logic of getAllCreditApplications method starts");
        List<CreditApplication> creditApplications= creditApplicationRepository.findAll();
        log.info("Business logic of getAllCreditApplications method ends");
        return creditApplications;
    }

    public List<CreditApplication> getAllCreditApplicationsOfCustomerByCustomer(Customer customer) {
        log.info("Business logic of getAllCreditApplicationsOfCustomerByCustomer method starts");
        List<CreditApplication> creditApplicationByCustomer = creditApplicationRepository.findAll().stream()
                .filter(creditApplication -> creditApplication.getCustomer()==customer).collect(Collectors.toList());
        if (creditApplicationByCustomer.size()<1){
            log.error("Credit Application is not found by customer");
            throw new NotFoundException("Credit Application");
        }
        log.info("Business logic of getAllCreditApplicationsOfCustomerByCustomer method ends");
        return creditApplicationByCustomer;


    }


    public CreditApplication getCreditApplicationById(Long id) {
        log.info("Business logic of getCreditApplicationById method starts");
        Optional<CreditApplication> byId = creditApplicationRepository.findById(id);
        log.info("Business logic of getCreditApplicationById method ends");
        return byId.orElseThrow(()->{
            log.error("Credit Application is not found by id "+id);
            return new NotFoundException("Credit Application");});

    }



    public CreditApplication getLastCreditApplicationByCustomer(Customer customer) {
        log.info("Business logic of getLastCreditApplicationByCustomer method starts");
        List<CreditApplication> creditApplicationByIdentityNo = creditApplicationRepository.findAll().stream()
                .filter(creditApplication -> creditApplication.getCustomer()==customer)
                .sorted(getCreditApplicationComparator()).collect(Collectors.toList());
        if (creditApplicationByIdentityNo.size()<1){
            log.error("Credit Application is not found by customer");
            throw new NotFoundException("Credit Application");
        }
        Optional<CreditApplication> creditApplication=Optional.of(creditApplicationByIdentityNo.get(creditApplicationByIdentityNo.size()-1));

        log.info("Business logic of getLastCreditApplicationByCustomer method ends");
        return creditApplication.orElseThrow(()->{
            log.error("Credit Applications is not found");
            return new NotFoundException("Credit Application");});
    }




    public CreditApplication determineLastCreditApplicationStatusByCustomer(Customer customer) {
        log.info("Business logic of determineLastCreditApplicationStatusByCustomer method starts");
        CreditApplication creditApplication = getLastCreditApplicationByCustomer(customer);
        if(ObjectUtils.isEmpty(creditApplication)){
            log.error("Credit Application is not found");
            throw new NotFoundException("Credit Application with related customer");
        }
        if( creditApplication.getApplicationStatus()== CreditApplicationResult.WAITING){
            int customerCreditScore = creditScoreService.getLastCreditScoreByCustomer(customer).getScore();
            if (customerCreditScore<500){
                creditApplication.setApplicationStatus(CreditApplicationResult.DENIED);
            }
            else{
                creditApplication.setApplicationStatus(CreditApplicationResult.ACCEPTED);
            }
            log.info("Business logic of determineLastCreditApplicationStatusByCustomer method ends");
            return creditApplicationRepository.save(creditApplication);
        }else{
            log.error("CanApplyCondition error");
            throw new CanApplyConditionException(
                "There is no need for determining result. This credit application was evaluated before");}




    }
    public void addCreditLimitToCreditApplicationByCustomer(Customer customer) {
        log.info("Business logic of addCreditLimitToCreditApplicationByCustomer method starts");
        CreditApplication creditApplication= getLastCreditApplicationByCustomer(customer);
        creditLimitService.addCreditLimitToCreditApplicationByCreditApplication(creditApplication);
        log.info("Business logic of addCreditLimitToCreditApplicationByCustomer method ends");

    }


    public CreditApplication addCreditApplicationToCustomerByCustomer(Customer customer) {
        log.info("Business logic of addCreditApplicationToCustomerByCustomer starts");
        if (customerCanApplyForCredit(customer)){
            CreditApplication creditApplication =  new CreditApplication(customer);
            log.info("Business logic of createCreditApplication method ends");
            return creditApplicationRepository.save(creditApplication);
        }
        else {
            log.error("canApplyCondition error");
            throw new CanApplyConditionException("New application can not be done. " +
                    "Please wait for the previous application to be finalized ");
        }
    }
/* No update, user should reapply
    public CreditApplication updateCreditLimitOfCreditApplicationByCustomer(Customer customer) {
        log.info("Business logic of updateCreditLimitOfCreditApplicationByCustomer method starts");
        CreditApplication updateCreditApplication= getLastCreditApplicationByCustomer(customer);
        CreditScore creditScore = creditScoreService.getLastCreditScoreByCustomer(customer);
        CreditLimit creditLimit= creditLimitService.getLastCreditLimitByCreditApplication(updateCreditApplication);
        double updateCreditLimit= creditLimitService.creditLimitCalculation(customer,updateCreditApplication,creditScore);
        creditLimit.setCreditLimit(updateCreditLimit);
        log.info("Business logic of updateCreditLimitOfCreditApplicationByCustomer method ends");
        return creditApplicationRepository.save(updateCreditApplication);


    }*/

    public String viewCreditApplicationResultByCustomer(Customer customer){
        log.info("Business logic of viewCreditApplicationResultByCustomer method starts");
        String result="";
        CreditApplication creditApplication= getLastCreditApplicationByCustomer(customer);
        if (ObjectUtils.isEmpty(creditApplication)){
            log.error("Credit Application is not found");
            throw new NotFoundException("Credit Application");
        }
        if (creditApplication.getCreditLimit()!=null){
            result = "Identity number: " + customer.getIdentityNo()+ " --- "
                    +"Name: "+customer.getName() + " --- "
                    +"Surname: "+customer.getSurname() + " --- "
                    +"Credit Result: " + creditApplication.getApplicationStatus().toString() + " --- "
                    +"Credit Limit: " + " Adjusting process is not ended";

            log.error("Credit Limit is not found. ");
        }
        else {
            CreditLimit creditLimit= creditLimitService.getLastCreditLimitByCreditApplication(creditApplication);
            result = "Identity number: " + customer.getIdentityNo() + " --- "
                    + "Name: " + customer.getName() + " --- "
                    + "Surname: " + customer.getSurname() + " --- "
                    + "Credit Result: " + creditApplication.getApplicationStatus().toString() + " --- "
                    + "Credit Limit: " + String.valueOf(creditLimit.getCreditLimit());
            log.info("Business logic of viewCreditApplicationResultByCustomer method ends");
        }
        return result;

    }

    public boolean customerCanApplyForCredit(Customer customer){
        log.info("Business logic of customerCanApplyForCredit starts");
        if(customer.getCreditApplications().size()<1){
            return true;
        }
        CreditApplication creditApplication= getLastCreditApplicationByCustomer(customer);
        if(creditApplication.getApplicationStatus()== CreditApplicationResult.WAITING){
            return false;
        }

        return true;


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
