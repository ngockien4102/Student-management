package com.account.repository;

import com.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository  extends JpaRepository<Account,Long> {

    @Query(value = "SELECT  * from accounts where username= :username",nativeQuery = true)
    Account findByUsername(@Param("username") String username);

    @Query(value = "insert into accounts_roles VALUES(:acId,:roleId)",nativeQuery = true)
    void addRole2User(@Param("acId") Long acId,@Param("roleId") Long roleId);
}
