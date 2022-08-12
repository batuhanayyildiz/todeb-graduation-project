package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper.CustomerMapper;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CreditApplicationServiceImpl;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.impl.CustomerServiceImpl;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    private static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);


    @GetMapping
    public String welcome() {
        return "Welcome to Customer Service!";
    }

    @GetMapping("/all")
    public ResponseEntity getAllCustomers(){
        List<Customer> allCustomers= customerService.getAllCustomers();
        List<CustomerDTO> allCustomersDTO=allCustomers.stream().map(CUSTOMER_MAPPER::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(allCustomersDTO);
    }

    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") Long id){
        Customer byId = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CUSTOMER_MAPPER.toDto(byId));
    }
    @GetMapping("/get/by-identity-number/{identityNo}")
    public ResponseEntity getCustomerByIdentityNo(@PathVariable("identityNo") String identityNo){
        Customer byId = customerService.getCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body(CUSTOMER_MAPPER.toDto(byId));
    }

    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {

        Customer respCustomer = customerService.createCustomer(CUSTOMER_MAPPER.toEntity(customerDTO));

        if (respCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomer);
    }

    @DeleteMapping("/delete/by-id/")
    public ResponseEntity deleteCustomerById(@RequestParam(name="id") Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer is deleted successfully");
    }

    @DeleteMapping("/delete/by-identity-number/")
    public ResponseEntity deleteCustomerById(@RequestParam(name="identityNo") String identityNo){
        customerService.deleteCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer is deleted successfully");
    }

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













    // For testing corresponding service methods with controller
    @PutMapping("/add/credit-score/by-identity-number/{identityNo}")
    public ResponseEntity addCreditScoreToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        customerService.addCreditScoreToCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Score was added to related Customer successfully");
    }

    @PutMapping("/add/credit-application/by-identity-number/{identityNo}")
    public ResponseEntity addCreditApplicationToCustomerByIdentityNo(
            @PathVariable String identityNo) {
        customerService.addCreditApplicationToCustomerByIdentityNo(identityNo);
        return ResponseEntity.status(HttpStatus.OK).body("Related Credit Application was added to related Customer successfully");
    }


}
