package com.example.order_service.dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDetail {
    String productId;
    String productName;
    Integer productPrice;
    int quantity;
}
