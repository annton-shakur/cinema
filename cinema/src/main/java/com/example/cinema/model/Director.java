package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE directors SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "directors")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Director() {
    }

    public Director(final Long id) {
        this.id = id;
    }

    public Director(
            final Long id,
            final String name,
            final String description,
            final String imageUrl,
            final boolean isDeleted
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
    }
}
