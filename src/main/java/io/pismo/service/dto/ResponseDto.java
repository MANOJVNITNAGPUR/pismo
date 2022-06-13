package io.pismo.service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseDto {
    private Object data;
    private String message;
    private HttpStatus status;
    private boolean hasError;
}
