package com.bogstepan.simple_bank.deal.model.entity;

import com.bogstepan.simple_bank.deal.model.enums.CreditStatus;
import com.bogstepan.simple_bank.deal.model.json.PaymentSchedule;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Credit {

    @Id
    @GeneratedValue
    UUID creditId;
    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    @JdbcTypeCode(SqlTypes.JSON)
    PaymentSchedule paymentSchedule;
    Boolean insuranceEnabled;
    Boolean salaryClient;
    @Enumerated(EnumType.STRING)
    CreditStatus creditStatus;
}
