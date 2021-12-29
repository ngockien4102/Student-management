package com.example.spring.security.basic.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleRequest {
    private String username;
    private String roleName;
}
