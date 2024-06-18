package com.bogstepan.simple_bank.deal.model.entity;

import com.bogstepan.simple_bank.deal.model.enums.Gender;
import com.bogstepan.simple_bank.deal.model.enums.MaritalStatus;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    @JdbcTypeCode(SqlTypes.UUID)
    UUID clientId;

    @Column(name = "first_name", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String firstName;

    @Column(name = "last_name", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String lastName;

    @Column(name = "middle_name", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String middleName;

    @Column(name = "birth_date")
    @JdbcTypeCode(SqlTypes.DATE)
    LocalDate birthDate;

    @Column(name = "email", unique = true, length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    @JdbcTypeCode(SqlTypes.INTEGER)
    Integer dependentAmount;

    @Column(name = "passport")
    @JdbcTypeCode(SqlTypes.JSON)
    Passport passport;

    @Column(name = "employment")
    @JdbcTypeCode(SqlTypes.JSON)
    Employment employment;

    @Column(name = "account_number", length = 20)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String accountNumber;
}
