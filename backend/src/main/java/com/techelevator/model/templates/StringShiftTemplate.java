package com.techelevator.model.templates;

import java.time.LocalTime;
import java.util.List;

public class StringShiftTemplate implements Template {
	
	private long id;
	private String startTime;
	private String endTime;
	private List<Integer> allowedShiftRoles;
	
	public StringShiftTemplate(String startTime, String endTime, List<Integer> allowedShiftRoles) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	public StringShiftTemplate() {
	}
	
	///////
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Integer> getAllowedShiftRoles() {
		return allowedShiftRoles;
	}

	public void setAllowedShiftRoles(List<Integer> allowedShiftRoles) {
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	
	
}
	
	
	