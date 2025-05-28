package com.example.restapinotes.notes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CreateNoteRequest {
    private String title;
    private String content;
}
