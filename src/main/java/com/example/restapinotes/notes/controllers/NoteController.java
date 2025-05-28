package com.example.restapinotes.notes.controllers;

import com.example.restapinotes.notes.dto.*;
import com.example.restapinotes.notes.entity.Note;
import com.example.restapinotes.notes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public CreateNoteResponse createNote(Principal principal, @RequestBody CreateNoteRequest request) {
        return noteService.create(principal.getName(), request);
    }

    @GetMapping
    public GetUserNotesResponse getAllNotes(Principal principal) {
        return noteService.getUserNotes(principal.getName());
    }

    @PatchMapping
    public UpdateNoteResponse updateNote(Principal principal, @RequestBody UpdateNoteRequest request) {
        return noteService.update(principal.getName(), request);
    }

    @DeleteMapping
    public DeleteNoteResponse deleteNote(Principal principal, @RequestParam(name = "id") long id){
        return noteService.deleteNote(principal.getName(), id);
    }
}
