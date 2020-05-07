package com.techelevator.model.templates;

import java.time.LocalTime;
import java.util.List;

import com.techelevator.model.ShiftRole;

public class ShiftTemplate implements Template {
	
	private int id;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<Integer> allowedShiftRoles;
	
	public ShiftTemplate(int id, LocalTime startTime, LocalTime endTime, List<Integer> allowedShiftRoles) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	public ShiftTemplate(LocalTime startTime, LocalTime endTime, List<Integer> allowedShiftRoles) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	public ShiftTemplate() {
	}
	
	
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
	public List<Integer> getAllowedShiftRoles() {
		return allowedShiftRoles;
	}
	public void setAllowedShiftRoles(List<Integer> allowedShiftRoles) {
		this.allowedShiftRoles = allowedShiftRoles;
	}
	
	
	
	

}
