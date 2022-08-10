package com.todeb.batuhanayyildiz.creditapplicationsystem.repository;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditLimitRepository extends JpaRepository<CreditLimit, Long> {
    Optional<CreditLimit> findBy
}
