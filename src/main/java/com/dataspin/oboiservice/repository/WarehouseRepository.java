package com.dataspin.oboiservice.repository;

import com.dataspin.oboiservice.entity.Warehouse;
import com.dataspin.oboiservice.projection.WarehouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByOboiCode_IdAndOboiParty_Id(Long codeId, Long partyId);

    boolean existsByOboiCode_IdAndOboiParty_Id(Long codeId, Long partyId);

    @Query("select w from warehouse w")
    List<WarehouseInfo> getAllInfo();

    @Query(value = "SELECT (income - (outcome + onhand)) as difference FROM (SELECT sum(income_quantity) as income, sum(on_hand) as onhand, sum(outcome_quantity) as outcome FROM oboiservice.warehouse) as F", nativeQuery = true)
    Integer checkDifference();
}