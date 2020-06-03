package com.techelevator.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.CompanyDao;
import com.techelevator.model.ShiftRoleDAO;
import com.techelevator.model.UserDao;
import com.techelevator.pojo.ShiftRole;
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
    
    @Autowired
    private ShiftRoleDAO shift;
    
    

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
    
    @RequestMapping(path = "/invite", method = RequestMethod.POST)
    public String employeeInvite(@RequestBody String email) throws UnsupportedEncodingException {
    	email = URLDecoder.decode(email, "UTF-8");
    	email = email.substring(0, email.length() - 1);
    	
    	long companyId = user.getCompanyIdByUserId(auth.getCurrentUser().getId());
    	String companyName = company.getCompanyNameById(companyId);
    	
    	SimpleMailMessage newEmail = new SimpleMailMessage();
    	String message = "You have been invited to join " + companyName + " Helpful Scheduler page"
    			+ "\n http://localhost:8081/register?id=" + companyId;
    	
    	newEmail.setTo(email);
    	newEmail.setSubject("Helpful Scheduler Invite");
    	newEmail.setText(message);
    	
    	mailSender.send(newEmail);
    	
    	return email;
    }
    
    @RequestMapping(path = "/roles", method = RequestMethod.POST)
    public List<ShiftRole> shiftRoles (@RequestBody List<ShiftRole> roles){
    	List<ShiftRole> newList;
    	long companyId = user.getCompanyIdByUserId(auth.getCurrentUser().getId());
    	
    	for(ShiftRole role : roles) {
    		shift.saveShiftRole(companyId, role.getTitle());
    		
    	}
    	return null;
    }
    
    @RequestMapping(path = "/getRoles", method = RequestMethod.GET)
    public List<ShiftRole> getCompanyRoles(){
    	long companyId = user.getCompanyIdByUserId(auth.getCurrentUser().getId());
    	List<ShiftRole> roles = shift.getAllShiftRoles(companyId);
    	
    	return roles;
    			
    }
    
}