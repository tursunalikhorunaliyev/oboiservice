package com.dataspin.oboiservice.projection;

/**
 * Projection for {@link com.dataspin.oboiservice.entity.Warehouse}
 */
public interface WarehouseInfo {
    Long getId();

    Integer getIncomeQuantity();

    Integer getOutcomeQuantity();

    Integer getOnHand();

    Boolean getStatus();

    OboiCodeInfo getOboiCode();

    OboiPartyInfo getOboiParty();
}