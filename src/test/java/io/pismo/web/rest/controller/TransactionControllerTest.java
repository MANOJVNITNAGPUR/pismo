package io.pismo.web.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pismo.exceptions.AccountNotFoundException;
import io.pismo.exceptions.OperationTypeNotFoundException;
import io.pismo.exceptions.TransactionNotFoundException;
import io.pismo.service.AccountService;
import io.pismo.service.TransactionService;
import io.pismo.service.dto.CreateTransactionRequestDto;
import io.pismo.service.dto.TransactionDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    public void transactionNOtFoundTest() throws Exception {
        Mockito.when(transactionService.getTransaction(Mockito.eq(1L))).thenThrow(new TransactionNotFoundException());
        mockMvc.perform(get("/v1/transactions/1")).andExpect(status().isNotFound());
    }
    @Test
    public void getTransactionTest() throws Exception {
        TransactionDto transactionDto  = new TransactionDto();
        transactionDto.setTransactionId(1l);
        transactionDto.setAmount(200.0);
        transactionDto.setCreatedOn(LocalDateTime.now());
        transactionDto.setAccountId(1l);
        transactionDto.setOperationTypeId(1);
        Mockito.when(transactionService.getTransaction(Mockito.eq(1L))).thenReturn(transactionDto);
        mockMvc.perform(get("/v1/transactions/1")).andExpect(status().isOk());
    }

    @Test
    public void createTransactionAccountNotFoundExceptionTest() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any(CreateTransactionRequestDto.class))).thenThrow(new AccountNotFoundException());
        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.getCreateTransactionRequestDto())))
                .andExpect(status().isNotFound());
    }
    @Test
    public void  createTransactionOperationTypeNotFoundExceptionTest() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any(CreateTransactionRequestDto.class))).thenThrow(new OperationTypeNotFoundException());
        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.getCreateTransactionRequestDto())))
                .andExpect(status().isNotFound());
    }
    @Test
    public void  createTransactionBadRequestExceptionTest() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateTransactionRequestDto())))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void  createTransactionTest() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any(CreateTransactionRequestDto.class))).thenReturn(1L);
        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(this.getCreateTransactionRequestDto())))
                .andExpect(status().isCreated());
    }

    private CreateTransactionRequestDto getCreateTransactionRequestDto(){
        return CreateTransactionRequestDto.builder().accountId(1L).operationTypeId(1).amount(200.0).build();
    }
}
