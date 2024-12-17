package com.example.order_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientException extends RuntimeException{
    private ClientError error;
    public ClientException(ClientError error) {
        super(error.getMessage());
        this.error = error;
    }
}
