package com.bogstepan.simple_bank.deal.model.entity;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "statements")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Statement {

    @Id
    @GeneratedValue
    @Column(name = "statement_id")
    @JdbcTypeCode(SqlTypes.UUID)
    UUID statementId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JdbcTypeCode(SqlTypes.UUID)
    Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    @JdbcTypeCode(SqlTypes.UUID)
    Credit credit;

    @Column(name = "status", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    ApplicationStatus status;

    @Column(name = "creation_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    LocalDateTime creationDate;


    @Column(name = "applied_offer")
    @JdbcTypeCode(SqlTypes.JSON)
    LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    LocalDateTime signDate;

    @Column(name = "ses_code")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String sesCode;

    @Column(name = "status_history")
    @JdbcTypeCode(SqlTypes.JSON)
    StatusHistory statusHistory;

}
