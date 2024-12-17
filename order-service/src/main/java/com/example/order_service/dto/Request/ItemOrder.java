package com.example.order_service.dto.Request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrder {
    private String productId;
    private int quantity;
}
