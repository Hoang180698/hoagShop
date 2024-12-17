package com.example.product_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String categoryId;
    String name;
    String description;
    Integer price;
    Integer quantity;
    String imageUrl;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
