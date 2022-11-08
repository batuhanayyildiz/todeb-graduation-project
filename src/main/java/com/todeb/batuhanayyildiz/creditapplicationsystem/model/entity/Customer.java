package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
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

    //@Pattern(regexp="^[1-9]{1}[0-9]{9}[02468]{1}$",
            //message="Format error. Example Format :00100100101")
    @NotBlank(message = "identity number cannot be empty")
    @Column(name="identity_number",nullable = false)
    private String identityNo;

    @NotBlank(message = "name cannot be empty")
    @Column(name="name")
    private String name;
    @NotBlank(message = "surname cannot be empty")
    @Column(name="surname")
    private String surname;

    @NotNull(message = "monthly income cannot be empty")
    @Column(name="monthly_income")
    private int monthlyIncome;

    //@Pattern(regexp="^(0)[0-9]{10}$" ,
            //message = "Format error.Please write phone number as unified and starting with zero")
    @NotBlank(message = "phone number cannot be empty")
    @Column(name="phone_number")
    private String phoneNo;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_score_id",referencedColumnName = "id")
    private List<CreditScore> creditScores=new ArrayList<CreditScore>();

//
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CreditApplication> creditApplications=new ArrayList<CreditApplication>();;


}
