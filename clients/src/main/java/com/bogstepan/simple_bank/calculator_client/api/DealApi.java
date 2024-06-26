package com.bogstepan.simple_bank.calculator_client.api;

import com.bogstepan.simple_bank.calculator_client.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.InvalidRequestDataDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "�� ������")
public interface DealApi {

    @Operation(summary = "������ ��������� ������� �������", description = """
            �� API �������� LoanStatementRequestDto
            �� ������ LoanStatementRequestDto �������� �������� Client � ����������� � ��.
            �������� Statement �� ������ �� ������ ��� ��������� Client � ����������� � ��.
            ������������ POST ������ �� /calculator/offers �� ����������� ����� FeignClient
            ������� �������� �� ������ List<LoanOfferDto> ������������� id ��������� ������ (Statement)
            ����� �� API - ������ �� 4� LoanOfferDto �� "�������" � "�������".
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "������ ������� ��������"),
            @ApiResponse(responseCode = "400", description = "������ ���������� �������", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "����� ������ �� �����������", description = """
            �� API �������� LoanOfferDto
            �������� �� �� ������(Statement) �� statementId �� LoanOfferDto.
            � ������ ����������� ������, ������� ��������(List<StatementStatusHistoryDto>), �������� ����������� LoanOfferDto ��������������� � ���� appliedOffer.
            ������ �����������.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "������ ������� ���������"),
            @ApiResponse(responseCode = "400", description = "������ ���������� ������", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/offer/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);

    @Operation(summary = "���������� �����������, ������ ������ �������", description = """
            �� API �������� ������ FinishRegistrationRequestDto � �������� statementId (String).
            �������� �� �� ������(Statement) �� statementId.
            ScoringDataDto ���������� ����������� �� FinishRegistrationRequestDto � Client, ������� �������� � Statement
            ������������ POST ������ �� /calculator/calc �� ����������� � ����� ScoringDataDto ����� FeignClient.
            �� ������ ����������� �� ���������� ��������� CreditDto �������� �������� Credit � ����������� � ���� �� �������� CALCULATED.
            � ������ ����������� ������, ������� ��������.
            ������ �����������.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "������ ������� ��������"),
            @ApiResponse(responseCode = "400", description = "������ ������� �������", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/calculate/{statementId}")
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);
}
