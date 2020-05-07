package com.techelevator.model.templates;

import java.time.LocalTime;
import java.util.List;

import com.techelevator.pojo.ShiftRole;

public class ShiftTemplate implements Template {
	
	private int id;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<ShiftRole> allowedShiftRoles;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public List<ShiftRole> getAllowedShiftRoles() {
		return allowedShiftRoles;
	}
	public void setAllowedShiftRoles(List<ShiftRole> allowedShiftRoles) {
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	
	
	

}
