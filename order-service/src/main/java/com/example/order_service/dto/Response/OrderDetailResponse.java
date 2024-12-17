package com.example.order_service.dto.Response;

import com.example.order_service.dto.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    String id;
    String userId;
    String buyerName;
    String phoneNumber;
    String email;
    Integer totalPrice;
    String deliveryAddress;
    OrderStatus status;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    List<ItemDetail> items;
}
