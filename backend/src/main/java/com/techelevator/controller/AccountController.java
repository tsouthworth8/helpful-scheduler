package com.techelevator.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.techelevator.pojo.Users;

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
    public String login(@RequestBody Users user, RedirectAttributes flash) throws UnauthorizedException {
        if (auth.signIn(user.getUsername(), user.getPassword())) {
            Users currentUser = auth.getCurrentUser();
            return tokenHandler.createToken(user.getUsername(), currentUser.isManager());
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @RequestBody Users user, BindingResult result) throws UserCreationException {
        if (result.hasErrors()) {
            String errorMessages = "";
            for (ObjectError error : result.getAllErrors()) {
                errorMessages += error.getDefaultMessage() + "\n";
            }
            throw new UserCreationException(errorMessages);
        }
        company.addCompany(user.getCompanyName());
        long companyId = company.getCompanyIdByName(user.getCompanyName());
        auth.register(user.getUsername(), user.getEmail(), user.getPassword(), user.isManager(), companyId);
        
        
        return "{\"success\":true}";
    }
    
    @RequestMapping(path="/forgot-password", method=RequestMethod.POST)
    public String forgotPassword(@RequestBody String emailAddress) {
    	Users user = userDAO.getUserByEmail(emailAddress);
    	String newPassword = RandomStringUtils.randomAlphanumeric(12);
    	
    	if(user != null) {
    		userDAO.changePassword(user, newPassword);
    		
    		// creates a simple e-mail object
            SimpleMailMessage email = new SimpleMailMessage();
            String message = "Your account password has been reset to " + newPassword + ". Please use this to log in and change your password immediately.";
            email.setTo(emailAddress);
            email.setSubject("Helpful Scheduler - Password Reset");
            email.setText(message);
             
            // sends the e-mail
            mailSender.send(email);
    	} else {
    		return "There is no account linked to that email address.";
    	}
    	
    	return "Please check your email for a message containing your new password.";
    }

}