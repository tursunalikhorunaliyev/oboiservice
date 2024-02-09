package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.Income;
import com.dataspin.oboiservice.projection.IncomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("select i from income i")
    List<IncomeInfo> getAllIncome();

}