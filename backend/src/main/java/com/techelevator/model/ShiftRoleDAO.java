package com.techelevator.model;

import java.util.List;

import com.techelevator.pojo.ShiftRole;

public interface ShiftRoleDAO {
	
	public boolean saveShiftRole(long companyId, String title);
	public List<ShiftRole> getAllShiftRoles(long companyId);
	public ShiftRole getShiftRoleById(int id);
	public boolean clearAllShiftRoles(long companyId);
	public int getNextShiftRoleId();

}
