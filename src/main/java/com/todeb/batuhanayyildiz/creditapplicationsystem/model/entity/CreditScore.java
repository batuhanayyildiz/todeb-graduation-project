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
public class CreditScore {

    // This entity takes its value from different service.
    // Therefore, creation of this value directly in another complex entity can cause problems related with dependencies.
    // To prevent that , it is created as seperated entity, not as a variable.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int creditScore;


    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

}
