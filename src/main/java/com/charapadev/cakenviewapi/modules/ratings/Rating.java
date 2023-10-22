package com.charapadev.cakenviewapi.modules.ratings;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.charapadev.cakenviewapi.modules.cakes.entities.Cake;
import com.charapadev.cakenviewapi.modules.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ratings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cake cake;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private Double number;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return Objects.equals(getId(), rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
