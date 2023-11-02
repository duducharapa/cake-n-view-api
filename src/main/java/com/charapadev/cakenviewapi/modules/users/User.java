package com.charapadev.cakenviewapi.modules.users;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the customers that consumes the application and rate the cakes available.
 */

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    /**
     * User internal identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user unique email address.
     *
     * It can be used to perform some sensitive actions like authentications.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The user public name.
     *
     * It'll be used on public resources like ratings or "identification" in some application sections.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The user password.
     *
     * It is used on authentication and is hashed already on user registration.
     */
    @Column(nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
