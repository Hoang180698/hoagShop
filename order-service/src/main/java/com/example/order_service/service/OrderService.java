package com.example.order_service.service;

import com.example.event.dto.NotificationEvent;
import com.example.order_service.dto.OrderStatus;
import com.example.order_service.dto.Request.CreateOrderRequest;
import com.example.order_service.dto.Request.ItemOrderList;
import com.example.order_service.dto.Response.ItemDetail;
import com.example.order_service.dto.Response.OrderDetailResponse;
import com.example.order_service.dto.Response.OrderResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import com.example.order_service.exception.AppException;
import com.example.order_service.exception.ErrorCode;
import com.example.order_service.exception.ErrorNormalizer;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.httpClient.ProductClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    ObjectMapper objectMapper;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    ProductClient productClient;
    ErrorNormalizer errorNormalizer;
    KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public List<OrderResponse> getAllOrder() {
        return null;
    }

    public OrderResponse getOrderById(String id) {
        return null;
    }

    public OrderDetailResponse getOrderDetailById(String id) {
        return null;
    }

    @Transactional
    public OrderDetailResponse createOrder(CreateOrderRequest createOrderRequest) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            String orderId = UUID.randomUUID().toString();
            List<ItemDetail> itemDetails = productClient.orderItem(new ItemOrderList(createOrderRequest.getItems())).getResult();
            Integer totalPrice = 0;
            for (ItemDetail itemDetail : itemDetails) {
                OrderItem orderItem = OrderItem.builder()
                        .orderId(orderId)
                        .productId(itemDetail.getProductId())
                        .priceAtOrder(itemDetail.getProductPrice())
                        .productName(itemDetail.getProductName())
                        .quantity(itemDetail.getQuantity())
                        .build();
                totalPrice += itemDetail.getProductPrice() * orderItem.getQuantity();
                orderItemRepository.save(orderItem);
            }
            Order order = Order.builder()
                    .id(orderId)
                    .totalPrice(totalPrice)
                    .email(createOrderRequest.getEmail())
                    .phoneNumber(createOrderRequest.getPhoneNumber())
                    .buyerName(createOrderRequest.getBuyerName())
                    .userId(userId)
                    .status(OrderStatus.PENDING)
                    .deliveryAddress(createOrderRequest.getDeliveryAddress())
                    .build();
            order = orderRepository.save(order);

            //Todo: thong bao cho admin

            return OrderDetailResponse.builder()
                    .id(orderId)
                    .totalPrice(totalPrice)
                    .email(order.getEmail())
                    .phoneNumber(order.getPhoneNumber())
                    .deliveryAddress(createOrderRequest.getDeliveryAddress())
                    .items(itemDetails)
                    .createAt(order.getCreateAt())
                    .updateAt(order.getUpdateAt())
                    .userId(userId)
                    .status(order.getStatus())
                    .build();

        } catch (FeignException e) {
            throw errorNormalizer.clientException(e);
        }
    }

    public OrderResponse changeStatus(String id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.INVALID_ORDER_ID);
        });
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);

        if (OrderStatus.CONFIRMED.toString().equals(status)) {
            List<ItemDetail> itemDetails = orderItemRepository.getAllItemDetailsByOrderId(id);

            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .email(order.getEmail())
                    .phoneNumber(order.getPhoneNumber())
                    .status(order.getStatus())
                    .userId(order.getUserId())
                    .buyerName(order.getBuyerName())
                    .deliveryAddress(order.getDeliveryAddress())
                    .createAt(order.getCreateAt())
                    .items(itemDetails)
                    .build();

            NotificationEvent notificationEvent = NotificationEvent.builder()
                    .channel("EMAIL")
                    .recipient(order.getEmail())
                    .data(orderDetailResponse)
                    .subject("Order confirmed")
                    .build();

            kafkaTemplate.send("order-confirmed", notificationEvent);
        }
        return null;
    }
}
