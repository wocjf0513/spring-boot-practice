package com.example.security_basic;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.NoArgsConstructor;


// ORM - Object Relation Mapping

@Entity
@NoArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;

@Builder	
public User(int id, String username, String password, String email, String role, String provider, String providerid) {
	this.id=id;
	this.username=username;
	this.password=password;
	this.email=email;
	this.role=role;
	
}
	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	 //ROLE_USER, ROLE_ADMIN
	@CreationTimestamp
	private Timestamp createDate;
}