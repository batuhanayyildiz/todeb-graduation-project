package com.todeb.batuhanayyildiz.creditapplicationsystem.repository;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication,Long> {
}
