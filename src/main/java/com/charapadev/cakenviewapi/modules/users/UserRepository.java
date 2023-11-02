package com.charapadev.cakenviewapi.modules.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Searches an user by the given email address.
     *
     * @param email The email address.
     * @return The user that owns it.
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    /**
     * Checks if already exists an user using the desired email address.
     *
     * @param email The email address.
     * @return If the user with this email exists or not.
     */
    boolean existsUserByEmail(String email);

}
