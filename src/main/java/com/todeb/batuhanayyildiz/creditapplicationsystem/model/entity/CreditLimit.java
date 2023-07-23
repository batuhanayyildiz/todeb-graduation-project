package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditLimit {


    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private double creditLimit;
    @OneToOne(mappedBy = "creditLimit",fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", referencedColumnName = "id")
    private  CreditApplication creditApplication;

    public CreditLimit(CreditApplication creditApplication, double creditLimit) {
        this.creditApplication=creditApplication;
        this.creditLimit=creditLimit;


    }
}
