package com.todeb.batuhanayyildiz.creditapplicationsystem.service;

import com.todeb.batuhanayyildiz.creditapplicationsystem.TestSupport;
import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Role;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.User;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CustomerMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest extends TestSupport {

    private  CustomerRepository customerRepository;
    private  CreditScoreService creditScoreService;
    private   CustomerMapper CUSTOMER_MAPPER;

    private  Clock clock;
    private CustomerService customerService;

    @BeforeEach
    void setUp()
    {
        customerRepository= Mockito.mock(CustomerRepository.class);
        creditScoreService=Mockito.mock(CreditScoreService.class);
        CUSTOMER_MAPPER=Mockito.mock(CustomerMapper.class);
        clock=Mockito.mock(Clock.class);

        customerService=new CustomerService(customerRepository,creditScoreService,clock);


        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());

    }

    @Test
    void testCreateCustomer_whenCustomerCreatedSuccessfully()
    {

        Customer customer=new Customer("customerId","identityNo", "customerName", "customerSurname"
            ,50,"12345678",new HashSet<>(),new HashSet<>());
        CustomerDTO customerDTO=generateCustomerDTO(customer);

        CreditScore creditScore=new CreditScore("",getLocalDateTime()
                ,5000,customer);

        Customer customerUpdated=new Customer("customerId","identityNo", "customerName", "customerSurname"
                ,50,"12345678",Set.of(creditScore),new HashSet<>());




        CustomerDTO expectedDTO=generateCustomerDTO(customerUpdated);

        when(creditScoreService.creditScoreCalculation()).thenReturn(5000);
        when(CUSTOMER_MAPPER.toEntity(customerDTO)).thenReturn(customer);
        when(CUSTOMER_MAPPER.toDto(customerUpdated)).thenReturn(expectedDTO);
        when(customerRepository.save(any())).thenReturn(customerUpdated);



        CustomerDTO result=customerService.createCustomer(customerDTO);


        assertEquals(result,expectedDTO);



    }

    @Test
    void testFindCustomerById_whenCustomerIdExists()
    {
        Customer customer=generateCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Customer result= customerService.findCustomerById(customer.getId());


        assertEquals(result,customer);



    }
    @Test
    void testFindCustomerById_whenCustomerDoesNotIdExist_shouldThrowNotFoundException()
    {
       String id ="id";
        when(customerRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.findCustomerById(id));

    }

    @Test
    void testGetCustomerById_whenCustomerIdExists()
    {
        Customer customer=generateCustomer();
        CustomerDTO customerDTO=generateCustomerDTO(customer);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(CUSTOMER_MAPPER.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result=customerService.getCustomerById(customer.getId());

        assertEquals(result,customerDTO);
    }
    @Test
    void testGetCustomerById_whenCustomerIdDoesNotExists_shouldThrowNotFoundException()
    {
        String id ="id";
        when(customerRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.getCustomerById(id));


    }

    @Test
    void testDeleteCustomerById_whenCustomerIdExists()
    {
        Customer customer=generateCustomer();
        String customerId=customer.getId();


        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomerById(customerId);

        Mockito.verify(customerRepository).deleteById(customerId);
    }

    @Test
    void testDeleteCustomerById_whenCustomerIdDoesNotExist_shouldThrowNotFoundException()
    {
        Customer customer=generateCustomer();
        String customerId=customer.getId();


        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.deleteCustomerById(customerId));

        Mockito.verify(customerRepository, never()).deleteById(customerId);
    }

    @Test
    void testUpdateCustomerById_whenCustomerIdExists()
    {
        Customer customer=generateCustomer();
        String customerId=customer.getId();
        CustomerDTO customerDTO=generateCustomerDTO(customer);
        Customer updatedCustomer =new Customer("customerId","identityNo"
                , "updated-customerName", "updated-customerSurname"
                ,100,"987456123",new HashSet<>(),new HashSet<>());
        CustomerDTO updatedCustomerDTO =generateCustomerDTO(updatedCustomer);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        when(CUSTOMER_MAPPER.toDto(updatedCustomer)).thenReturn(updatedCustomerDTO);

        CustomerDTO result= customerService.updateCustomerById(customerId,updatedCustomerDTO);

        assertEquals(result,updatedCustomerDTO);

        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(updatedCustomer);



    }
    @Test
    void testUpdateCustomerById_whenCustomerIdDoesNotExist_shouldThrowNotFoundException()
    {
        Customer customer=generateCustomer();
        String customerId=customer.getId();
        CustomerDTO customerDTO=generateCustomerDTO(customer);



        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.updateCustomerById(customerId,customerDTO));

        Mockito.verify(customerRepository, never()).save(customer);
    }


    @Test
    void testFindCustomerByIdentityNo_whenCustomerIdentityNoExists()
    {
        Customer customer=generateCustomer();
        when(customerRepository.findByIdentityNo(customer.getIdentityNo())).thenReturn(Optional.of(customer));

        Customer result= customerService.findCustomerByIdentityNo(customer.getIdentityNo());


        assertEquals(result,customer);



    }
    @Test
    void testFindCustomerByIdentityNo_whenCustomerDoesNotIdentityNoExist_shouldThrowNotFoundException()
    {
        String IdentityNo ="IdentityNo";
        when(customerRepository.findByIdentityNo(IdentityNo)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.findCustomerByIdentityNo(IdentityNo));

    }

    @Test
    void testGetCustomerByIdentityNo_whenCustomerIdentityNoExists()
    {
        Customer customer=generateCustomer();
        CustomerDTO customerDTO=generateCustomerDTO(customer);
        when(customerRepository.findByIdentityNo(customer.getIdentityNo())).thenReturn(Optional.of(customer));
        when(CUSTOMER_MAPPER.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result=customerService.getCustomerByIdentityNo(customer.getIdentityNo());

        assertEquals(result,customerDTO);
    }
    @Test
    void testGetCustomerByIdentityNo_whenCustomerIdentityNoDoesNotExists_shouldThrowNotFoundException()
    {
        String IdentityNo ="IdentityNo";
        when(customerRepository.findByIdentityNo(IdentityNo)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,
                () -> customerService.getCustomerByIdentityNo(IdentityNo));


    }
}