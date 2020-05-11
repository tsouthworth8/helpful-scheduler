package com.techelevator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String forgotPassword(@RequestBody String email) {
    	
    	return "Heyy";
    }

}