package org.techm.samples.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(JournalNotFoundException.class)
	public String handleJournalNotFoundException(Model model,JournalNotFoundException ex) {
		model.addAttribute("error",  ex.getMessage());
		
		return "error/data-error";
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(Model model,UserNotFoundException ex) {
		model.addAttribute("error", ex.getMessage());
		
		return "error/data-error";
	}
	
	
}
