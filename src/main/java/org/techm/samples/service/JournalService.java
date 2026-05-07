package org.techm.samples.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.techm.samples.entity.Journal;
import org.techm.samples.entity.Tag;
import org.techm.samples.exception.JournalNotFoundException;
import org.techm.samples.repository.JournalRepository;
import org.techm.samples.repository.TagRepository;

@Service
public class JournalService {

	
	@Autowired
	private JournalRepository journalRepo;
	
	@Autowired
	private TagRepository tagRepo;
	
	
	public Journal saveJournal(Journal journal) {
		
		return journalRepo.save(journal);
	}
	
	
	public Journal getJournalById(long id) {
		
		return journalRepo.findById(id).orElseThrow( () -> new JournalNotFoundException("Journal with ID " + id + " not found"));
	}
	
	
	public void deleteJournalById(Long id) {
		
		journalRepo.deleteById(id);
	}


	public List<Journal> searchByTitleOrTagName(Long userId, String keyword) {
	    return journalRepo.findByUserIdAndTitleContainingIgnoreCaseOrTags_NameContainingIgnoreCase(userId, keyword, keyword);
	}




	public List<Journal> getAllJournalsByUser(Long id) {
		// TODO Auto-generated method stub
		return journalRepo.findByUserId(id);
	}

	public List<Journal> filterByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
	    LocalDateTime start = startDate.atStartOfDay();             
	    LocalDateTime end = endDate.atTime(LocalTime.MAX);          

	    return journalRepo.findByUserIdAndCreatedAtBetween(userId, start, end);
	}

	
	public Page<Journal> getPaginatedJournalsByUser(Long userId, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
	    return journalRepo.findByUserId(userId, pageable);
	}

	public void attachTags(Journal journal, List<String> tagNames) {
	    List<Tag> tags = new ArrayList<>();
	    for (String name : tagNames) {
	        Tag tag = tagRepo.findByName(name);
	        if (tag == null) {
	            tag = new Tag();
	            tag.setName(name.trim());
	            tagRepo.save(tag);
	        }
	        tags.add(tag);
	    }
	    journal.setTags(tags);
	}



	
}
