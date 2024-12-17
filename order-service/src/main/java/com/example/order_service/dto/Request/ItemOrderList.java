package com.example.order_service.dto.Request;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrderList {
    private List<ItemOrder> items;
}
