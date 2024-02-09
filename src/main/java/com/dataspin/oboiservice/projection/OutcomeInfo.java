package com.dataspin.oboiservice.projection;

import com.dataspin.oboiservice.entity.OutcomeType;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.Outcome}
 */
public interface OutcomeInfo {
    Long getId();

    Integer getQuantity();

    Timestamp getTimestamp();

    String getRegNo();

    OboiCodeInfo getOboiCode();

    OboiPartyInfo getOboiParty();

    OutcomeType getOutcomeType();
}