package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="application_date", updatable = false, nullable = false)
    private LocalDate creditScoreCalculationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditScore that = (CreditScore) o;
        return score == that.score && id.equals(that.id) && creditScoreCalculationDate.equals(that.creditScoreCalculationDate) && customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, creditScoreCalculationDate, customer);
    }
}
    /*
    public CreditScore(Customer customer,int score) {
        this.customer=customer;
        this.score=score;

    }
    */

}
