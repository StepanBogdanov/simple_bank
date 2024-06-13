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
    @Column(name = "credit_id")
    @JdbcTypeCode(SqlTypes.UUID)
    UUID creditId;

    @Column(name = "amount")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    BigDecimal amount;

    @Column(name = "term")
    @JdbcTypeCode(SqlTypes.INTEGER)
    Integer term;

    @Column(name = "monthly_payment")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    BigDecimal monthlyPayment;

    @Column(name = "rate")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    BigDecimal rate;

    @Column(name = "psk")
    @JdbcTypeCode(SqlTypes.DECIMAL)
    BigDecimal psk;

    @Column(name = "payment_schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    PaymentSchedule paymentSchedule;

    @Column(name = "insurance_enabled")
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    Boolean insuranceEnabled;

    @Column(name = "salary_client")
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    Boolean salaryClient;

    @Column(name = "credit_status", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    CreditStatus creditStatus;
}
