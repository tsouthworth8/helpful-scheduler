package com.techelevator.pojo;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordUpdate {
	
	private String oldPassword;
	
	@NotBlank(message = "Password is required")
    private String newPassword;
    private String confirmNewPassword;

    private boolean newPasswordMatching;

    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordMatching() {
        if (newPassword != null) {
            return newPassword.equals(confirmNewPassword);
        }
        return true;
    }

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public boolean isNewPasswordMatching() {
		return newPasswordMatching;
	}

	public void setNewPasswordMatching(boolean newPasswordMatching) {
		this.newPasswordMatching = newPasswordMatching;
	}
    
    
	
	

}
