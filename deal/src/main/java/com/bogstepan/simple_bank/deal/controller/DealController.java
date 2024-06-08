package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.deal.exception.ExceptionData;
import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
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

@Tag(name = "Deal Controller")
public interface DealController {

    @Operation(summary = "Receiving offers from MS Calculator", description = """
            LoanStatementRequestDto arrives via API.
            Based on LoanStatementRequestDto, a Client entity is created and saved in the DB.
            A Statement is created with a connection to the newly created Client and saved in the DB.
            A POST request is sent to /calculator/offers MS Calculator via FeignClient
            Each element from the List<LoanOfferDto> list is assigned the id of the created statement
            The response to the API is a list of 4 LoanOfferDto from “worst” to “best”.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entities are saved and the response is received"),
            @ApiResponse(responseCode = "400", description = "Error saving or retrieving data", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)) })
    })
    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Select loan offer", description = """
            LoanOfferDto comes via API
            The Statement is retrieved from the DB by statementId from LoanOfferDto.
            The status of the statement is updated, status history (List<StatementStatusHistoryDto>), the accepted
            offer LoanOfferDto is set in the appliedOffer field.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The statement successfully updated"),
            @ApiResponse(responseCode = "400", description = "Error updating data", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)) })
    })
    @PostMapping("/offer/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);

    @Operation(summary = "Credit calculation", description = """
            The API receives the FinishRegistrationRequestDto object and the statementId (String) parameter.
            The Statement is retrieved from the DB by statementId.
            ScoringDataDto is saturated with information from FinishRegistrationRequestDto and Client, which is stored
            in Statement.
            A POST request is sent to /calculator/calc MS Calculator with the body ScoringDataDto via FeignClient.
            Based on the CreditDto received from the MS Calculator, the Credit entity is created and saved in the DB
            with the CALCULATED status.
            The statement is updated with status and status history.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The response is received and all entities saved or updated"),
            @ApiResponse(responseCode = "400", description = "Error saving or retrieving data", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)) })
    })
    @PostMapping("/calculate/{statementId}")
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable String statementId);
}
