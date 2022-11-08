package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CreditApplicationMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditApplicationService;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CustomerService;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.SmsService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/api/application")
public class CreditApplicationController {


    private final CreditApplicationService creditApplicationService;


    private final CustomerService customerService;

    private final SmsService smsService;

    private static final CreditApplicationMapper CREDIT_APPLICATION_MAPPER = Mappers.getMapper(CreditApplicationMapper.class);

    public CreditApplicationController(CreditApplicationService creditApplicationService, CustomerService customerService, SmsService smsService) {
        this.creditApplicationService = creditApplicationService;
        this.customerService = customerService;
        this.smsService = smsService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @GetMapping
    public String welcome() {
        return "Welcome to Credit Application Service!";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @PutMapping("/apply/credit-application/by-identity-number/{identityNo}")
    public ResponseEntity applyCreditApplicationToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.addCreditApplicationToCustomerByCustomer(customer);
        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customer);
        creditApplicationService.addCreditLimitToCreditApplicationByCustomer(customer);
        creditApplicationService.updateCreditLimitOfCreditApplicationByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).
                body("Related Credit Application was created for related Customer successfully"
                        + " and " + smsService.sendNotificationByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getCreditApplicationById(@PathVariable("id") Long id) {
        CreditApplication byId = creditApplicationService.getCreditApplicationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CREDIT_APPLICATION_MAPPER.toDto(byId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/by-identity-number/{identityNo}")
    public ResponseEntity getLastApplicationResultByCustomerIdentityNo(@PathVariable("identityNo") String identityNo) {
        return ResponseEntity.status(HttpStatus.OK).body(creditApplicationService
                .getLastCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/by-identity-number/all/{identityNo}")
    public ResponseEntity getAllApplicationResultsOfCustomerByIdentityNo(@PathVariable("identityNo") String identityNo) {
        return ResponseEntity.status(HttpStatus.OK).body(creditApplicationService
                .getLastCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/determine/result/by-identity-number/{identityNo}")
    public ResponseEntity determineApplicationResultByCustomerIdentityNo(@PathVariable("identityNo") String identityNo) {

        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customerService.getCustomerByIdentityNo(identityNo));
        creditApplicationService.addCreditLimitToCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo));
        creditApplicationService.updateCreditLimitOfCreditApplicationByCustomer(customerService.getCustomerByIdentityNo(identityNo));

        return ResponseEntity.status(HttpStatus.OK).body("Credit application was determined successfully for related Customer");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @GetMapping("/view/application-result/{identityNo}")
    public ResponseEntity viewApplicationResultByCustomerIdentityNo(@PathVariable("identityNo") String identityNo) {
        return ResponseEntity.status(HttpStatus.OK).body(creditApplicationService
                .viewCreditApplicationResultByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/credit-limit/by-identity-number/{identityNo}")
    public ResponseEntity updateCreditLimitOfCreditApplicationByCustomer(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.updateCreditLimitOfCreditApplicationByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Limit was updated for related Customer successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/credit-application/by-identity-number/{identityNo}")
    public ResponseEntity addCreditApplicationToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.addCreditApplicationToCustomerByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Limit was added to related Customer successfully");
    }



    // For testing corresponding service methods with controller.

    /*
    @PutMapping("/determine/last-application-status/by-identity-number/{identityNo}")
    public ResponseEntity determineLastCreditApplicationStatusByCustomerIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.determineLastCreditApplicationStatusByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Application Status was determined successfully");
    }
    @PostMapping("/add/credit-limit/by-identity-number/{identityNo}")
    public ResponseEntity addCreditLimitToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        Customer customer = customerService.getCustomerByIdentityNo(identityNo);
        creditApplicationService.addCreditLimitToCreditApplicationByCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Limit was added to related Customer successfully");
    }
    */

}








