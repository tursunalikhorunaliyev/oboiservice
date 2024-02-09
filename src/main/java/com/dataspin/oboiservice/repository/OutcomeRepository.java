package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.Outcome;
import com.dataspin.oboiservice.projection.OutcomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
    @Query("select o from outcome o")
    List<OutcomeInfo> getAllOutcome();
}