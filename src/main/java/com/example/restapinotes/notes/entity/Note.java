package com.example.restapinotes.notes.entity;

import com.example.restapinotes.users.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();
}
