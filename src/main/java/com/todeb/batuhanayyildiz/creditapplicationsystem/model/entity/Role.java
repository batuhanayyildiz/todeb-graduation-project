package com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CUSTOMER;

    public String getAuthority() {
        return name();
    }
}
