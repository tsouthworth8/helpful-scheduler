package com.techelevator.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.JwtTokenHandler;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.authentication.UserCreationException;
import com.techelevator.model.CompanyDao;
import com.techelevator.model.UserDao;
import com.techelevator.pojo.PasswordUpdate;
import com.techelevator.pojo.User;

/**
 * AccountController
 */

@CrossOrigin
@RestController
public class AccountController {
	@Autowired
	private AuthProvider auth;

	@Autowired
	private JwtTokenHandler tokenHandler;

	@Autowired
	private CompanyDao company;

	@Autowired
	private UserDao userDAO;

	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@RequestBody User user, RedirectAttributes flash) throws UnauthorizedException {
		if (auth.signIn(user.getUsername(), user.getPassword())) {
			User currentUser = auth.getCurrentUser();
			return tokenHandler.createToken(user.getUsername(), currentUser.isManager());
		} else {
			throw new UnauthorizedException();
		}
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public String register(@Valid @RequestBody User user, BindingResult result) throws UserCreationException {
		if (result.hasErrors()) {
			String errorMessages = "";
			for (ObjectError error : result.getAllErrors()) {
				errorMessages += error.getDefaultMessage() + "\n";
			}
			throw new UserCreationException(errorMessages);
		}
		
		if (user.getCompanyName() != "") {
			company.addCompany(user.getCompanyName());
			long companyId = company.getCompanyIdByName(user.getCompanyName());
			auth.register(user.getUsername(), user.getEmail(), user.getPassword(), user.isManager(), companyId);
		} else {
			auth.register(user.getUsername(), user.getEmail(), user.getPassword(), user.isManager(),
					user.getCompanyId());
		}

		return "{\"success\":true}";
	}

	@RequestMapping(path = "/forgot-password", method = RequestMethod.POST)
	public String forgotPassword(@RequestBody String emailAddress) {
		User user = userDAO.getUserByEmail(emailAddress);
		String newPassword = RandomStringUtils.randomAlphanumeric(12);

		if (user != null) {
			userDAO.changePassword(user, newPassword);

			// creates a simple e-mail object
			SimpleMailMessage email = new SimpleMailMessage();
			String message = "Your account password has been reset to " + newPassword
					+ ". Please use this to log in and change your password immediately.";
			email.setTo(emailAddress);
			email.setSubject("Helpful Scheduler - Password Reset");
			email.setText(message);

			// sends the e-mail
			mailSender.send(email);
			return "Email sent.";
		} else {
			return "User not found.";
		}
	}
	
	@RequestMapping(path = "/change-password", method = RequestMethod.POST)
	public boolean changePassword(@RequestBody PasswordUpdate passwords) {
		System.out.println("Controller reached.");
		
		String oldPassword = passwords.getOldPassword();
		String newPassword = passwords.getNewPassword();
		
		return auth.changePassword(oldPassword, newPassword);
	}

}