package com.example.restapinotes.notes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateNoteRequest {
    private long id;
    private String title;
    private String content;
}
