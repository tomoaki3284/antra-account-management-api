package com.antra.antraaccountmanagementapi.model;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`User`")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "uid")
	private Long uid;
	
	@Column(name = "Username")
	private String username;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "UserCreationDate")
	private LocalDate userCreationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//	@JoinTable(name = "UserRole",
//		joinColumns = @JoinColumn(name = "uid"),
//		inverseJoinColumns = @JoinColumn(name = "RoleId")
//	)
	private Set<UserRole> userRoleSet;
	
	public User(String username, String password, String email, LocalDate userCreationDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.userCreationDate = userCreationDate;
	}
	
	public User() {}
	
	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public Set<UserRole> getUserRoleSet() {
		return userRoleSet;
	}
	
	public void setUserRoleSet(Set<UserRole> userRoleSet) {
		this.userRoleSet = userRoleSet;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getUserCreationDate() {
		return userCreationDate;
	}
	
	public void setUserCreationDate(LocalDate userCreationDate) {
		this.userCreationDate = userCreationDate;
	}
}
