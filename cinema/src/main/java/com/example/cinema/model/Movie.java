package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE movies SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false)
    private String description;
    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;
    @ManyToMany
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;
    @ManyToMany
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
    @OneToMany(mappedBy = "movie")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<MovieRating> ratings;
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Movie() {
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Movie(
            Long id,
            String title,
            Integer duration,
            String description,
            String trailerUrl,
            LocalDate releaseDate,
            Director director,
            Set<Actor> actors,
            Set<Category> categories,
            List<Comment> comments,
            List<MovieRating> ratings,
            Double averageRating,
            boolean isDeleted
    ) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.releaseDate = releaseDate;
        this.director = director;
        this.actors = actors;
        this.categories = categories;
        this.comments = comments;
        this.ratings = ratings;
        this.averageRating = averageRating;
        this.isDeleted = isDeleted;
    }
}
