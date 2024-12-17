package com.example.order_service.dto.Request;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String buyerName;
    private String email;
    private String phoneNumber;
    private  String deliveryAddress;
    private List<ItemOrder> items;
}
