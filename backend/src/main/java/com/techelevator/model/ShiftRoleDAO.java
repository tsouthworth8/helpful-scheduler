package com.techelevator.model;

import java.util.List;

public interface ShiftRoleDAO {
	
	public boolean saveShiftRole(ShiftRole role);
	public List<ShiftRole> getAllShiftRoles(int companyId);

}
