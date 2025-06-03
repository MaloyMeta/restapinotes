package com.example.restapinotes.notes.services;

import com.example.restapinotes.notes.dto.*;
import com.example.restapinotes.notes.entity.Note;
import com.example.restapinotes.notes.repository.NoteRepository;
import com.example.restapinotes.users.entity.User;
import com.example.restapinotes.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNote_Success() {
        String username = "John_Doe";
        CreateNoteRequest request = new CreateNoteRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");

        User user = User.builder().userId(username).build();
        Note savedNote = Note.builder()
                .id(1L)
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        when(userService.findByUsername(username)).thenReturn(user);
        when(noteRepository.save(ArgumentMatchers.any(Note.class))).thenReturn(savedNote);

        CreateNoteResponse response = noteService.create(username, request);
        assertEquals(CreateNoteResponse.Error.ok, response.getError());
        assertEquals(1L, response.getCreatedNoteId());
    }

    @Test
    void testCreateNote_InvalidTitle() {
        CreateNoteRequest request = new CreateNoteRequest();
        request.setTitle(null); // invalid
        request.setContent("Content");

        CreateNoteResponse response = noteService.create("user1", request);

        assertEquals(CreateNoteResponse.Error.invalidTitle, response.getError());
        assertEquals(-1L, response.getCreatedNoteId());
    }

    @Test
    void testGetUserNotes() {
        String username = "user1";
        List<Note> notes = List.of(Note.builder().title("note1").build());

        when(noteRepository.getUserNotes(username)).thenReturn(notes);

        GetUserNotesResponse response = noteService.getUserNotes(username);

        assertEquals(GetUserNotesResponse.Error.ok, response.getError());
        assertEquals(1, response.getNotes().size());
    }

    @Test
    void testUpdateNote_Success() {
        String username = "user1";
        UpdateNoteRequest request = new UpdateNoteRequest();
        request.setId(1L);
        request.setTitle("New title");
        request.setContent("New content");

        User user = User.builder().userId(username).build();
        Note note = Note.builder().id(1L).title("Old").content("Old").user(user).build();

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        UpdateNoteResponse response = noteService.update(username, request);

        assertEquals(UpdateNoteResponse.Error.ok, response.getError());
        assertEquals("New title", response.getUpdatedNote().getTitle());
    }

    @Test
    void testUpdateNote_NotUserNote() {
        UpdateNoteRequest request = new UpdateNoteRequest();
        request.setId(1L);
        request.setTitle("title");

        User user = User.builder().userId("someoneElse").build();
        Note note = Note.builder().id(1L).title("Old").user(user).build();

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        UpdateNoteResponse response = noteService.update("user1", request);

        assertEquals(UpdateNoteResponse.Error.insufficientPrivileges, response.getError());
    }

    @Test
    void testDeleteNote_Success() {
        String username = "user1";
        User user = User.builder().userId(username).build();
        Note note = Note.builder().id(1L).user(user).build();

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        DeleteNoteResponse response = noteService.deleteNote(username, 1L);

        verify(noteRepository).delete(note);
        assertEquals(DeleteNoteResponse.Error.ok, response.getError());
    }

    @Test
    void testDeleteNote_InvalidId() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        DeleteNoteResponse response = noteService.deleteNote("user1", 1L);

        assertEquals(DeleteNoteResponse.Error.invalidNoteId, response.getError());
    }

}
