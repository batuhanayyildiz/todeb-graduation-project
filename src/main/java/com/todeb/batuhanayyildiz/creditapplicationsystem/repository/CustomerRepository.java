package com.todeb.batuhanayyildiz.creditapplicationsystem.repository;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
