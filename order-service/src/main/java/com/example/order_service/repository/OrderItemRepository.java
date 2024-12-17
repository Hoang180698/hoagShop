package com.example.order_service.repository;

import com.example.order_service.dto.Response.ItemDetail;
import com.example.order_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    @Query("select new com.example.order_service.dto.Response.ItemDetail(i.productId, i.productName, i.priceAtOrder, i.quantity)" +
            " from OrderItem i where i.orderId = ?1")
    List<ItemDetail> getAllItemDetailsByOrderId(String orderId);
}
