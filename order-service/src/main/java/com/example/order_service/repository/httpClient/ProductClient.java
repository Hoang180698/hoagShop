package com.example.order_service.repository.httpClient;

import com.example.order_service.config.AuthenticationRequestInterceptor;
import com.example.order_service.dto.ApiResponse;
import com.example.order_service.dto.Request.ItemOrder;
import com.example.order_service.dto.Request.ItemOrderList;
import com.example.order_service.dto.Response.ItemDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "profile-service", url = "${app.services.product.url}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface ProductClient {
    @PostMapping(value = "product/order", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<ItemDetail>> orderItem(@RequestBody ItemOrderList items);
}
