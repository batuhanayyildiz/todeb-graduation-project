package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="credit_score")
public class CreditScore {

    // This entity takes its value from different service.
    // Therefore, creation of this value as a variable directly in another complex entity can cause problems related with dependencies.
    // To prevent that , it is created as seperated entity, not as a variable.
    // Also, by doing it, an application ,which is more suitable for expansion, is aimed.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="score")
    private int score;



    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public CreditScore(Customer customer,int score) {
        this.customer=customer;
        this.score=score;

    }
}
