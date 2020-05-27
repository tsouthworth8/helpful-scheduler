package com.techelevator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.model.templates.ShiftTemplate;
import com.techelevator.model.templates.ShiftTemplateDAO;

@CrossOrigin
@RestController
@RequestMapping("/template")
public class TemplateController {
	
	@Autowired
	private ShiftTemplateDAO tempDAO;
	
	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public boolean newShiftTemplate(@RequestBody ShiftTemplate template) {
		return false;
	}

}
