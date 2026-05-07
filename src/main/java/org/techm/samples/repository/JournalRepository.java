package org.techm.samples.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techm.samples.entity.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long>{


	List<Journal> findByUserIdAndTitleContainingIgnoreCaseOrTags_NameContainingIgnoreCase(Long userId, String titleKeyword, String tagKeyword);

	List<Journal> findByUserId(Long id);

	List<Journal> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
	
	Page<Journal> findByUserId(Long userId, Pageable pageable);

}
