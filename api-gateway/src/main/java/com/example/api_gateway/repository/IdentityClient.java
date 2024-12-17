//package com.example.api_gateway.repository;
//
//import com.example.api_gateway.dto.request.IntrospectRequest;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.service.annotation.PostExchange;
//import reactor.core.publisher.Mono;
//
//public interface IdentityClient {
//    @PostExchange(url = "realms/hoagShop/protocol/openid-connect/token/introspect",
//            contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    Mono<ResponseEntity<?>> introspect(@RequestBody IntrospectRequest request);
//}
