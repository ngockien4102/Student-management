package com.library.decode;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class GetUserName {
    public String  getUsername(String token){
        DecodedJWT jwt = JWT.decode(token);
        String username = String.valueOf(jwt.getClaim("sub")).replace("\""," ").trim();
        return username;
    }
}
