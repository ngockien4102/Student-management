package com.account.service;


import com.account.Dto.BaseResponse;
import com.account.entity.Account;
import com.account.entity.Roles;
import com.account.exception.ResourceNotFoundException;
import com.account.repository.AccountRepository;
import com.account.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final PasswordEncoder passwordEncoder;
    private static final int notFound = 80915;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Account save(Account entity) {
        return accountRepository.save(entity);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        logger.info("===== receive username {} to load user infor =====",username);
        Account a = accountRepository.findByUsername(username);
        if (a == null) {
            logger.error("this user {} not found",username);
            throw new ResourceNotFoundException(new BaseResponse(notFound,"User not found "));
        } else {

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            a.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(a.getUsername(), a.getPassword(),
                    authorities);
        }
    }

    public Account saveUser(Account user) {
        logger.info("===== receive user infor {} to save change user infor =====",user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepository.save(user);
    }

    public Roles saveRole(Roles role) {
        logger.info("===== receive user role {} to save change user role =====",role);
        return roleRepository.save(role);
    }

    public Account getByUsername(String username) {
        logger.info("===== receive username {} to find user infor =====",username);
        return accountRepository.findByUsername(username);
    }

    public void addRoleToUser(String username, String roleName) throws ResourceNotFoundException {
        logger.info("===== receive username {}, and role {} to add role to user =====",username,roleName);
        Account user = accountRepository.findByUsername(username);
        if (user == null) {
            logger.error("this user {} not found",username);
            throw new ResourceNotFoundException(new BaseResponse(notFound,"Not found for this id "));
        }

        Roles role = roleRepository.findByName(roleName);
        if (role == null) {
            logger.error("this role name {} not found",roleName);
            throw new ResourceNotFoundException(new BaseResponse(notFound,"Not found for this id "));
        }
        // accountRepository.addRole2User(user.getId(), role.getId());
        user.getRoles().add(role);
    }


}
