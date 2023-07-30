package com.todeb.batuhanayyildiz.creditapplicationsystem.model.mapper;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto.CustomerDTO;
import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO toDto(Customer entity) ;


    Customer toEntity(CustomerDTO dto);

}
