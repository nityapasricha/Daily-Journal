package org.techm.samples.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.exception.UserNotFoundException;
import org.techm.samples.repository.JournalRepository;
import org.techm.samples.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JournalRepository journalRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Journal> getAllJournals() {
        return journalRepo.findAll();
    }

    public void deleteUserById(Long id) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        
        userRepo.delete(user);
    }


    public void deleteJournalById(Long id) {
        journalRepo.deleteById(id);
    }
    
    public List<User> getOnlyRegularUsers() {
        return userRepo.findByRole("user");
    }

    
}
