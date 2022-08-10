package com.todeb.batuhanayyildiz.creditapplicationsystem.repository;

import com.todeb.batuhanayyildiz.creditapplicationsystem.model.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
}
