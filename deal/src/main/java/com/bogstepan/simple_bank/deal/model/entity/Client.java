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
    UUID clientId;
    String firstName;
    String lastName;
    String middleName;
    LocalDate birthDate;
    @Column(unique = true)
    String email;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    @JdbcTypeCode(SqlTypes.JSON)
    Passport passport;
    @JdbcTypeCode(SqlTypes.JSON)
    Employment employment;
    String accountNumber;
}
