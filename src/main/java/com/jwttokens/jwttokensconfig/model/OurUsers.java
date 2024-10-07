package com.jwttokens.jwttokensconfig.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class OurUsers implements UserDetails{
    @Id
    private Long id;
    private String email;
    private String password;

    public Collection<? extends GrantedAuthority>
}
