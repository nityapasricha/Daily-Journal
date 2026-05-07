package org.techm.samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LandingController {
	
	
//	Landing page of the application
	
	@GetMapping("/")
	public String getHomePage() {
	    return "index"; 
	}

	
	@GetMapping("/login")
	public String getLoginPage() {
	    return "login"; 
	}

	
	@GetMapping("/register")
	public String getRegisterPage() {
	    return "register"; 
	}

	

}
