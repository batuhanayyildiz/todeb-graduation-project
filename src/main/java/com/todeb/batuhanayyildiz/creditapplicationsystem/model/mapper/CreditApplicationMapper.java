package com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CreditApplicationDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import org.mapstruct.Mapper;

@Mapper
public interface CreditApplicationMapper {
    CreditApplicationDTO toDto(CreditApplication entity) ;

    CreditApplication toEntity(CreditApplicationDTO dto);
}
