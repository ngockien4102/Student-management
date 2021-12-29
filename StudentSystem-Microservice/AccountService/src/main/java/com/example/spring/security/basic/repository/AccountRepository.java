package com.example.spring.security.basic.repository;

import com.example.spring.security.basic.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
}
