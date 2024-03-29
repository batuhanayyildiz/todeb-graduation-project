package com.todeb.batuhanayyildiz.creditapplicationsystem.service;


import com.todeb.batuhanayyildiz.creditapplicationsystem.exception.NotFoundException;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CustomerMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CreditScoreService creditScoreService;
    private static final CustomerMapper CUSTOMER_MAPPER= Mappers.getMapper(CustomerMapper.class);

    private final Clock clock;


    public CustomerDTO createCustomer(CustomerDTO customerDTO){
        Customer customer= CUSTOMER_MAPPER.toEntity(customerDTO);
        CreditScore creditScore = new CreditScore(customer,getLocalDateTimeNow(),creditScoreService.creditScoreCalculation());
        customer.getCreditScore().add(creditScore);
        return CUSTOMER_MAPPER.toDto(customerRepository.save(customer));
    }
    protected Customer findCustomerById(String id) {
        log.info("Business logic of getCustomerById starts");
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(()->{
            log.error("Customer is not found by id "+id);
            return new NotFoundException("Customer");});
    }
    public CustomerDTO getCustomerById(String id) {

        return CUSTOMER_MAPPER.toDto(findCustomerById(id));
    }

    public void deleteCustomerById(String id) {
        findCustomerById(id);
        customerRepository.deleteById(id);
    }
    public CustomerDTO updateCustomerById(String id, CustomerDTO customerDTO)
    {
        log.info("Business logic of updateCustomerByIdentityNo starts");
        Customer updatedCustomer=findCustomerById(id);

        if (!ObjectUtils.isEmpty(customerDTO.getName())){
            updatedCustomer.setName(customerDTO.getName());}

        if (!ObjectUtils.isEmpty(customerDTO.getSurname())){
            updatedCustomer.setSurname(customerDTO.getSurname());}

        if (!ObjectUtils.isEmpty(customerDTO.getPhoneNo())){
            updatedCustomer.setPhoneNo(customerDTO.getPhoneNo());}

        if (!ObjectUtils.isEmpty(customerDTO.getMonthlyIncome())){
            updatedCustomer.setMonthlyIncome(customerDTO.getMonthlyIncome());}

        return CUSTOMER_MAPPER.toDto(customerRepository.save(updatedCustomer));
    }

    protected Customer findCustomerByIdentityNo(String identityNo)
    {
        log.info("Business logic of getCustomerByIdentityNo starts");
        Optional<Customer> customerByIdentityNo = customerRepository.findByIdentityNo(identityNo);
        return customerByIdentityNo.orElseThrow(()->{
            log.error("Customer is not found by identity number: "+identityNo);
            return new NotFoundException("Customer");});
    }

    public CustomerDTO getCustomerByIdentityNo (String identityNo)
    {
        return CUSTOMER_MAPPER.toDto(findCustomerByIdentityNo(identityNo));
    }
    private LocalDateTime getLocalDateTimeNow ()
    {
        Instant instant = clock.instant();
        return LocalDateTime.ofInstant(
                instant,
                Clock.systemDefaultZone().getZone());
    }
    }







