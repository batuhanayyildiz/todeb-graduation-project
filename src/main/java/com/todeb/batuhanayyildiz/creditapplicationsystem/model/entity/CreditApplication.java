package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditApplication {
    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="application_date", updatable = false, nullable = false)
    private LocalDate creditApplicationDate;

    @Column(name = "application_status",columnDefinition = "varchar(32) default WAITING")
    @Enumerated(EnumType.STRING)
    private CreditApplicationStatus applicationStatus=CreditApplicationStatus.WAITING;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_limit_id", referencedColumnName = "id")
    private CreditLimit creditLimit;

    @Column(name = "credit_multiplier",columnDefinition = "integer default 4")
    private int creditMultiplier;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditApplication that = (CreditApplication) o;
        return creditMultiplier == that.creditMultiplier && id.equals(that.id)
                && creditApplicationDate.equals(that.creditApplicationDate)
                && applicationStatus == that.applicationStatus
                && creditLimit.equals(that.creditLimit) && customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditApplicationDate, applicationStatus, creditLimit, creditMultiplier, customer);
    }
}


/*

public CreditApplication (Customer customer, ) {
        this.creditApplication=creditApplication;
        this.creditLimit=creditLimit;


    }
*/