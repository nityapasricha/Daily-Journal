package org.techm.samples.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techm.samples.entity.User;
import org.techm.samples.exception.UserNotFoundException;
import org.techm.samples.repository.UserRepository;
import org.techm.samples.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;


    @Test
    void createShouldReturnSavedUser() {
        User user = new User();
        user.setName("Sanchita");

        when(userRepo.save(user)).thenReturn(user);

        User saved = userService.create(user);
        assertEquals("Sanchita", saved.getName());
    }

   
    @Test
    void getUserByUsernameShouldReturnUser() {
        User mockUser = new User();
        mockUser.setUsername("sanju");

        when(userRepo.findByUsername("sanju")).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByUsername("sanju");
        assertEquals("sanju", result.getUsername());
    }

   
    @Test
    void getUserByUsernameShouldThrowIfUserNotFound() {
        when(userRepo.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("ghost"));
    }

   
    @Test
    void checkEmailShouldReturnTrueIfExists() {
        when(userRepo.existsByEmail("sanju@example.com")).thenReturn(true);

        assertTrue(userService.checkEmail("sanju@example.com"));
    }

    @Test
    void checkEmailShouldReturnFalseIfNotExists() {
        when(userRepo.existsByEmail("new@example.com")).thenReturn(false);

        assertFalse(userService.checkEmail("new@example.com"));
    }

    
    @Test
    void checkUsernameShouldReturnTrueIfExists() {
        when(userRepo.existsByUsername("sanju")).thenReturn(true);

        assertTrue(userService.checkUsername("sanju"));
    }

    
    @Test
    void deleteShouldInvokeRepoDelete() {
        User user = new User();
        user.setId(2L);

        userService.delete(user);
        verify(userRepo, times(1)).delete(user);
    }
}
