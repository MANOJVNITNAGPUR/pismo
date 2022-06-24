package io.pismo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTypeDto {
    private int operationTypeId;
    private String description;
    private int operationValue;

    public boolean isCreditTransaction(){
        return operationTypeId == 4;
    }
}
