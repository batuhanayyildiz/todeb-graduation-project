package com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplicationStatus;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Data
public class CreditApplicationDTO {
    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    private LocalDate creditApplicationDate;

    @Enumerated(EnumType.STRING)
    private CreditApplicationStatus applicationStatus;

    private CreditLimit creditLimit;

    private Customer customer;
}
