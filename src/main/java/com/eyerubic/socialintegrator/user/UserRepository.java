package com.eyerubic.socialintegrator.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    
    @Query("select u from User u where u.email=:email")
    User findByEmail(@Param("email")String email);

    @Query("select u from User u where u.id=:userId")
    User findByUserId(@Param("userId")Integer userId);

    @Query("select u from User u where u.email=:email and u.verificationCode=:code")
    User findByCodeEmail(@Param("email")String email, @Param("code")int code);
}

