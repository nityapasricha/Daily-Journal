package org.techm.samples.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
 
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String username;
	private String email;
	private String mobile;
	private String pass;
	private String cpass;
	private String role;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Journal> journals;
	
	public User() {
		super();
	}
	

	
	
	
	public User(String name, String username, String email, String mobile, String pass, String cpass,
			String role, List<Journal> journals) {
		super();
		
		this.name = name;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.pass = pass;
		this.cpass = cpass;
		this.role = role;
		this.journals = journals;
	}



	public List<Journal> getJournals() {
		return journals;
	}


	public void setJournals(List<Journal> journals) {
		this.journals = journals;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCpass() {
		return cpass;
	}
	
	public void setCpass(String cpass) {
		this.cpass = cpass;
	}





	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", mobile="
				+ mobile + ", pass=" + pass + ", cpass=" + cpass + ", role=" + role + ", journals=" + journals + "]";
	}


	
	
 
}
 
 