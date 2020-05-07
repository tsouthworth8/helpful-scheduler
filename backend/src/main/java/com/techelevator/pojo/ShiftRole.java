package com.techelevator.pojo;

public class ShiftRole {
	
	private int id;
	private int companyId;
	private String title;
	
	public ShiftRole(int id, int companyId, String title) {
		this.id = id;
		this.companyId = companyId;
		this.title = title;
	}
	
	public ShiftRole(int companyId, String title) {
		this.companyId = companyId;
		this.title = title;
	}
	
	public ShiftRole() {
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	

}
