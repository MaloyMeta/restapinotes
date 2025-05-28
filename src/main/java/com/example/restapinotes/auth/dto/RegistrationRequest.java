package com.example.restapinotes.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    private String email;
    private String password;
    private String name;
    private int age;
}
