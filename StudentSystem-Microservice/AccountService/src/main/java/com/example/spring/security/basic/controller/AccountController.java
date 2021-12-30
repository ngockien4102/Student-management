package com.example.spring.security.basic.controller;

import com.example.spring.security.basic.User.CustomAccountDetails;
import com.example.spring.security.basic.dto.Request.AccountRequest;
import com.example.spring.security.basic.dto.Request.AddRoleRequest;
import com.example.spring.security.basic.dto.Request.LoginRequest;
import com.example.spring.security.basic.dto.Request.RoleRequest;
import com.example.spring.security.basic.dto.Response.AccountResponse;
import com.example.spring.security.basic.dto.Response.LoginResponse;
import com.example.spring.security.basic.exception.ResourceBadRequestException;
import com.example.spring.security.basic.exception.ResourceNotFoundException;
import com.example.spring.security.basic.jwt.JwtTokenProvider;
import com.example.spring.security.basic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountService accountService;

    @CrossOrigin
    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomAccountDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request

    // get detail account
    // http://localhost:8091/accounts/{username}
    @CrossOrigin
    @GetMapping("/{username}")

    public ResponseEntity<AccountResponse> getAccountById(@Valid @PathVariable(name = "username") String username) throws ResourceNotFoundException {
        AccountResponse accountResponse = accountService.getAccountbyUsername(username);
        return ResponseEntity.ok().body(accountResponse);
    }


    // Create account
    // http://localhost:8091/accounts
    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<AccountRequest> createAccount(@Valid @RequestBody AccountRequest accountRequest) throws ResourceBadRequestException {
        AccountRequest accountResponse = accountService.createAccount(accountRequest);
        return new ResponseEntity<AccountRequest>(accountResponse, HttpStatus.CREATED);
    }


    // Update account
    // http://localhost:8091/accounts/{id}
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<AccountRequest> updateAccount(@Valid @PathVariable(name = "id") Long id, @RequestBody AccountRequest accountRequest)
            throws ResourceNotFoundException, ResourceBadRequestException {
        accountService.updateAccount(id, accountRequest);
        return ResponseEntity.ok().body(accountRequest);
    }


    // create role(ex:,ROLE_ADMIN,ROLE_USER,ROLE_MANAGE,ROLE_PARENT,...)
    // http://localhost:8091/accounts/role/save
    @CrossOrigin
    @PostMapping("/role/save")
    public ResponseEntity<RoleRequest> saveRole(@Valid @RequestBody RoleRequest role) {
        return new ResponseEntity<RoleRequest>(accountService.saveRole(role), HttpStatus.CREATED);
    }


    // add role to User
    // http://localhost:8091/accounts/role/addtoaccounts
    @CrossOrigin
    @PostMapping("/role/addtoaccounts")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody AddRoleRequest form) {
        accountService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}
