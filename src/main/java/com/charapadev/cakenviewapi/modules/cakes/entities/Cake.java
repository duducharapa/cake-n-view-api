package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
import lombok.ToString;

@Entity
@Table(name = "cakes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Cake implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    @Builder.Default
    private CakeRatingData rating = new CakeRatingData(0.0, 0);

    @Column
    @Builder.Default
    private String imageUrl = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cake)) return false;
        Cake cake = (Cake) o;
        return Objects.equals(getId(), cake.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
