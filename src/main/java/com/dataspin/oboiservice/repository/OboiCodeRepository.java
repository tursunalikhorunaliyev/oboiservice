package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.OboiCode;
import com.dataspin.oboiservice.projection.OboiCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OboiCodeRepository extends JpaRepository<OboiCode, Long> {
    boolean existsByName(String name);

    Optional<OboiCode> findByName(String name);

    @Query("select o from oboi_code o")
    List<OboiCodeInfo> getAllInfo();
}