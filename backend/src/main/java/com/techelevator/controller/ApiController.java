package com.techelevator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.CompanyDao;
import com.techelevator.model.UserDao;
import com.techelevator.pojo.User;

/**
 * ApiController
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AuthProvider auth;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private CompanyDao company;
    
    @Autowired
    private UserDao user;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
        You can lock down which roles are allowed by checking
        if the current user has a role.
        
        In this example, if the user does not have the admin role
        we send back an unauthorized error.
        */
        if (!auth.userHasRole(new String[] { "admin" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }
    
    @RequestMapping(path = "/invite/{email}", method = RequestMethod.GET)
    public String employeeInvite(@PathVariable String email) {
    	
    	long companyId = user.getCompanyIdByUserId(auth.getCurrentUser().getId());
    	String companyName = company.getCompanyNameById(companyId);
    	
    	SimpleMailMessage newEmail = new SimpleMailMessage();
    	String message = "You have been invited to join " + companyName + " Helpful Scheduler page"
    			+ "\n http://localhost:8081/register?id=" + companyId;
    	
    	newEmail.setTo(email + ".com");
    	newEmail.setSubject("Helpful Scheduler Invite");
    	newEmail.setText(message);
    	
    	mailSender.send(newEmail);
    	
    	return email + ".com";
    }
}