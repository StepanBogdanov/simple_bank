package com.bogstepan.simple_bank.deal.model.entity;

import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.json.AppliedOffer;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "statements")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statement {

    @Id
    @GeneratedValue
    UUID statementId;
    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;
    @OneToOne
    @JoinColumn(name = "credit_id")
    Credit credit;
    @Enumerated(EnumType.STRING)
    ApplicationStatus status;
    @JdbcTypeCode(SqlTypes.JSON)
    AppliedOffer appliedOffer;
    LocalDate signDate;
    Integer sesCode;
    @JdbcTypeCode(SqlTypes.JSON)
    StatusHistory statusHistory;

}
