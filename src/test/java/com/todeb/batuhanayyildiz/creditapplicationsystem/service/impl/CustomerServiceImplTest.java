package com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl;

import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private  CustomerRepository customerRepository;
    @Mock
    private  CreditScoreServiceImpl creditScoreService;
    @Mock
    private  CreditApplicationServiceImpl creditApplicationService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getCustomerById_successful() {
        // init
        Customer expectedCustomer = getSampleTestCustomers().get(1);
        Optional<Customer> optExpectedCustomer = Optional.of(expectedCustomer);

        // stub
        Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(optExpectedCustomer);

        // then
        Customer actualCustomer = customerService.getCustomerById(2L);


        Assert.assertEquals(actualCustomer.getId(), expectedCustomer.getId());


    }
    @Test
    void getCustomerById_failed() {
        // stub
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class,
                () -> {
                    Customer actCustomer = customerService.getCustomerById(1L);
                }
        );

    }

    @Test
    void getCustomerByIdentityNo_successful() {
        // init
        Customer expectedCustomer = getSampleTestCustomers().get(1);
        Optional<Customer> optExpectedCustomer = Optional.of(expectedCustomer);

        // stub
        Mockito.when(customerRepository.findByIdentityNo(Mockito.any())).thenReturn(optExpectedCustomer);

        // then
        Customer actualCustomer = customerService.getCustomerByIdentityNo("12345678904");


        Assert.assertEquals(actualCustomer.getIdentityNo(), expectedCustomer.getIdentityNo());
    }
    @Test
    void getCustomerByIdentityNo_failed() {
        // stub
        Mockito.when(customerRepository.findByIdentityNo("12345678904")).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class,
                () -> {
                    Customer actCustomer = customerService.getCustomerByIdentityNo("12345678904");
                }
        );
    }

    @Test
    void getAllCustomers() {
        // init
        List<Customer> expCustomerList = getSampleTestCustomers();

        // stub
        Mockito.when(customerRepository.findAll()).thenReturn(expCustomerList);

        // then
        List<Customer> actualCustomerList = customerService.getAllCustomers();

        Assert.assertEquals(expCustomerList.size(), actualCustomerList.size());

        System.out.println("First: " + expCustomerList);
        expCustomerList = expCustomerList.stream().sorted(getCustomerComparator()).collect(Collectors.toList());
        actualCustomerList = actualCustomerList.stream().sorted(getCustomerComparator()).collect(Collectors.toList());
        for (int i = 0; i < expCustomerList.size(); i++) {
            Customer currExpectedCustomer = expCustomerList.get(i);
            Customer currActualCustomer = actualCustomerList.get(i);
            Assert.assertEquals(currExpectedCustomer.getId(), currActualCustomer.getId());
            Assert.assertEquals(currExpectedCustomer.getIdentityNo(), currActualCustomer.getIdentityNo());
            Assert.assertEquals(currExpectedCustomer.getName(), currActualCustomer.getName());
            Assert.assertEquals(currExpectedCustomer.getSurname(), currActualCustomer.getSurname());
            Assert.assertEquals(currExpectedCustomer.getPhoneNo(), currActualCustomer.getPhoneNo());
            Assert.assertEquals(currExpectedCustomer.getMonthlyIncome(), currActualCustomer.getMonthlyIncome());
            Assert.assertEquals(currExpectedCustomer.getCreditApplications(), currActualCustomer.getCreditApplications());
            Assert.assertEquals(currExpectedCustomer.getCreditScores(), currActualCustomer.getCreditScores());


        }

        System.out.println("Second : " + expCustomerList);
    }

    @Test
    void createCustomer() {
        //init
        Customer expected = getSampleTestCustomers().get(0);
        expected.setId(null);

//

        // stub
        Mockito.when(customerRepository.save(any())).thenReturn(expected);

        // then
        Customer customer = new Customer();
        customer.setName(expected.getName());
        customer.setSurname(expected.getSurname());
        customer.setIdentityNo(expected.getIdentityNo());
        customer.setPhoneNo(expected.getPhoneNo());
        customer.setMonthlyIncome(expected.getMonthlyIncome());

        Customer actualCustomer = customerService.createCustomer(customer);


        Assert.assertEquals(expected.getName(), actualCustomer.getName());
        Assert.assertEquals(expected.getSurname(), actualCustomer.getSurname());
        Assert.assertEquals(expected.getIdentityNo(), actualCustomer.getIdentityNo());
        Assert.assertEquals(expected.getPhoneNo(), actualCustomer.getPhoneNo());
        Assert.assertEquals(expected.getMonthlyIncome(), actualCustomer.getMonthlyIncome());
    }

    @Test
    void deleteCustomerById() {
        // init
        Long customerId = 1L;
        Customer customer = getSampleTestCustomers().get(0);

        // stub
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(customerId);

        // then
        customerService.deleteCustomerById(1L);

        verify(customerRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteCustomerByIdentityNo() {
        // init
        String identityNo = "12345678902";
        Customer customer = getSampleTestCustomers().get(0);

        // stub
        Mockito.when(customerRepository.findByIdentityNo(identityNo)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(customer.getId());

        // then
        customerService.deleteCustomerByIdentityNo(identityNo);

        verify(customerRepository,times(1)).deleteById(customer.getId());

    }

    @Test
    void updateCustomerByIdentityNo() {
        // init
        Customer customerPrevious = getSampleTestCustomers().get(0);
        String identityNo = customerPrevious.getIdentityNo();

        Customer customerExpected = getSampleTestCustomers().get(1);
        customerExpected.setIdentityNo(customerPrevious.getIdentityNo());
        customerExpected.setId(customerPrevious.getId());
        // stub
        Mockito.when(customerRepository.findByIdentityNo(identityNo)).thenReturn(Optional.of(customerPrevious));
        Mockito.when(customerRepository.save(any())).thenReturn(customerExpected);
        // then
        Customer actualCustomer= customerService.updateCustomerByIdentityNo(identityNo,customerExpected);

        verify(customerRepository,times(1)).save(customerExpected);

        Assert.assertEquals(customerExpected.getName(), actualCustomer.getName());
        Assert.assertEquals(customerExpected.getSurname(), actualCustomer.getSurname());
        Assert.assertEquals(customerExpected.getMonthlyIncome(), actualCustomer.getMonthlyIncome());
        Assert.assertEquals(customerExpected.getPhoneNo(), actualCustomer.getPhoneNo());
    }



    private List<Customer> getSampleTestCustomers() {
        List<Customer> sampleList = new ArrayList<>();
        Customer customer = new Customer(1L,"12345678902","Ali","Genc",5000,
                "05055055050",null,null);
        Customer customer2 = new Customer(2L,"12345678904","Ahmet","Genc",8000,
                "05055055051",null,null);
        Customer customer3 = new Customer(3L,"12345678906","Mehmet","Kaya",10000,
                "05055055052",null,null);
        sampleList.add(customer2);
        sampleList.add(customer);
        sampleList.add(customer3);
        return sampleList;
    }

    private Comparator<Customer> getCustomerComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }
}