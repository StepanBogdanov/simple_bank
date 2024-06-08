package com.bogstepan.simple_bank.deal.model.dto;

import com.bogstepan.simple_bank.deal.model.enums.Gender;
import com.bogstepan.simple_bank.deal.model.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
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
