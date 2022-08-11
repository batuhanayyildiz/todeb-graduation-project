package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.CustomJwtException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
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
    private final CreditScoreServiceImpl creditScoreService;



    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("Customer is not found by id "+id);
            return new NotFoundException("Customer");});

    }
    @Override
    public Customer getCustomerByIdentityNo(String identityNo) {
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
        log.info("method is started to use");
        if (!customerRepository.existsByIdentityNo(customer.getIdentityNo())){
            return customerRepository.save(customer);
        }
        else {
            throw new CustomJwtException("There is a book with same name.", HttpStatus.BAD_REQUEST);
        }



    }

    @Override
    public boolean deleteCustomerById(Long id) {
        Customer customer=getCustomerById(id);
        if(!ObjectUtils.isEmpty(customer)){
            log.info("in if condition");
            customerRepository.delete(getCustomerById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());


    }
    @Override
    public boolean deleteCustomerByIdentityNo(String identityNo){
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        Customer customer=customerByIdentityNo.get();
        Long id=customer.getId();
        if(!ObjectUtils.isEmpty(customerByIdentityNo)){
            log.info("in if condition");
            customerRepository.delete(getCustomerById(id));
            return true;
        }
        else throw new NotFoundException("id"+""+id.toString());

    }

    @Override
    public Customer updateCustomerByIdentityNo(String identityNo,Customer customer) {
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        if (!customerByIdentityNo.isPresent()) {
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


        return customerRepository.save(updatedCustomer);
    }
    @Override
    public Customer addCreditScoreToCustomerByIdentityNo(String identityNo){
        log.info("Business logic is started");
        CreditScore creditScore = creditScoreService.createCreditScore();
        creditScore.setCreditScore(creditScoreService.creditScoreCalculation());

        Customer customer= getCustomerByIdentityNo(identityNo);
        customer.setCreditScore(creditScore);
        log.info("Business logic is completed");
        return customer;

    }


}
