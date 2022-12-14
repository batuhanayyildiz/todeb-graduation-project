package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="application_date", updatable = false, nullable = false)
    private LocalDate creditApplicationDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "application_result")
    private CreditApplicationResult applicationResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private CreditApplicationStatus applicationStatus;






    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_limit_id", referencedColumnName = "id")
    private CreditLimit creditLimit;


    private int creditMultiplier;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public CreditApplication(Customer customer){
        this.customer=customer;
        this.applicationResult=CreditApplicationResult.WAITING;
        this.applicationStatus=CreditApplicationStatus.ACTIVE;
        this.creditMultiplier=4;
    }



}
