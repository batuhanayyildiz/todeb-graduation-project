package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class CreditScore {

    // This entity takes its value from different service.
    // Therefore, creation of this value directly in another complex entity can cause problems related with dependencies.
    // To prevent that , it is created as seperated entity, not as a variable.

    @Id
    int id;
    private int creditScore;

    @OneToOne(mappedBy = "creditScore")
    private Customer customer;

}
