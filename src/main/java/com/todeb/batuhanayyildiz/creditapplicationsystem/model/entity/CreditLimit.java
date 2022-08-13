package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CreditLimit {

    // an application ,which is more suitable for expansion, is aimed.
    // It is created as seperated entity because if application expands, there is possibility for being more complex for Credit Limit.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double creditLimit;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", referencedColumnName = "id")
    private  CreditApplication creditApplication;

    public CreditLimit(CreditApplication creditApplication, double creditLimit) {
        this.creditApplication=creditApplication;
        this.creditLimit=creditLimit;


    }
}
