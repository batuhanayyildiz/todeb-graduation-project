package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CreditLimit {
    // It is created as seperated entity because when application expands, this area can be more complex
    @Id
    private int id;

    private int creditLimit;

    @OneToOne(mappedBy = "creditLimit")
    private  CreditApplication creditApplication;
}
