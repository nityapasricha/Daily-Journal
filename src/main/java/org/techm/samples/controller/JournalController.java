package org.techm.samples.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.User;
import org.techm.samples.service.JournalService;
import org.techm.samples.service.UserService;

@Controller
@RequestMapping("/journals")
public class JournalController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JournalService journalService;
	
	
	@RequestMapping
	public String showJournal(Model model) {
		model.addAttribute("journal", new Journal());
		return "journal/journal-form";
	}
	
	
	
	
	@PostMapping("/create")
	public String createJournal(@ModelAttribute Journal journal, Principal principal,@RequestParam("image") MultipartFile imageFile,@RequestParam(value = "tag", required = false) String tagInput
) {
		
		 String username = principal.getName();
		 User user = userService.getUserByUsername(username);
		 
		 journal.setUser(user); 
		 
		    if (!imageFile.isEmpty()) {
		    	
		    	String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

		        
		        try {
		        	
		        	Path uploadDir = Paths.get("uploads");

		        	
		        	if(!Files.exists(uploadDir)) {
		        		Files.createDirectories(uploadDir);
		        	}
		        	
		        	
		        	Path filePath = uploadDir.resolve(filename);
		            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        	
			    	journal.setImg(filename);


		        }catch (Exception e) {
		        	e.printStackTrace();
				}
		        
		    }
		    
		    List<String> tagNames = (tagInput != null && !tagInput.trim().isEmpty())
		    	    ? List.of(tagInput.split("\\s*,\\s*"))
		    	    : List.of();
		    	
		    
		 journalService.attachTags(journal, tagNames);
		 journalService.saveJournal(journal);    

	
		return "redirect:/users/dashboard";
	}
	
	
	
	
	
	
	@GetMapping("/view/{id}")
	public String viewJournal(@PathVariable("id") Long id, Model model) {
		
		Journal journal = journalService.getJournalById(id);
		model.addAttribute("journal", journal);
		
		return "journal/view-journal";
	}
	
	@GetMapping("/views/{id}")
	public String viewAdminJournal(@PathVariable("id") Long id, Model model) {
		
		Journal journal = journalService.getJournalById(id);
		model.addAttribute("journal", journal);
		
		return "journal/view-journal-admin";
	}
	
	
	
	@GetMapping("/edit/{id}")
	public String editJournal(@PathVariable("id") Long id, Model model) {
		
		Journal journal = journalService.getJournalById(id);
	    model.addAttribute("journal", journal);
		return "journal/journal-form";
	}
	
	
	
	
	@PostMapping("/delete/{id}")
	public String deleteJournal(@PathVariable("id") Long id) {
		
		journalService.deleteJournalById(id);
		return "redirect:/users/dashboard";
	}
	
	
	@PostMapping("/delete-admin/{id}")
	public String deleteAdminJournal(@PathVariable("id") Long id) {
		
		journalService.deleteJournalById(id);
		return "redirect:/admin/dashboard";
	}
	
	
	
	
	
	@PostMapping("/update")
	public String updateJournal(@ModelAttribute Journal journal, Principal principal,@RequestParam("image") MultipartFile imageFile,@RequestParam(value = "tag", required = false) String tagInput
) {
		User user = userService.getUserByUsername(principal.getName());
		journal.setUser(user); 

		if (!imageFile.isEmpty()) {
		    String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
		    Path uploadDir = Paths.get("uploads");

		    try {
		        if (!Files.exists(uploadDir)) {
		            Files.createDirectories(uploadDir);
		        }

		        Path filePath = uploadDir.resolve(filename);
		        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		        journal.setImg(filename); 
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		} else {
		    // Do not clear the existing image if no new image is uploaded
		    Journal existing = journalService.getJournalById(journal.getId());
		    journal.setImg(existing.getImg());
		}
		
		List<String> tagNames = (tagInput != null && !tagInput.trim().isEmpty())
			    ? List.of(tagInput.split("\\s*,\\s*"))
			    : List.of();
			journalService.attachTags(journal, tagNames);


		journalService.saveJournal(journal);
		return "redirect:/users/dashboard";

	}
	
}
