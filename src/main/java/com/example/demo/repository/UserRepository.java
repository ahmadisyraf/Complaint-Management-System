package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
