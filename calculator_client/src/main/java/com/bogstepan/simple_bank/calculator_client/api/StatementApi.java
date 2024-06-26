package com.bogstepan.simple_bank.calculator_client.api;

import com.bogstepan.simple_bank.calculator_client.dto.InvalidRequestDataDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
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

@Tag(name = "�� ������")
public interface StatementApi {

    @Operation(summary = "���������� � ������ �� ��������� ��������� ����������� �� �� Deal", description = """
            �� API �������� LoanStatementRequestDto
            �� ������ LoanStatementRequestDto ���������� ����������.
            ������������ POST-������ �� /deal/statement � �� deal ����� FeignClient.
            ����� �� API - ������ �� 4� LoanOfferDto �� "�������" � "�������".
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "���������� ������ �������, ��������� ����������� ��������"),
            @ApiResponse(responseCode = "400", description = "������ ��������� ������ �� �� Deal", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "����� ������ �� ��������� �����������", description = """
            �� API �������� LoanOfferDto
            ������������ POST-������ �� /deal/offer/select � �� deal ����� FeignClient.
            """)
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "��������� ��������� ����������� ���������� � �� Deal"))
    @PostMapping("/satement/offer")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
