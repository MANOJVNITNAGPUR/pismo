package io.pismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Transaction not exists.")
public class TransactionNotFoundException extends RuntimeException{
}
