package org.techm.samples.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.service.JournalService;
import org.techm.samples.service.UserService;

@ExtendWith(MockitoExtension.class)
class JournalControllerTest {

    @InjectMocks
    private JournalController journalController;

    @Mock
    private UserService userService;

    @Mock
    private JournalService journalService;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Mock
    private MultipartFile imageFile;

    private User user;
    private Journal journal;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("tester");

        journal = new Journal();
        journal.setId(10L);
        journal.setTitle("Sample Journal");
    }


    @Test
    void viewJournalShouldAddToModel() {
        when(journalService.getJournalById(10L)).thenReturn(journal);

        String result = journalController.viewJournal(10L, model);

        verify(model).addAttribute("journal", journal);
        assertEquals("journal/view-journal", result);
    }

    @Test
    void viewAdminJournalShouldAddToModel() {
        when(journalService.getJournalById(10L)).thenReturn(journal);

        String result = journalController.viewAdminJournal(10L, model);

        verify(model).addAttribute("journal", journal);
        assertEquals("journal/view-journal-admin", result);
    }

    @Test
    void editJournalShouldLoadJournalForm() {
        when(journalService.getJournalById(10L)).thenReturn(journal);

        String result = journalController.editJournal(10L, model);

        verify(model).addAttribute("journal", journal);
        assertEquals("journal/journal-form", result);
    }

    @Test
    void deleteJournalShouldRedirect() {
        String result = journalController.deleteJournal(10L);
        verify(journalService).deleteJournalById(10L);
        assertEquals("redirect:/users/dashboard", result);
    }

    @Test
    void createJournalShouldHandleTagsAndImageUpload() throws Exception {
        when(principal.getName()).thenReturn("tester");
        when(userService.getUserByUsername("tester")).thenReturn(user);
        when(imageFile.isEmpty()).thenReturn(true);

        String result = journalController.createJournal(journal, principal, imageFile, "tag1, tag2");

        verify(journalService).attachTags(journal, List.of("tag1", "tag2"));
        verify(journalService).saveJournal(journal);
        assertEquals("redirect:/users/dashboard", result);
    }

    @Test
    void updateJournalShouldRetainOldImageIfEmpty() {
        when(principal.getName()).thenReturn("tester");
        when(userService.getUserByUsername("tester")).thenReturn(user);
        when(imageFile.isEmpty()).thenReturn(true);
        when(journalService.getJournalById(journal.getId())).thenReturn(journal);

        journal.setImg("old.jpg");

        String result = journalController.updateJournal(journal, principal, imageFile, "tag1, tag2");

        verify(journalService).attachTags(journal, List.of("tag1", "tag2"));
        verify(journalService).saveJournal(journal);
        assertEquals("redirect:/users/dashboard", result);
    }
}
