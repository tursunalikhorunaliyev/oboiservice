package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.Outcome;
import com.dataspin.oboiservice.projection.OutcomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
    @Query("select o from outcome o")
    List<OutcomeInfo> getAllOutcome();

    @Query("select o from outcome o where o.timestamp between ?1 and ?2")
    List<OutcomeInfo> findByTimestampBetween(Timestamp timestampStart, Timestamp timestampEnd);

}