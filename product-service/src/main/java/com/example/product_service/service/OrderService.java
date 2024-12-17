package com.example.product_service.service;

import com.example.product_service.dto.request.ItemOrder;
import com.example.product_service.dto.request.OrderItemRequest;
import com.example.product_service.dto.response.OrderItemResponse;
import com.example.product_service.entity.Product;
import com.example.product_service.exception.AppException;
import com.example.product_service.exception.ErrorCode;
import com.example.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    ProductRepository productRepository;

    @Transactional
    public List<OrderItemResponse> createOrderItem(OrderItemRequest request) {
        List<OrderItemResponse> responses = new ArrayList<>();
        for (ItemOrder item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> {
                throw new AppException(ErrorCode.INVALID_PRODUCT_ID);
            });
            if(product.getQuantity() < item.getQuantity()) {
                throw new AppException(ErrorCode.NOT_ENOUGH_STOCK);
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());

            OrderItemResponse orderItem = OrderItemResponse
                    .builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            responses.add(orderItem);
        }
        return responses;
    }
}
