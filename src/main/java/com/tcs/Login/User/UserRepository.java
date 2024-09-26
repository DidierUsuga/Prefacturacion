package com.tcs.Login.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying()
    @Query("update User u set u.role=:role, u.name=:name where u.id = :id")
    void updateUser(@Param(value = "id") Integer id, @Param(value = "role") String role, @Param(value = "name") String name);

}