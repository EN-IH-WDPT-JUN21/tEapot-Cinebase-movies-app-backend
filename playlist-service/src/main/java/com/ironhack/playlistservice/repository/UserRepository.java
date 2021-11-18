package com.ironhack.playlistservice.repository;

import com.ironhack.playlistservice.dao.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsername (String username);

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
