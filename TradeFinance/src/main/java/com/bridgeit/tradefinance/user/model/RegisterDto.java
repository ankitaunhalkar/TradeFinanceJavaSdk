package com.bridgeit.tradefinance.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterDto {

	@Size(min=2, max=45)
	private String org_name;
	
	@Size(min=2, max=45)
	private String role;
	
	@Email
	@NotNull
	private String email;
	
	@NotNull
	@Size(min=3)
	private String password;
	
	@Size(min=10, max=10)
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phoneno;

	private String bankname;

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
	
	public String getBankname() {
		return bankname;
	}
	
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
}
