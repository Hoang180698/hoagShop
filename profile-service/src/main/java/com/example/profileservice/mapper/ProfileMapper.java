package com.example.profileservice.mapper;

import com.example.profileservice.dto.request.RegistrationRequest;
import com.example.profileservice.dto.response.ProfileResponse;
import com.example.profileservice.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);

    ProfileResponse toProfileResponse(Profile profile);
}
