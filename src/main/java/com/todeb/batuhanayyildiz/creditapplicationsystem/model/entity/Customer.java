package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp="^[1-9]{1}[0-9]{9}[02468]{1}$",
            message="Format error. Example Format :00100100101")
    @NotBlank
    @Column(name="identity_number")
    private String identityNo;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private int monthlyIncome;

    @Pattern(regexp="^(0)[0-9]{10}$" ,
            message = "Format error.Please write phone number as unified and starting with zero")
    @NotBlank
    @Column(name="phone_number")
    private String PhoneNo;


    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name="credit_score_id")
    private CreditScore creditScore;

    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name="credit_application_id")
    private CreditApplication creditApplication;
}
