package com.example.product_service.controller;

import com.example.product_service.dto.ApiResponse;
import com.example.product_service.dto.request.OrderItemRequest;
import com.example.product_service.dto.response.OrderItemResponse;
import com.example.product_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("product")
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping("order")
    ApiResponse<List<OrderItemResponse>> createOrderItem(@RequestBody OrderItemRequest request) {
        return ApiResponse.<List<OrderItemResponse>>builder()
                    .result(orderService.createOrderItem(request))
                    .build();
        }
}
