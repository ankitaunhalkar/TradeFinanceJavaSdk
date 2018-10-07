package com.bridgeit.tradefinance.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	@Size(min=2, max=45)
	private String org_name;
	
	@Column
	@Size(min=2, max=45)
	private String role;
	
	@Column
	private String bankname;
	
	@Column
	@Email
	@NotNull
	private String email;
	
	@Column
	@NotNull
	@Size(min=3)
	private String password;
	
	@Column
	@Size(min=10, max=10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phoneno;
	
	private boolean isVerified;

	public User() {
		
	}
	public User(RegisterDto user) {
		this.org_name = user.getOrg_name();
		this.role = user.getRole();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.phoneno = user.getPhoneno();
		this.bankname = user.getBankname();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	public String getBankname() {
		return bankname;
	}
	
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
}