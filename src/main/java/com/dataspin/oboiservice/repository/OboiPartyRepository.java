package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.OboiParty;
import com.dataspin.oboiservice.projection.OboiPartyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OboiPartyRepository extends JpaRepository<OboiParty, Long> {
    boolean existsByName(String name);

    Optional<OboiParty> findByName(String name);

    @Query("select o from oboi_party o")
    List<OboiPartyInfo> getAllInfo();
}