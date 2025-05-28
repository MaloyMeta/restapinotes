package com.example.restapinotes.users.entity;

import com.example.restapinotes.notes.entity.Note;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "user")
    List<Note> notes;

}
