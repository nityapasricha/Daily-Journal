package org.techm.samples.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.service.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void getAdminDashboardShouldPopulateModel() {
        List<User> mockUsers = List.of(new User());
        List<Journal> mockJournals = List.of(new Journal());

        when(adminService.getOnlyRegularUsers()).thenReturn(mockUsers);
        when(adminService.getAllJournals()).thenReturn(mockJournals);

        String view = adminController.getAdminDashboard(model);

        verify(model).addAttribute("users", mockUsers);
        verify(model).addAttribute("journals", mockJournals);
        assertEquals("admin/admin-dashboard", view);
    }

    @Test
    void deleteUserShouldRedirectAndSetMessage() {
        String result = adminController.deleteUser(1L, redirectAttributes);

        verify(adminService).deleteUserById(1L);
        verify(redirectAttributes).addFlashAttribute("msg", "User deleted successfully.");
        assertEquals("redirect:/admin/dashboard", result);
    }

    @Test
    void deleteJournalShouldRedirectAndSetMessage() {
        String result = adminController.deleteJournal(2L, redirectAttributes);

        verify(adminService).deleteJournalById(2L);
        verify(redirectAttributes).addFlashAttribute("msg", "Journal deleted successfully.");
        assertEquals("redirect:/admin/dashboard", result);
    }
}
