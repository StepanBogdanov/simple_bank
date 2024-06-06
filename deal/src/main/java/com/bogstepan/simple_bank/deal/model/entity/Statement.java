package com.bogstepan.simple_bank.deal.model.entity;

import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
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
    LoanOfferDto appliedOffer;
    LocalDate signDate;
    String sesCode;
    @JdbcTypeCode(SqlTypes.JSON)
    StatusHistory statusHistory;

}
