package org.techm.samples.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Journal> journals = new ArrayList<>();
    
    
    public Tag() {
    	
    }

	public Tag(Long id, String name, List<Journal> journals) {
		super();
		this.id = id;
		this.name = name;
		this.journals = journals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Journal> getJournals() {
		return journals;
	}

	public void setJournals(List<Journal> journals) {
		this.journals = journals;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", journals=" + journals + "]";
	}


    
    
    
}
