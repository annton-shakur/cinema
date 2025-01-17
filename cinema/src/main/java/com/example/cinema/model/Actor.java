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
@SQLDelete(sql = "UPDATE actors SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String description;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Actor() {
    }

    public Actor(final Long id) {
        this.id = id;
    }

    public Actor(final Long id,
                 final String name,
                 final Integer age,
                 final String description,
                 final String imageUrl,
                 final boolean isDeleted
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
    }
}
