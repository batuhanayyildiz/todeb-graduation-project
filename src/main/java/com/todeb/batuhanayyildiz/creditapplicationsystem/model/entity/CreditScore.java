package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String id="";

    @CreationTimestamp
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss" )
    @Column(name="credit_score_calculation_date", updatable = false, nullable = false)
    private LocalDateTime creditScoreCalculationDate;
    @Column(name="score")
    private int score;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;


    public CreditScore(LocalDateTime creditScoreCalculationDate,int score){
        this.creditScoreCalculationDate=creditScoreCalculationDate;
        this.score=score;
    }

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


