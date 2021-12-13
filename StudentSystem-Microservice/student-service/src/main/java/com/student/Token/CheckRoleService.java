package com.student.Token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class CheckRoleService {
    public boolean checkRole(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String role = String.valueOf(jwt.getClaim("roles"));

        if (role.contains("ROLE_ADMIN")) {
            return true;
        } else {
            return false;
        }
    }
}
