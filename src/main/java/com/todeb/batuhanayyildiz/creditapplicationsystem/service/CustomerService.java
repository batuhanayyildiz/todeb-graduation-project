package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomerById(Long id);
    Customer getCustomerByIdentityNo(String identityNo);
    List<Customer> getAllCustomers();
    void createCustomer(Customer customer);

    boolean deleteCustomer(Long id);
    boolean deleteCustomerByIdentityNo(String identityNo);

    Customer updateCustomerByIdentityNo(String identityNo,Customer customer);

    Customer addCreditScoreToCustomerByIdentityNo(String identityNo);

    Customer addCreditLimitToCustomerByIdentityNo(String identityNo);


}