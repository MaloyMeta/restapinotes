package com.example.restapinotes.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private Error error;

    public enum Error {
        ok,
        userAlreadyExists,
        invalidEmail,
        invalidPassword,
        invalidName,
        invalidAge
    }

    public static RegistrationResponse success() {
        return builder().error(Error.ok).build();
    }

    public static RegistrationResponse failed(Error error) {
        return builder().error(error).build();
    }
}

