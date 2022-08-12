package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CustomerMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CreditApplicationServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CustomerServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
public class CreditApplicationController {

    @Autowired
    private CreditApplicationServiceImpl creditApplicationService;

    @Autowired
    private CustomerServiceImpl customerService;
    private static final CreditApplicationMapper CREDIT_APPLICATION_MAPPER = Mappers.getMapper(CreditApplicationMapper.class);

    @GetMapping
    public String welcome() {
        return "Welcome to Credit Application Service!";
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getCreditApplicationById(@PathVariable("id") Long id){
        CreditApplication byId = creditApplicationService.getCreditApplicationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CREDIT_APPLICATION_MAPPER.toDto(byId));
    }
    /*
    @GetMapping("/get-by-identity-number/{identityNo}")
    public ResponseEntity getCreditApplicationByCustomerIdentityNo(@PathVariable("identityNo") String identityNo){
        CreditApplication byId = creditApplicationService.getLastCreditApplicationByCustomerIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body(CREDIT_APPLICATION_MAPPER.toDto(byId));
    }
*/
    @PutMapping("/determine-last-application-status/by-identity-number/{identityNo}")
    public ResponseEntity determineLastCreditApplicationStatusByCustomerIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Application Status was determined successfully");
    }





}
