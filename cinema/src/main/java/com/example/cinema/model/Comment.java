package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @Column(nullable = false)
    private String content;
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Comment() {
    }

    public Comment(Long id,
                   Movie movie,
                   String content,
                   LocalDateTime creationTime,
                   User user,
                   boolean isDeleted
    ) {
        this.id = id;
        this.movie = movie;
        this.content = content;
        this.creationTime = creationTime;
        this.user = user;
        this.isDeleted = isDeleted;
    }
}
