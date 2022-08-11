package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

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
    private String phoneNo;


    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="credit_score_id")
    private CreditScore creditScore;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;
}
