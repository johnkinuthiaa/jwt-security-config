package com.jwttokens.jwttokensconfig.service;

import com.jwttokens.jwttokensconfig.dto.ReqRes;
import com.jwttokens.jwttokensconfig.model.OurUsers;
import com.jwttokens.jwttokensconfig.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private OurUserRepository ourUserRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signup(ReqRes registrationRequest){
        ReqRes resp=new ReqRes();
        try{
            OurUsers ourUsers =new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUsers ourUsersResult =ourUserRepository.save(ourUsers);

            if(ourUsersResult !=null &&ourUsersResult.getId()>0){
                resp.setOurUsers(ourUsersResult);
                resp.setMessage("user saved successfully");
                resp.setStatusCode(200);
            }
        }
        catch (Exception e){
            resp.setError(e.getMessage());
        }
        return resp;
    }
    public ReqRes signIn(ReqRes signinRequest){
        ReqRes response =new ReqRes();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user =ourUserRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("user is: "+user);
            var jwt =jwtUtils.generateToken(user);
            var jwtRefresh =jwtUtils.generateFreshToken(new HashMap<>(),user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(jwtRefresh);
            response.setExpiryTime("24Hr");
            response.setMessage("user successfully signed in");
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes response =new ReqRes();
        String email =jwtUtils.extractUsername(refreshTokenRequest.getToken());
        OurUsers users =ourUserRepository.findByEmail(email).orElseThrow();
        if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(),users)){
            var jwt =jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpiryTime("24Hr");
            response.setMessage("user successfully refreshed token");
        }
        response.setStatusCode(500);
        return response;
    }
}
