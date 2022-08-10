package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    @Column(name="application_date", updatable = false, nullable = false)
    private LocalDate creditApplicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private CreditApplicationStatus applicationStatus;



    @OneToOne(mappedBy = "creditApplication")
    private Customer customer;





}
