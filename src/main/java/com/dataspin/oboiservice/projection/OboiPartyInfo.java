package com.dataspin.oboiservice.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.OboiParty}
 */
public interface OboiPartyInfo {
    Long getId();

    String getName();

    Timestamp getTimestamp();
}