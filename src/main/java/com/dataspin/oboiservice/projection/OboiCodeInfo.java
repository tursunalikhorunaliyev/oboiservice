package com.dataspin.oboiservice.projection;

import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.OboiCode}
 */
public interface OboiCodeInfo {
    Long getId();

    String getName();

    LocalDateTime getTimestamp();
}