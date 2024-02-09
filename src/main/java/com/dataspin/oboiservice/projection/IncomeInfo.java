package com.dataspin.oboiservice.projection;

import com.dataspin.oboiservice.entity.IncomeType;

import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.Income}
 */
public interface IncomeInfo {
    Long getId();

    Integer getQuantity();

    LocalDateTime getTimestamp();

    String getRegNo();

    OboiCodeInfo getOboiCode();

    OboiPartyInfo getOboiParty();

    IncomeType getIncomeType();
}