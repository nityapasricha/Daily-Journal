package org.techm.samples.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.security.Principal;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.techm.samples.controller.ProfileController;
import org.techm.samples.entity.User;
import org.techm.samples.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Principal principal;

    @Mock
    private HttpSession session;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUsername("tester");
        user.setPass("oldpass");
        user.setEmail("test@example.com");
        user.setName("Tester");
        user.setMobile("1234567890");
    }

    @Test
    void testChangePassword() {
        when(principal.getName()).thenReturn("tester");
        when(userService.getUserByUsername("tester")).thenReturn(user);
        when(passwordEncoder.encode("newpass")).thenReturn("encodedPass");

        String result = profileController.changePassword("newpass", principal, session);

        verify(userService).create(user);
        verify(session).setAttribute("msg", "Password changed successfully!");
        assertEquals("redirect:/users/profile", result);
    }

    @Test
    void testUpdateUserInfo() {
        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setMobile("9876543210");

        when(principal.getName()).thenReturn("tester");
        when(userService.getUserByUsername("tester")).thenReturn(user);

        String result = profileController.updateUser(updatedUser, principal, session);

        verify(userService).create(user);
        verify(session).setAttribute("msg", "Profile updated successfully!");
        assertEquals("redirect:/users/profile", result);
    }

    @Test
    void testDeleteUser() {
        when(principal.getName()).thenReturn("tester");
        when(userService.getUserByUsername("tester")).thenReturn(user);

        String result = profileController.deleteUser(principal, session);

        verify(userService).delete(user);
        verify(session).invalidate();
        assertEquals("redirect:/users/profile", result);
    }
}
