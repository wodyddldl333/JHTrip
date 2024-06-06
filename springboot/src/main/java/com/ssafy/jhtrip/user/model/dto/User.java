package com.ssafy.jhtrip.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {

	@NotBlank
	private String id;

	@NotBlank
	private String name;

	@Size(min = 4, message = "Password must be at least 4 characters long")
	private String pass;
	
	@NotBlank
	private String confirmpw;

	@Email
	@NotBlank
    private String email;
	
	private String remember;

	@Size(min = 4, message = "Password must be at least 4 characters long")
	private String newPw;

	private String profileUri;

	public boolean isPassVal() {
		return this.pass.equals(this.confirmpw);
	}
}
