package com.techelevator.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.model.UserDao;
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
	@Autowired
	private UserDao userDAO;
	
	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public List<ShiftTemplate> getAllShiftTemplates() {
		long companyId = auth.getCurrentUser().getCompanyId();
		return tempDAO.getAllShiftTemplates(companyId);
	}
	
	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public String newShiftTemplate(@RequestBody StringShiftTemplate template) {
		System.out.println(userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId()));
		
		int startHour = Integer.parseInt(template.getStartTime().split(":")[0]);
		int startMinute = Integer.parseInt(template.getStartTime().split(":")[1]);
		LocalTime startTime = LocalTime.of(startHour, startMinute, 0, 0);
		
		int endHour = Integer.parseInt(template.getEndTime().split(":")[0]);
		int endMinute = Integer.parseInt(template.getEndTime().split(":")[1]);
		LocalTime endTime = LocalTime.of(endHour, endMinute, 0, 0);
		
		ShiftTemplate finalTemplate = new ShiftTemplate(startTime, endTime, template.getAllowedShiftRoles());
		tempDAO.saveCompanyShiftTemplate(userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId()), finalTemplate);
		
		System.out.println("Controller reached");
		return "Haaayyy";
	}

}
