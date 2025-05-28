package com.example.restapinotes.notes.services;

import com.example.restapinotes.notes.dto.*;
import com.example.restapinotes.notes.entity.Note;
import com.example.restapinotes.notes.repository.NoteRepository;
import com.example.restapinotes.users.entity.User;
import com.example.restapinotes.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_CONTENT_LENGTH = 1000;

    private final NoteRepository noteRepository;
    private final UserService userService;

    //Create
    public CreateNoteResponse create(String name,CreateNoteRequest request) {
        Optional<CreateNoteResponse.Error> validationError = validateCreateFields(request);
        if (validationError.isPresent()) {
            return CreateNoteResponse.failed(validationError.get());
        }
        User user = userService.findByUsername(name);

        Note createdNote = noteRepository.save(Note.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build());
        return CreateNoteResponse.success(createdNote.getId());
    }

    //Read
    public GetUserNotesResponse getUserNotes(String name){
        List<Note> notes = noteRepository.getUserNotes(name);

        return GetUserNotesResponse.success(notes);
    }

    //Update
    public UpdateNoteResponse update(String name, UpdateNoteRequest request) {
        Optional<Note> note = noteRepository.findById(request.getId());

        if(note.isEmpty()){
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.invalidNoteId);
        }

        Note updateNote = note.get();

        boolean isNoteUserNote = isNotUserNote(name,updateNote);

        if(isNoteUserNote){
            return UpdateNoteResponse.failed(UpdateNoteResponse.Error.insufficientPrivileges);
        }

        Optional<UpdateNoteResponse.Error> validateError = validateUpdateFields(request);
        if (validateError.isPresent()) {
            return UpdateNoteResponse.failed(validateError.get());
        }

        updateNote.setTitle(request.getTitle());
        updateNote.setContent(request.getContent());

        noteRepository.save(updateNote);
        return UpdateNoteResponse.success(updateNote);
    }

    //Delete
    public DeleteNoteResponse deleteNote(String name, Long id){
        Optional<Note> note = noteRepository.findById(id);
        if(note.isEmpty()){
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.invalidNoteId);
        }

        Note deleteNote = note.get();

        boolean isNoteUserNote = isNotUserNote(name,deleteNote);

        if(isNoteUserNote){
            return DeleteNoteResponse.failed(DeleteNoteResponse.Error.insufficientPrivileges);
        }

        noteRepository.delete(deleteNote);
        return DeleteNoteResponse.success();
    }

    //Validate Create Fields
    private Optional<CreateNoteResponse.Error> validateCreateFields(CreateNoteRequest request) {
        if(Objects.isNull(request.getTitle()) || request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidTitle);
        }

        if(Objects.isNull(request.getContent()) || request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(CreateNoteResponse.Error.invalidContent);
        }

        return Optional.empty();
    }

    //Validate Update Fields
    private Optional<UpdateNoteResponse.Error> validateUpdateFields(UpdateNoteRequest request) {
        if (Objects.nonNull(request.getTitle()) && request.getTitle().length() > MAX_TITLE_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidTitleLength);
        }

        if (Objects.nonNull(request.getContent()) && request.getContent().length() > MAX_CONTENT_LENGTH) {
            return Optional.of(UpdateNoteResponse.Error.invalidContentLength);
        }

        return Optional.empty();
    }

    private boolean isNotUserNote(String username, Note note) {
        return !note.getUser().getUserId().equals(username);
    }
}
