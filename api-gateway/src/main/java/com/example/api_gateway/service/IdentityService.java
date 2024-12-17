//package com.example.api_gateway.service;
//
//import com.example.api_gateway.dto.ApiResponse;
//import com.example.api_gateway.dto.request.IntrospectRequest;
//import com.example.api_gateway.dto.response.IntrospectResponse;
//import com.example.api_gateway.repository.IdentityClient;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.experimental.NonFinal;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class IdentityService {
//    IdentityClient identityClient;
//
//    @Value("${idp.client-id}")
//    @NonFinal
//    String clientId;
//
//    @Value("${idp.client-secret}")
//    @NonFinal
//    String clientSecret;
//
//    public Mono<ResponseEntity<?>> introspect(String token){
//        return identityClient.introspect(IntrospectRequest.builder()
//                .client_id(clientId)
//                .client_secret(clientSecret)
//                .token(token)
//                .build());
//    }
//}
