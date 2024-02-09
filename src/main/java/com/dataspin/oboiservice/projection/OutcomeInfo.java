package com.dataspin.oboiservice.projection;

import com.dataspin.oboiservice.entity.OutcomeType;

import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.Outcome}
 */
public interface OutcomeInfo {
    Long getId();

    Integer getQuantity();

    LocalDateTime getTimestamp();

    String getRegNo();

    OboiCodeInfo getOboiCode();

    OboiPartyInfo getOboiParty();

    OutcomeType getOutcomeType();
}