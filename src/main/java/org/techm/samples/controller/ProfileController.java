package org.techm.samples.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.techm.samples.entity.User;
import org.techm.samples.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String newPassword, Principal principal, HttpSession session) {
        User user = userService.getUserByUsername(principal.getName());
        user.setPass(passwordEncoder.encode(newPassword));
        userService.create(user);
        session.setAttribute("msg", "Password changed successfully!");
        return "redirect:/users/profile";
    }
    
 // Update user information
 	@PostMapping("/update")
 	public String updateUser(@ModelAttribute User updatedUser, Principal principal, HttpSession session) {
 		User currentUser = userService.getUserByUsername(principal.getName());

 		currentUser.setName(updatedUser.getName());
 		currentUser.setEmail(updatedUser.getEmail());
 		currentUser.setMobile(updatedUser.getMobile());

 		userService.create(currentUser); 
 		session.setAttribute("msg", "Profile updated successfully!");

 		return "redirect:/users/profile";
 	}

	// delete account
	@PostMapping("/delete")
	public String deleteUser(Principal principal, HttpSession session) {
		User user = userService.getUserByUsername(principal.getName());
		userService.delete(user);

		session.invalidate();
		return "redirect:/users/profile";
	}

}