package com.example.profileservice.service;

import com.example.profileservice.dto.identity.Credential;
import com.example.profileservice.dto.identity.TokenExchangeParam;
import com.example.profileservice.dto.identity.UserCreationParam;
import com.example.profileservice.dto.request.RegistrationRequest;
import com.example.profileservice.dto.response.ProfileResponse;
import com.example.profileservice.entity.Profile;
import com.example.profileservice.exception.AppException;
import com.example.profileservice.exception.ErrorCode;
import com.example.profileservice.exception.ErrorNormalizer;
import com.example.profileservice.mapper.ProfileMapper;
import com.example.profileservice.repository.IdentityClient;
import com.example.profileservice.repository.ProfileRepository;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {
    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    IdentityClient identityClient;
    ErrorNormalizer errorNormalizer;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    public ProfileResponse register(RegistrationRequest request) {
        // create account in keycloak

        // Exchange client token
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .scope("openid")
                    .client_secret(clientSecret)
                    .build());
            log.info("TokenInfo {}", token);
            // Create user with client token and given info
            var creationResponse = identityClient.createUser("Bearer " + token.getAccessToken(), UserCreationParam.builder()
                    .username(request.getUsername())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .enabled(true)
                    .emailVerified(false)
                    .realmRoles(List.of("User"))
                    .credentials(List.of(Credential.builder()
                            .type("password")
                            .value(request.getPassword())
                            .temporary(false)
                            .build()))
                    .build());
            // get userId of keycloak account
            String userId = extractUserId(creationResponse);
            var profile = profileMapper.toProfile(request);
            profile.setUserId(userId);

            profile = profileRepository.save(profile);
            return profileMapper.toProfileResponse(profile);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        }

    }

    public List<ProfileResponse> getAllProfiles() {
        var auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("auth: {}", auth);
        var profiles = profileRepository.findAll();
        return profiles.stream().map(profileMapper::toProfileResponse).toList();
    }

    public ProfileResponse getMyProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("userId: {}", userId);
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        return profileMapper.toProfileResponse(profile);
    }

    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").get(0);
        String[] splitStr = location.split("/");
        return splitStr[splitStr.length - 1];
    }
}
