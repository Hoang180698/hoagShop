package com.example.product_service.entity;

import jakarta.persistence.*;
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
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String categoryId;
    String name;
    String description;
    Integer price;
    Integer quantity;

    String imageUrl;

    LocalDateTime createAt;
    LocalDateTime updateAt;

    @PrePersist
    void prePersist() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
