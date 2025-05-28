package com.example.restapinotes.notes.dto;

import com.example.restapinotes.notes.entity.Note;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNoteResponse {
        private Error error;

        private Note updatedNote;

        public enum Error {
            ok,
            insufficientPrivileges,
            invalidNoteId,
            invalidTitleLength,
            invalidContentLength
        }

        public static UpdateNoteResponse success(Note updatedNote) {
            return builder().error(Error.ok).updatedNote(updatedNote).build();
        }

        public static UpdateNoteResponse failed(Error error) {
            return builder().error(error).updatedNote(null).build();
        }
    }

