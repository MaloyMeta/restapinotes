package com.example.restapinotes.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private Error error;

    private String authToken;

    public enum Error {
        ok,
        invalidEmail,
        invalidPassword
    }

    public static LoginResponse success(String authToken) {
        return builder().error(Error.ok).authToken(authToken).build();
    }

    public static LoginResponse failed(Error error) {
        return builder().error(error).build();
    }
}

