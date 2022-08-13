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

    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getCreditApplicationById(@PathVariable("id") Long id){
        CreditApplication byId = creditApplicationService.getCreditApplicationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CREDIT_APPLICATION_MAPPER.toDto(byId));
    }
    /*    @PutMapping("/create/by-identity-number/{identityNo}")
    public ResponseEntity createApplicationByCustomerIdentityNo(@PathVariable("identityNo") String identityNo){
        customerService.addCreditApplicationToCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body("Credit Application was created for related Customer successfully");
    }
*/
    @PutMapping("/determine/result/by-identity-number/{identityNo}")
    public ResponseEntity determineApplicationResultByCustomerIdentityNo(@PathVariable("identityNo") String identityNo){

        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customerService.getCustomerByIdentityNo(identityNo));
        creditApplicationService.addCreditLimitToCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo));
        creditApplicationService.updateCreditLimitOfCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo));

        return ResponseEntity.status(HttpStatus.OK).body("Credit Application was created for related Customer successfully");
    }
    @GetMapping("/view/application-result/{identityNo}")
    public ResponseEntity viewApplicationResultByCustomerIdentityNo(@PathVariable("identityNo") String identityNo){

        return ResponseEntity.status(HttpStatus.OK).body(creditApplicationService
                .viewCreditApplicationResultByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }



    /*
    @GetMapping("/get-by-identity-number/{identityNo}")
    public ResponseEntity getCreditApplicationByCustomerIdentityNo(@PathVariable("identityNo") String identityNo){
        CreditApplication byId = creditApplicationService.getLastCreditApplicationByCustomerIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body(CREDIT_APPLICATION_MAPPER.toDto(byId));
    }
*/













    // For testing corresponding service methods with controller
    @PutMapping("/determine/last-application-status/by-identity-number/{identityNo}")
    public ResponseEntity determineLastCreditApplicationStatusByCustomerIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Application Status was determined successfully");
    }
    @PutMapping("/determine/credit-limit/by-identity-number/{identityNo}")
    public ResponseEntity updateCreditLimitOfCreditApplicationByCustomer(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.updateCreditLimitOfCreditApplicationByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Limit was determined for related Customer successfully");
    }

    @PutMapping("/add/credit-limit/by-identity-number/{identityNo}")
    public ResponseEntity addCreditApplicationToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.addCreditLimitToCreditApplicationByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Limit was added to related Customer successfully");
    }







}
