package com.jwttokens.jwttokensconfig.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwttokens.jwttokensconfig.model.OurUsers;
import com.jwttokens.jwttokensconfig.model.Products;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expiryTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private List<Products> products;
    private OurUsers ourUsers;
}
