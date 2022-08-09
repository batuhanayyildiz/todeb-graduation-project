package com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
public class CustomerDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    @Pattern(regexp="^[1-9]{1}[0-9]{9}[02468]{1}$",
            message="Format error. Example Format :00100100101")
    @NotBlank
    @Column(name="identity_number")
    private String identityNo;

    @NotNull
    private int monthlyIncome;

    @Pattern(regexp="^(0)[0-9]{10}$" ,
            message = "Format error.Please write phone number as unified and starting with zero")
    @NotBlank
    @Column(name="phone_number")
    private String PhoneNo;
}
