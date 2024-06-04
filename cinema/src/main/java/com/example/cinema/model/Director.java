package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
    @OneToMany(mappedBy = "director")
    private List<Movie> movieList;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Director() {
    }

    public Director(Long id) {
        this.id = id;
    }

    public Director(Long id,
                    String name,
                    String description,
                    List<Movie> movieList,
                    boolean isDeleted
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.movieList = movieList;
        this.isDeleted = isDeleted;
    }
}
