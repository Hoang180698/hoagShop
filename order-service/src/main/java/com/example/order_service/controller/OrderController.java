package com.example.order_service.controller;

import com.example.order_service.dto.ApiResponse;
import com.example.order_service.dto.Request.CreateOrderRequest;
import com.example.order_service.dto.Response.OrderDetailResponse;
import com.example.order_service.dto.Response.OrderResponse;
import com.example.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("order")
@Slf4j
public class OrderController {
    OrderService orderService;

    @GetMapping("")
    ApiResponse<List<OrderResponse>> getAllOrder() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrder())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<OrderResponse> getOrderById(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(id))
                .build();
    }
    @GetMapping("detail/{id}")
    ApiResponse<OrderDetailResponse> getOrderDetailById(@PathVariable String id) {
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderService.getOrderDetailById(id))
                .build();
    }

    @PostMapping("/create")
    ApiResponse<OrderDetailResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderService.createOrder(createOrderRequest))
                .build();
    }

    @PutMapping("/status/change/{id}")
    ApiResponse<OrderResponse> changeStatus(@PathVariable String id, @RequestParam String status) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.changeStatus(id, status))
                .build();
    }
}
