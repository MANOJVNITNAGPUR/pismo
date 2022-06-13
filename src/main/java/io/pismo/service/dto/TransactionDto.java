package io.pismo.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@ApiModel
public class TransactionDto {
    private long transactionId;
    private long accountId;
    private int operationTypeId;
    private double amount;
    private LocalDateTime createdOn;
}
