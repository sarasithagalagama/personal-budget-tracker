package com.sarasitha.budgettracker.repository;

import com.sarasitha.budgettracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
} 