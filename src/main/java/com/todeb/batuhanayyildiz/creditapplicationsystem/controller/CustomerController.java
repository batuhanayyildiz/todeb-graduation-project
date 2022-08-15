package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CustomerMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CreditApplicationServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CreditScoreServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CustomerServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.SmsServiceImpl;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private CreditScoreServiceImpl creditScoreService;
    @Autowired
    private SmsServiceImpl smsService;

    private static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @GetMapping
    public String welcome() {
        return "Welcome to Customer Service!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity getAllCustomers(){
        List<Customer> allCustomers= customerService.getAllCustomers();
        List<CustomerDTO> allCustomersDTO=allCustomers.stream().map(CUSTOMER_MAPPER::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(allCustomersDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") Long id){
        Customer byId = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CUSTOMER_MAPPER.toDto(byId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/by-identity-number/{identityNo}")
    public ResponseEntity getCustomerByIdentityNo(@PathVariable("identityNo") String identityNo){
        Customer byId = customerService.getCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body(CUSTOMER_MAPPER.toDto(byId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer respCustomer = customerService.createCustomer(CUSTOMER_MAPPER.toEntity(customerDTO));
        creditScoreService.addCreditScoreToCustomerByCustomer(respCustomer);

        if (respCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/credit-score/{identityNo}")
    public ResponseEntity addNewCreditScoreToCustomer(@PathVariable String identityNo) {
        creditScoreService.addCreditScoreToCustomerByCustomer(customerService.getCustomerByIdentityNo(identityNo));
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Score was added to related Customer successfully");


    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/by-id/")
    public ResponseEntity deleteCustomerById(@RequestParam(name="id") Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer is deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/by-identity-number/")
    public ResponseEntity deleteCustomerById(@RequestParam(name="identityNo") String identityNo){
        customerService.deleteCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer is deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/by-identity-number/{identityNo}")
    public ResponseEntity updateCustomerByIdentityNo(@Valid
            @PathVariable String identityNo,
            @RequestBody CustomerDTO customerDTO){
        Customer update =customerService.updateCustomerByIdentityNo(identityNo,CUSTOMER_MAPPER.toEntity(customerDTO));
        if(update ==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer could not be updated successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(update);

    }









/*

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @PutMapping("/apply/credit-application/by-identity-number/{identityNo}")
    public ResponseEntity applyCreditApplicationToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        customerService.addCreditApplicationToCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).
                body("Related Credit Application was created for related Customer successfully"
                                +" and "+smsService.sendNotificationByCustomer(customerService.getCustomerByIdentityNo(identityNo)));
    }
*/

}
