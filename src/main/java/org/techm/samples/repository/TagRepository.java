package org.techm.samples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techm.samples.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByName(String name);

	
}
