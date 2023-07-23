package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="credit_score")
public class CreditScore {

    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    @Column(name="score")
    private int score;



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public CreditScore(Customer customer,int score) {
        this.customer=customer;
        this.score=score;

    }
}
