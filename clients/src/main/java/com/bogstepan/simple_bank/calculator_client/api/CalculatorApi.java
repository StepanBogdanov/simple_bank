package com.bogstepan.simple_bank.calculator_client.api;

import com.bogstepan.simple_bank.calculator_client.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "�� �����������")
public interface CalculatorApi {

    @Operation(summary = "������ ��������� �����������", description = """
            �� API �������� LoanStatementRequestDto.
            �� ��������� LoanStatementRequestDto �������� 4 ��������� ����������� LoanOfferDto
            �� ��������� ���� ��������� ���������� ��������� ����� isInsuranceEnabled � isSalaryClient
            (false-false, false-true, true-false, true-true)
            ����� �� API - ������ �� 4� LoanOfferDto �� "�������" � "�������" (��� ������ �������� ������, ��� �����).
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "��������� ����������� ������� ���������"),
            @ApiResponse(responseCode = "400", description = "������ ������� ��������� �����������", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/calculator/offers")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto);

    @Operation(summary = "������ ������ �������", description = """
            �� API �������� ScoringDataDto.
            ���������� ������� ������, ������������ �������� ������(rate), ������ ��������� �������(psk),
            ������ ������������ �������(monthlyPayment), ������ ����������� �������� (List<PaymentScheduleElementDto>).
            ����� �� API - CreditDto, ���������� ����� ������������� �����������.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "������ ������� ��������"),
            @ApiResponse(responseCode = "400", description = "������ ������� �������", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/calculator/calc")
    ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
