package com.example.order_service.entity;

import com.example.order_service.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "oders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;
    String buyerName;
    String phoneNumber;
    String email;
    Integer totalPrice;
    String deliveryAddress;
    LocalDateTime createAt;
    LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @PrePersist
    void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
