package io.pismo.web.rest.controller;

import io.pismo.service.TransactionService;
import io.pismo.service.dto.CreateTransactionRequestDto;
import io.pismo.service.dto.CreateTransactionResponseDto;
import io.pismo.service.dto.TransactionDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags="Customer transaction API.")
@RequestMapping("v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping
    @ApiOperation(value = "Create new transactions.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Customer account or Operation type not exists."),@ApiResponse(code = 201, message = "Transaction created."),@ApiResponse(code = 400, message = "Invalid request.")})
    public ResponseEntity<CreateTransactionResponseDto> createTransaction(@Valid @RequestBody CreateTransactionRequestDto requestDto){
        long transactionId = transactionService.createTransaction(requestDto);
        return new ResponseEntity<>(CreateTransactionResponseDto.builder().transactionId(transactionId).build(), HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    @ApiOperation(value = "Get transaction using txn id.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Transaction details not exists."),@ApiResponse(code = 200, message = "Transaction details retrieve successfully.")})
    public ResponseEntity<TransactionDto> getTransaction(@ApiParam(value = "Transaction Id",required = true) @PathVariable long transactionId){
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }
}
