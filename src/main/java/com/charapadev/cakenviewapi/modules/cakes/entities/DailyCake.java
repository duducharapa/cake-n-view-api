package com.charapadev.cakenviewapi.modules.cakes.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "trending_cakes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DailyCake implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Cake cake;

    @Column
    @Builder.Default
    private boolean current = true;

    @Column(nullable = false)
    private Timestamp expiresAt;

}
