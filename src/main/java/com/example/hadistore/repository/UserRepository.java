package com.example.hadistore.repository;

import com.example.hadistore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByStatusTrue();
    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);
}
