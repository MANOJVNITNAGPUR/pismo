package io.pismo.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("Customer account details model.")
public class AccountDto {
    private long accountId;
    private String documentNumber;
}
