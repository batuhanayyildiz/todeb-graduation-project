package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Pattern(regexp="^[1-9]{1}[0-9]{9}[02468]{1}$",
            message="Format error. Example Format :00100100101")
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


    @NotBlank(message = "phone number cannot be empty")
    @Column(name="phone_number")
    private String phoneNo;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_score_id",referencedColumnName = "id")
    private Set<CreditScore> creditScores=new HashSet<CreditScore>();

//
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<CreditApplication> creditApplications=new HashSet<CreditApplication>();;


}
