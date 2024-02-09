package com.dataspin.oboiservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oboicode_id", referencedColumnName = "id", nullable = false)
    private OboiCode oboiCode;

    @ManyToOne
    @JoinColumn(name = "oboiparty_id", referencedColumnName = "id", nullable = false)
    private OboiParty oboiParty;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @CreationTimestamp()
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "reg_no", nullable = false)
    private String regNo;

    @ManyToOne
    @JoinColumn(name = "income_type_id", referencedColumnName = "id", nullable = false)
    private IncomeType incomeType;
}
