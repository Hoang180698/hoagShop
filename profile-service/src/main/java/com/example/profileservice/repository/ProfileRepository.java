package com.example.profileservice.repository;

import com.example.profileservice.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByProfileId(String profileId);
    Optional<Profile> findByUserId(String userId);
}
