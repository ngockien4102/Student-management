package com.student.Token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class CheckRoleService {
    public static boolean checkRole(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String role = String.valueOf(jwt.getClaim("roles"));

        if (role.contains("ROLE_ADMIN")) {
            return true;
        } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(checkRole("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWVubm4iLCJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlzcy" +
//                "I6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA5MS9hY2NvdW50cy9sb2dpbiIsImV4cCI6MTYzNjQ0MTk0OH0.1pUW8ogZBdjD5JKgXumarUwaB" +
//                "6z-kUTp4TH4yhg4jF4"));;
//    }

}
