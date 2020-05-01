package com.techelevator.model.templates;

import java.util.List;

public class DayTemplate implements Template {
	
	private int id;
	private String nickname;
	private List<ShiftTemplate> shifts;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<ShiftTemplate> getShifts() {
		return shifts;
	}
	public void setShifts(List<ShiftTemplate> shifts) {
		this.shifts = shifts;
	}
	
	
	

}
