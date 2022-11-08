package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditLimit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double creditLimit;
    @OneToOne(mappedBy = "creditLimit")
    private  CreditApplication creditApplication;

    public CreditLimit(CreditApplication creditApplication, double creditLimit) {
        this.creditApplication=creditApplication;
        this.creditLimit=creditLimit;


    }
}
