package com.dataspin.oboiservice.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.OboiCode}
 */
public interface OboiCodeInfo {
    Long getId();

    String getName();

    Timestamp getTimestamp();
}