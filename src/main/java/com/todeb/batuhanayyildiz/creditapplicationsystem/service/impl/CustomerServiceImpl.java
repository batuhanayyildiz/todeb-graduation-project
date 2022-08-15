package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CanApplyConditionException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CustomJwtException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CustomerRepository;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditScoreService;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CreditApplicationServiceImpl creditApplicationService;



    @Override
    public Customer getCustomerById(Long id) {
        log.info("Business logic of getCustomerById starts");
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("Customer is not found by id "+id);
            return new NotFoundException("Customer");});

    }
    @Override
    public Customer getCustomerByIdentityNo(String identityNo) {
        log.info("Business logic of getCustomerByIdentityNo starts");
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        return customerByIdentityNo.orElseThrow(()->{
            log.error("Customer is not found by identity number: "+identityNo);
            return new NotFoundException("Customer");});
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        log.info("Business logic of createCustomer starts");
        if (!customerRepository.existsByIdentityNo(customer.getIdentityNo())){
            log.info("Business logic of createCustomer ends");
            return customerRepository.save(customer);
        }
        else {
            throw new CustomJwtException("There is a customer with same identity number.", HttpStatus.BAD_REQUEST);
        }





    }

    @Override
    public void deleteCustomerById(Long id) {
        log.info("Business logic of deleteCustomerById starts");
        getCustomerById(id);
        log.info("Business logic of deleteCustomerById ends");
        customerRepository.deleteById(id);

    }
    @Override
    public void deleteCustomerByIdentityNo(String identityNo) {
        log.info("Business logic of deleteCustomerByIdentityNo starts");
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        customerRepository.deleteById(customerByIdentityNo.get().getId());
        log.info("\"Business logic of deleteCustomerByIdentityNo ends");
    }


    @Override
    public Customer updateCustomerByIdentityNo(String identityNo,Customer customer) {
        log.info("Business logic of updateCustomerByIdentityNo starts");
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        if (!customerByIdentityNo.isPresent()) {
            log.error("Customer is not found");
            throw new NotFoundException("Customer");
        }
        Customer updatedCustomer = customerByIdentityNo.get();
        if (!ObjectUtils.isEmpty(customer.getName())){
            updatedCustomer.setName(customer.getName());}

        if (!ObjectUtils.isEmpty(customer.getSurname())){
            updatedCustomer.setSurname(customer.getSurname());}

        if (!ObjectUtils.isEmpty(customer.getPhoneNo())){
            updatedCustomer.setPhoneNo(customer.getPhoneNo());}

        if (!ObjectUtils.isEmpty(customer.getMonthlyIncome())){
            updatedCustomer.setMonthlyIncome(customer.getMonthlyIncome());}

        log.info("Business logic of updateCustomerByIdentityNo ends");
        return customerRepository.save(updatedCustomer);
    }






}
