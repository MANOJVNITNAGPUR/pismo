package io.pismo.web.rest.controller;

import io.pismo.configuration.ExceptionResolver;
import io.pismo.service.AccountService;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.RegisterAccountRequestDto;
import io.pismo.service.dto.RegisterAccountResponseDto;
import io.pismo.service.dto.ResponseDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags="Account API.")
@RequestMapping("v1/accounts")
public class AccountController extends ExceptionResolver {

    @Autowired
    private AccountService accountService;


    @PostMapping
    @ApiOperation(value = "Register account.")
    @ApiResponses(value = {@ApiResponse(code = 409, message = "Account already exists."),@ApiResponse(code = 201, message = "Account created."),@ApiResponse(code = 400, message = "Invalid request.")})
    public ResponseEntity<RegisterAccountResponseDto> registerAccount(@ApiParam("Account registration details") @Valid @RequestBody RegisterAccountRequestDto requestDto){
        RegisterAccountResponseDto responseDto = accountService.registerAccount(requestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @ApiOperation("Get Account details using account id.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Account not found."),@ApiResponse(code = 200, message = "Account details retrieve successfully.")})
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@ApiParam(value = "Account id", required = true) @PathVariable("accountId") final long accountId){
        return ResponseEntity.ok(accountService.getAccount(accountId));
    }

}
