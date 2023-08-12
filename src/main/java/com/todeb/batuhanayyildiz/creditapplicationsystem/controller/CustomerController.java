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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customerDTO)
    {
        CustomerDTO respCustomerDTO = customerService.createCustomer(customerDTO);
        if (respCustomerDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping()
    public ResponseEntity deleteCustomer(@RequestParam(name = "id") String id)
    {
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer deleted successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable String id, @RequestBody()CustomerDTO customerDTO)
    {
        CustomerDTO updatedCustomer=customerService.updateCustomerById(id,customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be updated successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
    }

}
