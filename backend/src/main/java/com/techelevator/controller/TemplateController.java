package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.model.templates.ShiftTemplate;
import com.techelevator.model.templates.ShiftTemplateDAO;
import com.techelevator.model.templates.StringShiftTemplate;

@CrossOrigin
@RestController
@RequestMapping("/templates")
public class TemplateController {
	
	@Autowired
	private AuthProvider auth;
	@Autowired
	private ShiftTemplateDAO tempDAO;
	
	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public List<ShiftTemplate> getAllShiftTemplates() {
		long companyId = auth.getCurrentUser().getCompanyId();
		return tempDAO.getAllShiftTemplates(companyId);
	}
	
	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public String newShiftTemplate(@RequestBody StringShiftTemplate template) {
		System.out.println("Controller reached");
		return "Haaayyy";
	}

}
