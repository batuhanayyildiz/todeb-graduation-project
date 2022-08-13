package com.todeb.batuhanayyildiz.creditapplicationsystem.controller;


import com.todeb.batuhanayyildiz.creditapplicationsystem.model.response.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class WelcomeController {

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @GetMapping("/welcome")
    public ResponseEntity welcomeMessageApi(){
       String welcomeMsg= "Welcome to Library Automation Application";
       ResponseModel responseModel= new ResponseModel();
       responseModel.setWelcomeMessage((welcomeMsg));
       return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }
}
