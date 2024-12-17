package com.example.order_service.dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String userId;
    String buyerName;
    String email;
    String phoneNumber;
    Integer totalPrice;
    String deliveryAddress;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
