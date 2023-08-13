package com.todeb.batuhanayyildiz.creditapplicationsystem;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationResult;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TestSupport
{
    public Instant getCurrentInstant()
    {
        String instantExpected = "2023-02-15T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), Clock.systemDefaultZone().getZone());

        return Instant.now(clock);
    }

    public LocalDateTime getLocalDateTime()
    {
        return LocalDateTime.ofInstant(getCurrentInstant(), Clock.systemDefaultZone().getZone());
    }

    public LocalDateTime getDifferentLocalDateTime()
    {
        String instantExpected = "2023-02-15T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), Clock.systemDefaultZone().getZone());

        return LocalDateTime.ofInstant(Instant.now(clock), Clock.systemDefaultZone().getZone());
    }
    public CreditApplication generateCreditApplication(Customer customer)
    {

        CreditApplication creditApplication=new CreditApplication("creditApplicationId",getLocalDateTime()
                , CreditApplicationResult.WAITING,null,4,customer);

        return  creditApplication;
    }
    public CreditApplicationDTO generateCreditApplicationDTO(CreditApplication creditApplication) {
        CreditApplicationDTO creditApplicationDTO =new CreditApplicationDTO();

        creditApplicationDTO.setCreditApplicationDate(creditApplication.getCreditApplicationDate());
        creditApplicationDTO.setCreditLimit(creditApplication.getCreditLimit());
        creditApplicationDTO.setApplicationResult(creditApplication.getApplicationResult());

        return creditApplicationDTO;
    }

    public Customer generateCustomer() {

        return new Customer("customerId","identityNo", "customerName", "customerSurname"
                ,50,"12345678",new HashSet<>(),new HashSet<>());
    }

    public CustomerDTO generateCustomerDTO(Customer customer) {
        CustomerDTO customerDTO =new CustomerDTO();

        customerDTO.setIdentityNo(customer.getIdentityNo());
        customerDTO.setName(customer.getName());
        customerDTO.setSurname(customer.getSurname());
        customerDTO.setMonthlyIncome(customer.getMonthlyIncome());
        customerDTO.setPhoneNo(customer.getPhoneNo());

        return customerDTO;
    }
}
