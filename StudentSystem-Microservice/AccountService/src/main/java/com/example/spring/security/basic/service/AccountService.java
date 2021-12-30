package com.example.spring.security.basic.service;

import com.example.spring.security.basic.User.CustomAccountDetails;
import com.example.spring.security.basic.dto.Request.AccountRequest;
import com.example.spring.security.basic.dto.Request.RoleRequest;
import com.example.spring.security.basic.dto.Response.AccountResponse;
import com.example.spring.security.basic.entity.Roles;
import com.example.spring.security.basic.exception.ResourceDuplicateException;
import com.example.spring.security.basic.exception.ResourceNotFoundException;
import com.example.spring.security.basic.repository.AccountRepository;
import com.example.spring.security.basic.entity.Account;
import com.example.spring.security.basic.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("not found user " + username);
        }
        return new CustomAccountDetails(user);
    }


    public AccountResponse getAccountbyUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setPassword(account.getPassword());
        accountResponse.setUsername(account.getUsername());
        return accountResponse;
    }

    public AccountRequest createAccount(AccountRequest accountRequest) {
        Account account = accountRepository.findByUsername(accountRequest.getUsername());
        if (account != null) {
            throw new ResourceDuplicateException();
        }
        account.setUsername(accountRequest.getUsername());
        account.setPassword(accountRequest.getPassword());
        accountRepository.save(account);
        return accountRequest;
    }

    public void updateAccount(Long id, AccountRequest accountRequest) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        account.setUsername(accountRequest.getUsername());
        account.setPassword(accountRequest.getPassword());
        accountRepository.save(account);
    }

    public RoleRequest saveRole(RoleRequest role) {
        Roles roles = rolesRepository.findByName(role.getName());
        if (roles != null) {
            throw new ResourceDuplicateException();
        }
        roles.setName(role.getName());
        rolesRepository.save(roles);
        return role;
    }

    public void addRoleToUser(String username, String roleName) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        Roles roles = rolesRepository.findByName(roleName);
        if (roles == null) {
            throw new ResourceNotFoundException();
        }

        account.getRoles().add(roles);
    }
}
