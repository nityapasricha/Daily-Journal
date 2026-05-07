package org.techm.samples.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.service.JournalService;
import org.techm.samples.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public JournalService journalService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/dashboard")
	public String showUserDashboard(
	        @RequestParam(defaultValue = "0") int page,
	        Model model,
	        Principal principal) {

	    User user = userService.getUserByUsername(principal.getName());
	    int pageSize = 5; 

	    Page<Journal> journalPage = journalService.getPaginatedJournalsByUser(user.getId(), page, pageSize);

	    model.addAttribute("user", user);
	    model.addAttribute("journals", journalPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", journalPage.getTotalPages());

	    return "users/user-dashboard";
	}

	
	
	
	@GetMapping("/register")
	public String showRegisterPage(HttpSession session, Model model) {
	    model.addAttribute("msg", session.getAttribute("msg"));
	    session.removeAttribute("msg"); 
	    return "register";
	}

	
	@PostMapping("/clear-msg")
	@ResponseBody
	public void clearMessage(HttpSession session) {
	    session.removeAttribute("msg");
	}

	
	@PostMapping("/regis")
	public String create(@ModelAttribute User user, @RequestParam("cpass") String confirmPass, HttpSession session) {

	    if (!user.getPass().equals(confirmPass)) {
	        session.setAttribute("msg", "Password and Confirm Password do not match!");
	        return "redirect:/register";
	    }

	    if (userService.checkEmail(user.getEmail())) {
	        session.setAttribute("msg", "Email already exist");
	        return "redirect:/register";
	    }

	    if (userService.checkUsername(user.getUsername())) {
	        session.setAttribute("msg", "Username already exist");
	        return "redirect:/register";
	    }

	    user.setPass(passwordEncoder.encode(user.getPass()));
	    user.setCpass(passwordEncoder.encode(user.getCpass()));

	    User savedUser = userService.create(user);
	    if (savedUser != null) {
	        session.setAttribute("msg", "Registered Successfully");
	    } else {
	        session.setAttribute("msg", "Error!!!");
	    }

	    return "redirect:/login";
	}

	
	

	
	@GetMapping("/profile")
	public String showProfile(Model model, Principal principal,HttpSession session) {
		
		String username = principal.getName();
		
		User user = userService.getUserByUsername(username);
		model.addAttribute("user", user);
		
		 Object msg = session.getAttribute("msg");
		    if (msg != null) {
		        model.addAttribute("msg", msg);
		        session.removeAttribute("msg"); // Clear after showing
		    }
		
		return "journal/profile-details";		
	}
	

	
	@GetMapping("/search")
	public String searchByKeyword(Model model, Principal principal, @RequestParam("keyword") String keyword) {
	    User user = userService.getUserByUsername(principal.getName());

	    List<Journal> journals;
	    

	    if (keyword != null && !keyword.trim().isEmpty()) {
	    	journals = journalService.searchByTitleOrTagName(user.getId(), keyword);

	    } else {
	        journals = journalService.getAllJournalsByUser(user.getId()); 
	    }
	    

	    if (journals == null || journals.isEmpty()) {
	        return "journal/no-results";
	    }

	    model.addAttribute("user", user);
	    model.addAttribute("journals", journals);
	    model.addAttribute("currentPage", 0);
	    model.addAttribute("totalPages", 1);
	    return "users/user-dashboard";
	}


	@GetMapping("/filter")
	public String filterByDate(Model model, Principal principal,
	        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

	    User user = userService.getUserByUsername(principal.getName());
	    List<Journal> journals = journalService.filterByDateRange(user.getId(), startDate, endDate);
	    
	    if (journals == null || journals.isEmpty()) {
	        return "journal/no-results";
	    }
	    
	    model.addAttribute("user", user);
	    model.addAttribute("journals", journals);
	    return "users/user-dashboard";
	}


}
