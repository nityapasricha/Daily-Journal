package org.techm.samples.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techm.samples.entity.Journal;
import org.techm.samples.exception.JournalNotFoundException;
import org.techm.samples.repository.JournalRepository;
import org.techm.samples.repository.TagRepository;
import org.techm.samples.service.JournalService;


@ExtendWith(MockitoExtension.class)
class JournalServiceTest {

    @Mock
    private JournalRepository journalRepo;

    @Mock
    private TagRepository tagRepo;

    @InjectMocks
    private JournalService journalService;
    
    
    
    
    @Test
    void testSaveJournalShouldReturnSavedJournal() {
        Journal journal = new Journal();
        journal.setTitle("Gratitude Entry");

        when(journalRepo.save(journal)).thenReturn(journal);

        Journal result = journalService.saveJournal(journal);

        assertNotNull(result);
        assertEquals("Gratitude Entry", result.getTitle());
    }

    
    
    

    @Test
    void attachTagsShouldCreateNewTagIfNotFound() {
        Journal journal = new Journal();
        List<String> tagNames = List.of("peace", "gratitude");

        when(tagRepo.findByName("peace")).thenReturn(null); // Simulate tag not existing
        when(tagRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Save new tag

        journalService.attachTags(journal, tagNames);

        assertEquals(2, journal.getTags().size()); // Verify tags were attached
    }
    
    
    
    @Test
    void testGetJournalByIdWhenJournalExists() {
    	
    	Journal journal = new Journal();
    	journal.setId(1L);
    	
    	when(journalRepo.findById(1L)).thenReturn(Optional.of(journal));
    	
    	Journal result = journalService.getJournalById(1L);
    	
    	assertEquals(1L, result.getId());
    	
    }
    
    @Test
    void testGetJournalByIdThrowsExceptionWhenNotFound() {
        when(journalRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(JournalNotFoundException.class, () -> journalService.getJournalById(99L));
    }
    
    
    @Test
    void testDeleteJournalByIdShouldInvokeRepositoryDelete() {
        journalService.deleteJournalById(1L);
        verify(journalRepo, times(1)).deleteById(1L);
    }

    
    @Test
    void testSearchByTitleOrTagNameReturnsMatchingJournals() {
        List<Journal> mockList = List.of(new Journal(), new Journal());

        when(journalRepo.findByUserIdAndTitleContainingIgnoreCaseOrTags_NameContainingIgnoreCase(1L, "mindful", "mindful"))
            .thenReturn(mockList);

        List<Journal> result = journalService.searchByTitleOrTagName(1L, "mindful");

        assertEquals(2, result.size());
    }

    
    @Test
    void testGetAllJournalsByUserReturnsList() {
        when(journalRepo.findByUserId(1L)).thenReturn(List.of(new Journal()));

        List<Journal> result = journalService.getAllJournalsByUser(1L);

        assertEquals(1, result.size());
    }

    
    @Test
    void testFilterByDateRangeReturnsFilteredList() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);
        LocalDateTime startDT = start.atStartOfDay();
        LocalDateTime endDT = end.atTime(LocalTime.MAX);

        when(journalRepo.findByUserIdAndCreatedAtBetween(1L, startDT, endDT)).thenReturn(List.of(new Journal()));

        List<Journal> result = journalService.filterByDateRange(1L, start, end);

        assertEquals(1, result.size());
    }

}
