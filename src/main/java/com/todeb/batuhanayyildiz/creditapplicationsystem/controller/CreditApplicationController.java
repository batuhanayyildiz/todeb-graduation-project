package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("v1/credit-application")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("create/by-customer-identity-number")
    public ResponseEntity createCreditApplicationByCustomerIdentityNo(
            @RequestParam(name="identity-number") String identityNo)
    {
        CreditApplicationDTO respCreditApplicationDTO=
                creditApplicationService.createApplicationByCustomerIdentityNo(identityNo);
        if (respCreditApplicationDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Credit Application could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCreditApplicationDTO);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("view-application-result")
    public ResponseEntity viewLastCreditApplicationByIdentityNo(@RequestParam(name="identity-number") String identityNo)
    {
        return ResponseEntity.ok(creditApplicationService.viewLastCreditApplicationResultByIdentityNo(identityNo));

    }
}
