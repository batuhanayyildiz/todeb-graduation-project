package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CreditLimit {
    // It is created as seperated entity because when application expands, this area can be more complex
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int creditLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", referencedColumnName = "id")
    private  CreditApplication creditApplication;
}
