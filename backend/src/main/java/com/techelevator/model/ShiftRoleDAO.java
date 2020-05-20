package com.techelevator.model;

import java.util.List;

import com.techelevator.pojo.ShiftRole;

public interface ShiftRoleDAO {
	
	public boolean saveShiftRole(ShiftRole role);
	public List<ShiftRole> getAllShiftRoles(int companyId);
	public ShiftRole getShiftRoleById(int id);
	public boolean clearAllShiftRoles(int companyId);
	public int getNextShiftRoleId();

}
