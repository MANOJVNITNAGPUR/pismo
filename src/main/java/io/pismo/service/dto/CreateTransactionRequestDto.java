package io.pismo.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class CreateTransactionRequestDto {

    @NotNull(message = "account is empty.")
    private Long accountId;

    @NotNull(message = "operation type is empty.")
    private Integer operationTypeId;

    @NotNull(message = "amount is empty.")
    private Double amount;

}
