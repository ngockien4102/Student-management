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
    // http://localhost:8091/accounts/{id}
    @CrossOrigin
    @GetMapping("/{id}")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AccountDto.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = BaseResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})

    public ResponseEntity<AccountResponse> getAccountById(@Valid @PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        AccountResponse accountResponse = accountService.getAccountbyId(id);
        return ResponseEntity.ok().body(accountResponse);
    }


    // Create account
    // http://localhost:8091/accounts
    @CrossOrigin
    @PostMapping("/create")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = AccountDto.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<AccountRequest> createAccount(@Valid @RequestBody AccountRequest accountRequest) throws ResourceBadRequestException {
        // convert DTO to entity
//        Account postRequest = modelMapper.map(accountDto, Account.class);
//        Account account = accountService.saveUser(postRequest);
//        // convert entity to DTO
//        AccountDto postResponse = modelMapper.map(account, AccountDto.class);
        AccountRequest accountResponse = accountService.createAccount(accountRequest);
        return new ResponseEntity<AccountRequest>(accountResponse, HttpStatus.CREATED);
    }


    // Update account
    // http://localhost:8091/accounts/{id}
    @CrossOrigin
    @PutMapping("/{id}")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
//            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<AccountRequest> updateAccount(@Valid @PathVariable(name = "id") Long id, @RequestBody AccountRequest accountRequest)
            throws ResourceNotFoundException, ResourceBadRequestException {
        // convert DTO to Entity
//        Account accountRequest = accountService.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(new BaseResponse(notFound, "Not found for this id")));
//        accountRequest = modelMapper.map(accountDto, Account.class);
//        Account account = accountService.saveUser(accountRequest);
//
//        // entity to DTO
//        AccountDto accountResponse = modelMapper.map(account, AccountDto.class);
        accountService.updateAccount(id, accountRequest);
        return ResponseEntity.ok().body(accountRequest);
    }


    // create role(ex:,ROLE_ADMIN,ROLE_USER,ROLE_MANAGE,ROLE_PARENT,...)
    // http://localhost:8091/accounts/role/save
    @CrossOrigin
    @PostMapping("/role/save")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
//            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<RoleRequest> saveRole(@Valid @RequestBody RoleRequest role) {
        return new ResponseEntity<RoleRequest>(accountService.saveRole(role), HttpStatus.CREATED);
    }


    // add role to User
    // http://localhost:8091/accounts/role/addtoaccounts
    @CrossOrigin
    @PostMapping("/role/addtoaccounts")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
//            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody AddRoleRequest form) {
        accountService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}
