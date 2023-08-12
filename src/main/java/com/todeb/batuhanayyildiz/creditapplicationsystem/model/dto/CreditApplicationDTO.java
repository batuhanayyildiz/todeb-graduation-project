package com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.enums.CreditApplicationResult;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreditApplicationDTO {
    @CreationTimestamp
    @JsonFormat( pattern = "dd-MM-yyyy" )
    private LocalDateTime creditApplicationDate;

    @Enumerated(EnumType.STRING)
    private CreditApplicationResult applicationResult;

    private CreditLimit creditLimit;


}
