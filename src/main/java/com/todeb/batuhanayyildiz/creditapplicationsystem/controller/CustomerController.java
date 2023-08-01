package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO respCustomerDTO = customerService.createCustomer(customerDTO);
        if (respCustomerDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping()
    public ResponseEntity deleteCustomer(@RequestParam(name = "id") String id) {
        customerService.deleteCustomerByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer deleted successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable String id, @RequestBody()CustomerDTO customerDTO){
        CustomerDTO updatedCustomer=customerService.updateCustomerByID(id,customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be updated successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

}
