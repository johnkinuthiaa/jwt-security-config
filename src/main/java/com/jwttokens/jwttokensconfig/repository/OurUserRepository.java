package com.jwttokens.jwttokensconfig.repository;

import com.jwttokens.jwttokensconfig.model.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OurUserRepository extends JpaRepository<OurUsers,Long> {
    Optional<OurUsers> findByEmail(String email);
}
