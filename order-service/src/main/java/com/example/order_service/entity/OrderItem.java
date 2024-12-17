package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String orderId;
    String productId;
    String productName;
    Integer quantity;
    Integer priceAtOrder;
    LocalDateTime createAt;
    LocalDateTime updateAt;

    @PrePersist
    void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
