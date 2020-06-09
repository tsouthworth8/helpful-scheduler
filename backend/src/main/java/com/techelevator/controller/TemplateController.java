package com.techelevator.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.model.ShiftRoleDAO;
import com.techelevator.model.UserDao;
import com.techelevator.model.templates.DayTemplate;
import com.techelevator.model.templates.DayTemplateDAO;
import com.techelevator.model.templates.ShiftTemplate;
import com.techelevator.model.templates.ShiftTemplateDAO;
import com.techelevator.model.templates.StringShiftTemplate;
import com.techelevator.model.templates.WeekTemplate;
import com.techelevator.model.templates.WeekTemplateDAO;
import com.techelevator.pojo.ShiftRole;

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
	@Autowired
	private ShiftRoleDAO srDAO;
	@Autowired
	private DayTemplateDAO dayDAO;
	@Autowired
	private WeekTemplateDAO weekDAO;
	
	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public List<ShiftTemplate> getAllShiftTemplates() {
		long companyId = userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId());
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
		
		List<ShiftRole> roleList = new ArrayList<ShiftRole>();
		for(Integer id : template.getAllowedShiftRoles()) {
			roleList.add(srDAO.getShiftRoleById(id));
		}
		
		ShiftTemplate finalTemplate = new ShiftTemplate(startTime, endTime, roleList);
		tempDAO.saveCompanyShiftTemplate(userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId()), finalTemplate);
		
		return "Haaayyy";
	}
	
	@RequestMapping(path = "/newDay", method = RequestMethod.POST)
	public boolean newDayTemplate(@RequestBody DayTemplate dayTemplate) {
		long companyId = userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId());
		return dayDAO.saveDayTemplate(companyId, dayTemplate);
	}
	
	@RequestMapping(path = "/newWeek", method = RequestMethod.POST)
	public boolean newDayTemplate(@RequestBody WeekTemplate weekTemplate) {
		long companyId = userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId());
		return weekDAO.saveWeekTemplate(companyId, weekTemplate);
	}
	
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	public boolean deleteShiftTemplates(@RequestBody List<Integer> templateList) {
		boolean answer = false;
		long companyId = userDAO.getCompanyIdByUserId(auth.getCurrentUser().getId());
		
		for(Integer templateId : templateList) {
			tempDAO.deleteCompanyShiftTemplate(templateId, companyId);
		}
		
		return answer;
	}

}
