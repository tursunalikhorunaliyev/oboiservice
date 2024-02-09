package com.dataspin.oboiservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oboicode_id", referencedColumnName = "id")
    private OboiCode oboiCode;

    @ManyToOne
    @JoinColumn(name = "oboiparty_id", referencedColumnName = "id")
    private OboiParty oboiParty;

    @Column(name = "income_quantity", nullable = false)
    private Integer incomeQuantity;

    @Column(name = "outcome_quantity", nullable = false)
    private Integer outcomeQuantity;

    @Column(name = "on_hand", nullable = false)
    private Integer onHand;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
