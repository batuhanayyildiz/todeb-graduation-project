package com.todeb.batuhanayyildiz.creditapplicationsystem.repository;


import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdentityNo(String identityNo);
    boolean existsByIdentityNo(String identityNo);
}
