package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="credit_limit")
public class CreditLimit {


    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id="";

    private double creditLimit;

    @OneToOne(mappedBy = "creditLimit",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_application_id", referencedColumnName = "id")
    private  CreditApplication creditApplication;

    public CreditLimit(double creditLimit){
        this.creditLimit=creditLimit;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditLimit that = (CreditLimit) o;
        return Double.compare(that.creditLimit, creditLimit) == 0 && id.equals(that.id) && creditApplication.equals(that.creditApplication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditLimit, creditApplication);
    }

/*
    public CreditLimit(CreditApplication creditApplication, double creditLimit) {
        this.creditApplication=creditApplication;
        this.creditLimit=creditLimit;


    }
    */

}
