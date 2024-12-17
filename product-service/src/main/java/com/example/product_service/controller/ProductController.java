package com.example.product_service.controller;

import com.example.product_service.dto.ApiResponse;
import com.example.product_service.dto.request.CreateProductRequest;
import com.example.product_service.dto.request.UpdateProductRequest;
import com.example.product_service.dto.response.ProductResponse;
import com.example.product_service.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("product")
@Slf4j
public class ProductController {
    ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("")
    ApiResponse<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductsById(@PathVariable("id") String id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductsById(id))
                .build();
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteProductsById(@PathVariable("id") String id) {
        return ApiResponse.<String>builder()
                .result(productService.deleteProductsById(id))
                .build();
    }

}
