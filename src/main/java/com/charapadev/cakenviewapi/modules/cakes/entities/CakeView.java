package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "views")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CakeView implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    @Builder.Default
    private int views = 0;

    @OneToOne(fetch = FetchType.LAZY)
    private Cake cake;

    public void incrementView() {
        this.views++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CakeView)) return false;
        CakeView cakeView = (CakeView) o;
        return Objects.equals(getId(), cakeView.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
