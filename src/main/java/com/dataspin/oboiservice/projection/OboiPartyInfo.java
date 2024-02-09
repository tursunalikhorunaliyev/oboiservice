package com.dataspin.oboiservice.projection;

import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.OboiParty}
 */
public interface OboiPartyInfo {
    Long getId();

    String getName();

    LocalDateTime getTimestamp();
}