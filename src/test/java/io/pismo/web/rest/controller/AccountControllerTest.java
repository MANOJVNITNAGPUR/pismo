package io.pismo.web.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.exceptions.AccountAlreadyRegisterException;
import io.pismo.exceptions.AccountNotFoundException;
import io.pismo.service.AccountService;
import io.pismo.service.dto.AccountDto;
import io.pismo.service.dto.RegisterAccountRequestDto;
import io.pismo.service.dto.RegisterAccountResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(accountController).build();
    }

    @Test
    public void accountNotExistsTest() throws Exception {
        Mockito.when(accountService.getAccount(Mockito.eq(1l))).thenThrow(new AccountNotFoundException());
        mockMvc.perform(get("/v1/accounts/1")).andExpect(status().isNotFound());
    }

    @Test
    public void accountTest() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(1l);
        accountDto.setDocumentNumber("12345");
        Mockito.when(accountService.getAccount(Mockito.eq(1l))).thenReturn(accountDto);
        mockMvc.perform(get("/v1/accounts/1")).andExpect(status().isOk());
    }

    @Test
    public void accountAlreadyRegisteredTest() throws Exception {
        Mockito.when(accountService.registerAccount(Mockito.any(RegisterAccountRequestDto.class))).thenThrow(new AccountAlreadyRegisterException());
        RegisterAccountRequestDto requestDto = new RegisterAccountRequestDto();
        requestDto.setDocumentNumber("12345");
        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))).andExpect(status().isConflict());
    }

    @Test
    public void accountRegisterTest() throws Exception {
        Mockito.when(accountService.registerAccount(Mockito.any(RegisterAccountRequestDto.class))).thenReturn(RegisterAccountResponseDto.builder().accountId(1l).build());
        RegisterAccountRequestDto requestDto = new RegisterAccountRequestDto();
        requestDto.setDocumentNumber("12345");
        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))).andExpect(status().isCreated());
    }

    @Test
    public void accountRegisterBadRequestTest() throws Exception {
        Mockito.when(accountService.registerAccount(Mockito.any(RegisterAccountRequestDto.class))).thenReturn(RegisterAccountResponseDto.builder().accountId(1l).build());
        RegisterAccountRequestDto requestDto = new RegisterAccountRequestDto();
        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))).andExpect(status().isBadRequest());
    }
}
