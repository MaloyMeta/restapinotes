package com.example.restapinotes.notes.dto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNoteResponse {
    private Error error;

    public enum Error {
        ok,
        insufficientPrivileges,
        invalidNoteId
    }

    public static DeleteNoteResponse success() {
        return builder().error(Error.ok).build();
    }

    public static DeleteNoteResponse failed(Error error) {
        return builder().error(error).build();
    }
}
