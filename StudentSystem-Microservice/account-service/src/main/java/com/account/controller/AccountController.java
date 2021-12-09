package com.account.controller;

import com.account.Dto.RoleToUserForm;
import com.account.entity.Account;
import com.account.entity.Roles;
import com.account.exception.ResourceBadRequestException;
import com.account.exception.ResourceNotFoundException;
import com.account.repository.AccountRepository;
import com.account.service.AccountService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.account.Dto.AccountDto;
import com.account.Dto.BaseResponse;
import com.account.Dto.LoginResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private static final int notFound = 80915;
    private static final int usernameEmpty = 80911;
    private static final int passwordEmpty = 80912;
    @Autowired
    private HttpSession session;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;
//    @GetMapping
//    public List<PostDto> getAllPosts() {
//
//        return postService.getAllPosts().stream().map(post -> modelMapper.map(post, PostDto.class))
//                .collect(Collectors.toList());
//    }

    // get detail account
    // http://localhost:8091/accounts/{id}
    @GetMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AccountDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = BaseResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})

    public ResponseEntity<AccountDto> getAccountById(@Valid @PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(new BaseResponse(notFound, "Not found for this id")));

        // convert entity to DTO
        AccountDto postResponse = modelMapper.map(account, AccountDto.class);


        return ResponseEntity.ok().body(postResponse);
    }

    // Login
    // http://localhost:8091/accounts/login
    @PostMapping("/login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Login success", response = LoginResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public LoginResponse login(HttpServletRequest request, @RequestBody AccountDto accountDto) throws ResourceBadRequestException {

        if (accountDto.getUsername() == null) {
            throw new ResourceBadRequestException(new BaseResponse(usernameEmpty, "must input username"));
        } else if (accountDto.getPassword() == null) {
            throw new ResourceBadRequestException(new BaseResponse(passwordEmpty, "must input password"));
        }
        HttpSession session = request.getSession();
        AccountDto ac = (AccountDto) session.getAttribute("accountInfo");
        return new LoginResponse(session.getAttribute("token").toString(), ac);
    }

    // Logout
    // http://localhost:8091/accounts/logout
    @PostMapping("/logout")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Logout success", response = String.class)})
    public String logout() {
        return "Logout success";
    }

    // Create account
    // http://localhost:8091/accounts
    @PostMapping("")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Add success", response = AccountDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) throws ResourceBadRequestException {
        // convert DTO to entity

        if (accountDto.getUsername() == null) {
            throw new ResourceBadRequestException(new BaseResponse(usernameEmpty, "must input username"));
        } else if (accountDto.getPassword() == null) {
            throw new ResourceBadRequestException(new BaseResponse(passwordEmpty, "must input password"));
        }
        Account postRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountService.save(postRequest);
        // convert entity to DTO
        AccountDto postResponse = modelMapper.map(account, AccountDto.class);

        return new ResponseEntity<AccountDto>(postResponse, HttpStatus.CREATED);
    }

    // Update account
    // http://localhost:8091/accounts/{id}
    @PutMapping("{id}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<AccountDto> updateAccount( @PathVariable long id, @RequestBody AccountDto accountDto)
            throws ResourceNotFoundException , ResourceBadRequestException{
        if (accountDto.getUsername() == null) {
            throw new ResourceBadRequestException(new BaseResponse(usernameEmpty, "must input username"));
        } else if (accountDto.getPassword() == null) {
            throw new ResourceBadRequestException(new BaseResponse(passwordEmpty, "must input password"));
        }
        // convert DTO to Entity
        Account accountRequest = accountService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(new BaseResponse(notFound, "Not found for this id")));
        accountRequest = modelMapper.map(accountDto, Account.class);
        Account account = accountService.save(accountRequest);

        // entity to DTO
        AccountDto accountResponse = modelMapper.map(account, AccountDto.class);

        return ResponseEntity.ok().body(accountResponse);
    }

    // create role(ex:,ROLE_ADMIN,ROLE_USER,ROLE_MANAGE,ROLE_PARENT,...)
    // http://localhost:8091/accounts/role/save
    @PostMapping("/role/save")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<Roles> saveRole(@Valid @RequestBody Roles role) {

        return new ResponseEntity<Roles>(accountService.saveRole(role), HttpStatus.CREATED);
    }

    // add role to User
    // http://localhost:8091/accounts/role/addtoaccounts
    @PostMapping("/role/addtoaccounts")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update success", response = AccountDto.class),
            @ApiResponse(code = 401, message = "Unauthorization", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BaseResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = BaseResponse.class)})
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody RoleToUserForm form) {
        accountService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
