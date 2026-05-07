package org.techm.samples.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.service.AdminService;
import org.techm.samples.service.JournalService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model) {
        List<User> users = adminService.getOnlyRegularUsers();
        List<Journal> journals = adminService.getAllJournals();

        model.addAttribute("users", users);
        model.addAttribute("journals", journals);
        return "admin/admin-dashboard";
    }

     //Delete user
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("id") Long userId, RedirectAttributes redirectAttributes) {
        adminService.deleteUserById(userId);
        redirectAttributes.addFlashAttribute("msg", "User deleted successfully.");
        return "redirect:/admin/dashboard";
    }

    // Delete journal
    @PostMapping("/delete-journal")
    public String deleteJournal(@RequestParam("id") Long journalId, RedirectAttributes redirectAttributes) {
        adminService.deleteJournalById(journalId);
        redirectAttributes.addFlashAttribute("msg", "Journal deleted successfully.");
        return "redirect:/admin/dashboard";
    }
    
    
}
