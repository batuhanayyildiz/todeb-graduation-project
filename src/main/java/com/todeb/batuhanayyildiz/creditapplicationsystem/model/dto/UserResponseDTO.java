package com.todeb.batuhanayyildiz.creditapplicationsystem.model.dto;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private List<Role> roles;

}
