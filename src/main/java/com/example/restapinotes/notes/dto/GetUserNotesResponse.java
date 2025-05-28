package com.example.restapinotes.notes.dto;

import com.example.restapinotes.notes.entity.Note;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserNotesResponse {
    private Error error;

    private List<Note> notes;

    public enum Error {
        ok
    }

    public static GetUserNotesResponse success(List<Note> notes) {
        return builder().error(Error.ok).notes(notes).build();
    }

    public static GetUserNotesResponse failed(Error error) {
        return builder().error(error).notes(null).build();
    }
}
