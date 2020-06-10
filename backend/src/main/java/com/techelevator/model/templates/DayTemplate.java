package com.techelevator.model.templates;

import java.util.List;

public class DayTemplate implements Template {
	
	private int id;
	private String nickname;
	private List<Integer> shiftIds;
	
	
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
	public List<Integer> getShifts() {
		return shiftIds;
	}
	public void setShifts(List<Integer> shifts) {
		this.shiftIds = shiftIds;
	}
	
	
	

}
