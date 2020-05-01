package com.techelevator.model.templates;

import java.util.List;

public class WeekTemplate implements Template {
	
	private int id;
	private String nickname;
	private List<DayTemplate> days;
	
	
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
	public List<DayTemplate> getDays() {
		return days;
	}
	public void setDays(List<DayTemplate> days) {
		this.days = days;
	}
	
	

}
