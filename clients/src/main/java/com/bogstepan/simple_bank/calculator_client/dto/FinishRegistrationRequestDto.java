package com.bogstepan.simple_bank.calculator_client.dto;

import com.bogstepan.simple_bank.calculator_client.enums.Gender;
import com.bogstepan.simple_bank.calculator_client.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FinishRegistrationRequestDto {

    @Schema(example = "MALE")
    Gender gender;
    @Schema(example = "MARRIED")
    MaritalStatus maritalStatus;
    @Schema(example = "1")
    Integer dependentAmount;
    @Schema(format = "yyyy-mm-dd", example = "1995-06-06")
    LocalDate passportIssueDate;
    @Schema(example = "UFMS")
    String passportIssueBranch;
    @Schema(example = """
            {
                "employmentStatus": "SELF_EMPLOYED",
                "employerINN": "string",
                "salary": "45000",
                "position": "TOP_MANAGER",
                "workExperienceTotal": "24",
                "workExperienceCurrent": "12"
            }
            """)
    EmploymentDto employment;
    @Schema(example = "1231231234321")
    String accountNumber;
}
